package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.vo.PromotionType;

@SuppressWarnings("NonAsciiCharacters")
class PromotionsTest {

    private List<List<String>> promotionsData;
    private LocalDateTime fixedDate;

    @BeforeEach
    void setUp() {
        fixedDate = LocalDate.parse("2024-11-07").atStartOfDay();
        promotionsData = List.of(
                List.of("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"),
                List.of("MD추천상품", "1", "1", "2024-01-01", "2024-12-31"),
                List.of("반짝할인", "1", "1", "2024-11-01", "2024-11-30")
        );
    }

    @Test
    void 프로모션_타입으로_프로모션을_찾는다() {
        // given
        Promotions promotions = new Promotions(promotionsData, fixedDate);

        // when
        Promotion mdPromotion = promotions.getPromotionByType(PromotionType.from("MD추천상품"));

        // then
        assertThat(mdPromotion.promotionType()).isEqualTo("MD추천상품");
        assertThat(mdPromotion.isPromotion()).isTrue();
    }

    @Test
    void 없는_프로모션_타입을_조회하면_noPromotion을_반환한다() {
        // given
        Promotions promotions = new Promotions(promotionsData, fixedDate);

        // when
        Promotion noPromotion = promotions.getPromotionByType(PromotionType.from("없는프로모션"));

        // then
        assertThat(noPromotion.promotionType()).isEqualTo("");
        assertThat(noPromotion.isPromotion()).isFalse();
    }
}
