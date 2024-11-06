package store.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@SuppressWarnings("NonAsciiCharacters")
class ProductTest {

    @ParameterizedTest
    @CsvSource(value = {"콜라, 1000","사이다, 1000","과자, 2000"})
    void 만들어진_상품이_올바른_값을_반환한다(String name, String quantity) {
        // given
        Product product = new Product(name, quantity);

        //when & then
        assertThat(product.name()).isEqualTo(name);
        assertThat(product.quantity()).isEqualTo(quantity);
    }
}
