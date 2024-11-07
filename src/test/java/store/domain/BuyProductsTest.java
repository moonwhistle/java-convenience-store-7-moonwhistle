package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("NonAsciiCharacters")
class BuyProductsTest {

    @ParameterizedTest
    @MethodSource("provideProductData")
    void 구매한_데이터를_객체로_생성한다(Map<String, String> splitProducts, List<Product> expectedProducts) {
        // given
        BuyProducts buyProducts = new BuyProducts(splitProducts);

        // when
        List<Product> actualProducts = buyProducts.products();

        // then
        assertThat(actualProducts).hasSameElementsAs(expectedProducts);
    }

    private static Stream<Arguments> provideProductData() {
        return Stream.of(
                Arguments.of(
                        Map.of("콜라", "1000"),
                        List.of(new Product("콜라", "1000"))
                ),
                Arguments.of(
                        Map.of("콜라", "1000", "사이다", "500"),
                        List.of(new Product("콜라", "1000"),
                                new Product("사이다", "500")
                        )
                )
        );
    }

}
