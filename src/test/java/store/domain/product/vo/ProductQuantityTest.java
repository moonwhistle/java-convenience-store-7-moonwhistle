package store.domain.product.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.product.exception.ProductErrorCode;

@SuppressWarnings("NonAsciiCharacters")
class ProductQuantityTest {

    @ParameterizedTest
    @ValueSource(strings = {"10", "4", "5", "100"})
    void 올바른_수량을_반환한다(String quantity) {
        // given
        ProductQuantity productQuantity = ProductQuantity.from(quantity);

        // when
        String actual = productQuantity.quantity();

        // then
        assertThat(actual).isEqualTo(quantity);
    }

    @Test
    void 재고가_0일_경우_재고없음을_반환한다() {
        // given
        String quantity = "0";
        String expectedQuantity = "재고 없음";
        ProductQuantity productQuantity = ProductQuantity.from(quantity);

        // when
        String actual = productQuantity.quantity();

        // then
        assertThat(actual).isEqualTo(expectedQuantity);
    }

    @Nested
    class 예외_처리_테스트를_진행한다 {

        @ParameterizedTest
        @NullAndEmptySource
        void 빈_값이_들어온다(String quantity) {
            // when & then
            assertThatThrownBy(() -> ProductQuantity.from(quantity))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ProductErrorCode.EMPTY_QUANTITY.getMessage());
        }

        @ParameterizedTest
        @ValueSource(strings = {"a", "?", "콜라", "사이다"})
        void 수량_형식이_아닌_값이_들어온다(String quantity) {
            // when & then
            assertThatThrownBy(() -> ProductQuantity.from(quantity))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ProductErrorCode.NOT_MATCH_TYPE.getMessage());
        }

        @ParameterizedTest
        @ValueSource(strings = {"-1", "-10", "-100", "-20"})
        void 수량에_음수_값이_들어온다(String quantity) {
            // when & then
            assertThatThrownBy(() -> ProductQuantity.from(quantity))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ProductErrorCode.NOT_MATCH_TYPE.getMessage());
        }
    }
}
