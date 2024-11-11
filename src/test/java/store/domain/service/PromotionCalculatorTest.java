package store.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.vo.PromotionType;
import store.view.dto.request.PromotionRequest;

@SuppressWarnings("NonAsciiCharacters")
class PromotionCalculatorTest {

    private Promotions promotions;
    private Product promotionProduct;
    private List<Product> noPromotionProducts;
    private PromotionCalculator promotionCalculator;

    @BeforeEach
    void setUp() {
        LocalDateTime fixedDate = LocalDate.parse("2024-11-07").atStartOfDay();
        List<List<String>> promotionsData = List.of(
                List.of("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"),
                List.of("MD추천상품", "1", "1", "2024-01-01", "2024-12-31"),
                List.of("반짝할인", "1", "1", "2024-11-01", "2024-11-30")
        );

        promotions = new Promotions(promotionsData, fixedDate);
        promotionProduct = new Product("콜라", "1000", "10", promotions.getPromotionByType(PromotionType.from("탄산2+1")));
        noPromotionProducts = List.of(new Product("콜라", "1000", "10", Promotion.noPromotion()));
        promotionCalculator = new PromotionCalculator();
    }

    @Test
    void 구입_개수가_초과_수량일_때_프로모션이_적용_안되는_물건을_구매한다() {
        // given
        Product buyProduct = new Product("콜라", "1000", "20",
                promotions.getPromotionByType(PromotionType.from("탄산2+1")));
        List<PromotionRequest> requests = List.of(new PromotionRequest("콜라", true, false));
        int expectedPromotionQuantity = 3;

        // when
        int actual = promotionCalculator.calculatePromotion(buyProduct, promotionProduct, noPromotionProducts,
                requests);

        // then
        assertThat(actual).isEqualTo(expectedPromotionQuantity);
    }

    @Test
    void 구입_개수가_초과_수량일_때_프로모션이_적용_되는_물건만_구매한다() {
        // given
        Product buyProduct = new Product("콜라", "1000", "20",
                promotions.getPromotionByType(PromotionType.from("탄산2+1")));
        List<PromotionRequest> requests = List.of(new PromotionRequest("콜라", false, false));
        int expectedPromotionQuantity = 3;

        // when
        int actual = promotionCalculator.calculatePromotion(buyProduct, promotionProduct, noPromotionProducts,
                requests);

        // then
        assertThat(actual).isEqualTo(expectedPromotionQuantity);
    }

    @Test
    void 무료_프로모션_적용_물건을_구매한다() {
        // given
        Product buyProduct = new Product("콜라", "1000", "6", promotions.getPromotionByType(PromotionType.from("탄산2+1")));
        List<PromotionRequest> requests = List.of(new PromotionRequest("콜라", false, true));
        int expectedFreePromotionQuantity = 3;

        // when
        int actual = promotionCalculator.calculatePromotion(buyProduct, promotionProduct, noPromotionProducts,
                requests);

        // then
        assertThat(actual).isEqualTo(expectedFreePromotionQuantity);
    }

    @Test
    void 무료_프로모션_적용_물건을_구매하지_않는다() {
        // given
        Product buyProduct = new Product("콜라", "1000", "2", promotions.getPromotionByType(PromotionType.from("탄산2+1")));
        List<PromotionRequest> requests = List.of(new PromotionRequest("콜라", true, false));
        int expectedPromotionQuantity = 0;

        // when
        int actual = promotionCalculator.calculatePromotion(buyProduct, promotionProduct, noPromotionProducts,
                requests);

        // then
        assertThat(actual).isEqualTo(expectedPromotionQuantity);
    }
}
