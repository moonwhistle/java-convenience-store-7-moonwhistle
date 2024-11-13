package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.domain.exception.ProductErrorCode;

@SuppressWarnings("NonAsciiCharacters")
class BuyProductsTest {

    private Promotions promotions;
    private Products products;

    @BeforeEach
    void setUp() {
        LocalDateTime fixedDate = LocalDate.parse("2024-11-07").atStartOfDay();
        List<List<String>> promotionsData = List.of(
                List.of("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"),
                List.of("MD추천상품", "1", "1", "2024-01-01", "2024-12-31"),
                List.of("반짝할인", "1", "1", "2024-11-01", "2024-11-30")
        );
        List<List<String>> productsData = List.of(
                List.of("콜라", "1000", "10", "탄산2+1"),
                List.of("콜라", "1000", "10", "null"),
                List.of("빵", "1000", "10", "null"),
                List.of("사이다", "1000", "8", "탄산2+1"),
                List.of("껌", "1000", "7", "null"),
                List.of("오렌지주스", "1800", "9", "MD추천상품")
        );
        promotions = new Promotions(promotionsData, fixedDate);
        products = new Products(productsData, promotions);
    }

    @ParameterizedTest
    @MethodSource("provideProductData")
    void 구매한_데이터로_구매_객체를_생성한다(Map<String, String> splitProducts, List<Product> expectedProducts) {
        // given
        BuyProducts buyProducts = new BuyProducts(splitProducts, promotions, products);

        // when
        List<Product> actualProducts = buyProducts.products();

        // then
        assertThat(actualProducts).hasSameElementsAs(expectedProducts);
    }

    @ParameterizedTest
    @MethodSource("provideNoProductData")
    void 존재하지_않는_상품_값이_들어온다(Map<String, String> splitProducts) {
        // when & then
        assertThatThrownBy(() -> new BuyProducts(splitProducts, promotions, products))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ProductErrorCode.NOT_FOUND_PRODUCT_NAME.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideOverQuantityProductData")
    void 재고_수량을_초과한_구입값이_들어온다(Map<String, String> splitProducts) {
        // when & then
        assertThatThrownBy(() -> new BuyProducts(splitProducts, promotions, products))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ProductErrorCode.OVER_QUANTITY.getMessage());
    }

    private static Stream<Arguments> provideOverQuantityProductData() {
        return Stream.of(
                Arguments.of(Map.of("콜라", "50")),
                Arguments.of(Map.of("오렌지주스", "50", "사이다", "50"))
        );
    }

    private static Stream<Arguments> provideNoProductData() {
        return Stream.of(
                Arguments.of(Map.of("오징어", "5")),
                Arguments.of(Map.of("문어", "5", "사이다", "5"))
        );
    }

    private static Stream<Arguments> provideProductData() {
        return Stream.of(
                Arguments.of(
                        Map.of("콜라", "5"),
                        List.of(new Product("콜라", "1000", "5", Promotion.noPromotion()))
                ),
                Arguments.of(
                        Map.of("콜라", "5", "사이다", "5"),
                        List.of(new Product("콜라", "1000", "5", Promotion.noPromotion()),
                                new Product("사이다", "500", "8", Promotion.noPromotion())
                        )
                )
        );
    }
}
