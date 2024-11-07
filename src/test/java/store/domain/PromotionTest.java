package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.vo.PromotionType;

@SuppressWarnings("NonAsciiCharacters")
class PromotionTest {

    private LocalDateTime fixedTime;

    @BeforeEach
    void setUp() {
        fixedTime = LocalDate.parse("2024-11-07").atStartOfDay();
    }

    @Test
    void 프로모션이_없는_객체를_생성한다() {
        // given
        Promotion noPromotion = Promotion.noPromotion();

        // then
        assertThat(noPromotion.promotionType()).isEqualTo("");
        assertThat(noPromotion.isPromotion()).isFalse();
    }

    @Test
    void 해당되는_날짜가_아니면_프로모션이_비적용된다() {
        //given
        Promotion promotion = new Promotion(
                PromotionType.from("탄산2+1"),
                fixedTime,
                "2",
                "1",
                "2024-01-01",
                "2024-10-06"
        );

        // then
        assertThat(promotion.isPromotion()).isEqualTo(false);
    }

    @Test
    void 해당되는_날짜이면_프로모션이_적용된다() {
        //given
        Promotion promotion = new Promotion(
                PromotionType.from("탄산2+1"),
                fixedTime,
                "2",
                "1",
                "2024-01-01",
                "2024-11-08"
        );

        // then
        assertThat(promotion.isPromotion()).isEqualTo(true);
    }
}
