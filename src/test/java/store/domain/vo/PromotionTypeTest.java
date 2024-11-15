package store.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.exception.ProductErrorCode;

@SuppressWarnings("NonAsciiCharacters")
class PromotionTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"MD추천상품", "탄산2+1", "반짝할인"})
    void 올바른_프로모션_타입을_반환한다(String type) {
        // given
        PromotionType promotionType = PromotionType.from(type);

        // when
        String actual = promotionType.getPromotionType();

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
        String actual = promotionType.getPromotionType();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 빈_문자열_값을_가진_프로모션_타입_객체를_생성한다() {
        // given
        PromotionType promotionType = PromotionType.none();
        String expected = "";

        // then
        assertThat(promotionType.getPromotionType()).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 빈_값이_들어오면_예외를_발생시킨다(String promotionType) {
        // when & then
        assertThatThrownBy(() -> PromotionType.from(promotionType))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ProductErrorCode.WRONG_INPUT.getMessage());
    }
}
