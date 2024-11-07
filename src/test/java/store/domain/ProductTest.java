package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@SuppressWarnings("NonAsciiCharacters")
class ProductTest {

    @ParameterizedTest
    @CsvSource(value = {"콜라, 1000, 500", "사이다, 1000, 500", "과자, 2000, 1000"})
    void 만들어진_상품이_올바른_값을_반환한다(String name, String quantity, String price) {
        // given
        Product product = new Product(name, price, quantity, Promotion.noPromotion());

        // when & then
        assertThat(product.name()).isEqualTo(name);
        assertThat(product.showQuantity()).isEqualTo(quantity);
        assertThat(product.price()).isEqualTo(Integer.parseInt(price));
        assertThat(product.promotionType()).isEqualTo("none");
    }

    @Test
    void 같은_이름을_가진_상품은_동일하다고_인식한다() {
        // given
        Product product1 = new Product("콜라", "1000", "10", Promotion.noPromotion());
        Product product2 = new Product("콜라", "2000", "5", Promotion.noPromotion());

        // when & then
        assertThat(product1).isEqualTo(product2);
        assertThat(product1.hashCode()).isEqualTo(product2.hashCode());
    }
}
