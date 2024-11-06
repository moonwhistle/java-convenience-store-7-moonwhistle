package store.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@SuppressWarnings("NonAsciiCharacters")
class ProductInfoTest {

    @ParameterizedTest
    @CsvSource({"콜라, 1000", "사이다, 1000", "컵라면, 1700"})
    void 상품_이름에_따라_가격을_반환한다(String name, int price) {
        // when & then
        assertThat(ProductInfo.findPriceByName(name))
                .isEqualTo(price);
    }
}
