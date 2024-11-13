package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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
        assertThat(noPromotion.getPromotionType()).isEqualTo("");
        assertThat(noPromotion.isPromotion()).isFalse();
    }

    @Test
    void 프로모션_구매수량과_증정수량이_올바르게_설정된다() {
        // given
        Promotion promotion = Promotion.from(
                PromotionType.from("탄산2+1"),
                fixedTime,
                "2",
                "1",
                "2024-01-01",
                "2024-12-31"
        );

        // then
        assertThat(promotion.getBuyQuantity()).isEqualTo(2);
        assertThat(promotion.getGettableQuantity()).isEqualTo(1);
    }

    @Test
    void 프로모션_주기_계산이_올바르다() {
        // given
        Promotion promotion = Promotion.from(
                PromotionType.from("탄산2+1"),
                fixedTime,
                "2",
                "1",
                "2024-01-01",
                "2024-12-31"
        );

        // then
        assertThat(promotion.getOnePromotionCycle()).isEqualTo(3);
    }

    @ParameterizedTest
    @CsvSource({
            "2024-11-07, true",
            "2024-01-01, false",
            "2024-12-31, true",
            "2025-01-01, false"
    })
    void 특정_날짜가_프로모션_적용_범위에_있는지_확인한다(String date, boolean expectedPromotionState) {
        // given
        LocalDateTime testDate = LocalDate.parse(date).atStartOfDay();
        Promotion promotion = Promotion.from(
                PromotionType.from("탄산2+1"),
                testDate,
                "2",
                "1",
                "2024-01-01",
                "2024-12-31"
        );

        // then
        assertThat(promotion.isPromotion()).isEqualTo(expectedPromotionState);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2024-01-01", "2024-12-30"})
    void 프로모션_경계일에_포함되어_프로모션이_적용된다(String date) {
        // given
        LocalDateTime boundaryDate = LocalDate.parse(date).atTime(LocalTime.MAX);
        Promotion promotion = Promotion.from(
                PromotionType.from("탄산2+1"),
                boundaryDate,
                "2",
                "1",
                "2024-01-01",
                "2024-12-31"
        );

        // then
        assertThat(promotion.isPromotion()).isTrue();
    }
}
