package store.domain.product.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.product.exception.ProductErrorCode;

@SuppressWarnings("NonAsciiCharacters")
class PromotionTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"MD추천상품", "탄산2+1", "반짝할인"})
    void 올바른_프로모션_타입을_반환한다(String type) {
        // given
        PromotionType promotionType = PromotionType.from(type);

        // when
        String actual = promotionType.promotionType();

        // then
        assertThat(actual).isEqualTo(type);
    }

    @Test
    void 프로모션에_null_값이_들어오면_빈_문자를_반환한다() {
        // given
        String type = "null";
        PromotionType promotionType = PromotionType.from(type);
        String expected = "";

        // when
        String actual = promotionType.promotionType();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 빈_값이_들어오면_예외를_발생시킨다(String promotionType) {
        // when & then
        assertThatThrownBy(() -> PromotionType.from(promotionType))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ProductErrorCode.NOT_FOUND_PROMOTION_TYPE.getMessage());
    }
}
