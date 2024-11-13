package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import store.domain.vo.PromotionType;

@SuppressWarnings("NonAsciiCharacters")
class ProductsTest {

    private Products products;

    @BeforeEach
    void setUp() {
        LocalDateTime fixedLocalDateTime = LocalDate.parse("2024-11-07").atStartOfDay();
        List<List<String>> productsData = List.of(
                List.of("콜라", "1000", "10", "탄산2+1"),
                List.of("콜라", "1000", "10", "null"),
                List.of("빵", "1000", "10", "null"),
                List.of("사이다", "1000", "8", "탄산2+1"),
                List.of("껌", "1000", "7", "null"),
                List.of("오렌지주스", "1800", "9", "MD추천상품")
        );
        Promotions promotions = new Promotions(
                List.of(
                        List.of("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"),
                        List.of("MD추천상품", "1", "1", "2024-01-01", "2024-12-31"),
                        List.of("null", "0", "0", "2024-01-01", "2024-10-31")
                ),
                fixedLocalDateTime
        );
        products = new Products(productsData, promotions);
    }

    @Test
    void 파일_데이터로_객체를_생성한다() {
        // when
        List<Product> actual = products.getProducts();

        // when
        assertThat(actual.size()).isEqualTo(6);
    }

    @ParameterizedTest
    @CsvSource(value = {"콜라, 20", "사이다, 8","오렌지주스, 9"})
    void 특정_상품의_재고_개수를_가져온다(String name, int quantity) {
        // when
        int actual = products.countProductsByName(name);

        // then
        assertThat(actual).isEqualTo(quantity);
    }

    @ParameterizedTest
    @CsvSource(value = {"콜라, 1", "사이다, 1"})
    void 프로모션에_해당되는_상품만_추출한다(String name, int expectedCount) {
        // when
        List<Product> promotionProducts = products.getPromotionProducts(name);

        // then
        assertThat(promotionProducts)
                .hasSize(expectedCount)
                .allMatch(Product::isPromotion);
    }

    @ParameterizedTest
    @CsvSource(value = {"콜라, 1", "사이다, 0"})
    void 프로모션에_해당되지_않는_상품만_추출한다(String name, int expectedCount) {
        // when
        List<Product> promotionProducts = products.getNoPromotionProducts(name);

        // then
        assertThat(promotionProducts)
                .hasSize(expectedCount)
                .allMatch(product -> !product.isPromotion());
    }

    @ParameterizedTest
    @MethodSource("provideShowProducts")
    void 상품을_보여주기_위해_재고없는_상품을_표시한다(List<Product> productData) {
        // when
        List<Product> showProduct = products.displayProducts();

        // then
        assertThat(showProduct)
                .containsAll(productData);
    }

    static Stream<Arguments> provideShowProducts() {
        Promotions promotions = new Promotions(List.of(
                List.of("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"),
                List.of("MD추천상품", "1", "1", "2024-01-01", "2024-12-31")
        ), LocalDateTime.now());
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Product("콜라", "1000", "10", promotions.getPromotionByType(PromotionType.from("탄산2+1"))),
                                new Product("콜라", "1000", "10", Promotion.noPromotion()),
                                new Product("빵", "1000", "10", Promotion.noPromotion()),
                                new Product("사이다", "1000", "8", promotions.getPromotionByType(PromotionType.from("탄산2+1"))),
                                new Product("사이다", "1000", "0", Promotion.noPromotion()),
                                new Product("껌", "1000", "7", Promotion.noPromotion()),
                                new Product("오렌지주스", "1800", "9", promotions.getPromotionByType(PromotionType.from("MD추천상품"))),
                                new Product("오렌지주스", "1800", "0", Promotion.noPromotion())
                        )
                )
        );
    }
}
