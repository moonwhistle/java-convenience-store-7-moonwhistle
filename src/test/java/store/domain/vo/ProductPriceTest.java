package store.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.exception.ProductErrorCode;

@SuppressWarnings("NonAsciiCharacters")
class ProductPriceTest {

    @ParameterizedTest
    @ValueSource(strings = {"10", "4", "5", "100"})
    void 올바른_가격을_반환한다(String price) {
        // given
        ProductPrice productPrice = ProductPrice.from(price);

        // when
        int actual = productPrice.getPrice();

        // then
        assertThat(actual).isEqualTo(Integer.parseInt(price));
    }

    @Test
    void 가격이_0인_객체를_생성한다() {
        // given
        ProductPrice productPrice = ProductPrice.none();
        int expected = 0;

        // then
        assertThat(productPrice.getPrice()).isEqualTo(expected);
    }

    @Nested
    class 예외_처리_테스트를_진행한다 {

        @ParameterizedTest
        @NullAndEmptySource
        void 빈_값이_들어온다(String price) {
            // when & then
            assertThatThrownBy(() -> ProductPrice.from(price))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ProductErrorCode.WRONG_INPUT.getMessage());
        }

        @ParameterizedTest
        @ValueSource(strings = {"a", "?", "콜라", "사이다"})
        void 가격_형식이_아닌_값이_들어온다(String price) {
            // when & then
            assertThatThrownBy(() -> ProductPrice.from(price))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ProductErrorCode.NOT_MATCH_TYPE.getMessage());
        }

        @ParameterizedTest
        @ValueSource(strings = {"-1", "-10", "-100", "-20"})
        void 가격에_음수_값이_들어온다(String price) {
            // when & then
            assertThatThrownBy(() -> ProductPrice.from(price))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ProductErrorCode.NOT_MATCH_TYPE.getMessage());
        }
    }

}
