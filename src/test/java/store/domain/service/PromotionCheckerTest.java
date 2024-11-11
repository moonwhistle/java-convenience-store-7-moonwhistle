package store.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.BuyProducts;
import store.domain.Products;
import store.domain.Promotions;
import store.view.dto.response.PromotionResponse;

@SuppressWarnings("NonAsciiCharacters")
class PromotionCheckerTest {

    private PromotionChecker promotionChecker;
    private Products products;
    private Promotions promotions;

    @BeforeEach
    void setUp() {
        LocalDateTime fixedDate = LocalDate.parse("2024-11-07").atStartOfDay();
        List<List<String>> promotionsData = List.of(
                List.of("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"),
                List.of("MD추천상품", "1", "1", "2024-01-01", "2024-12-31"),
                List.of("반짝할인", "1", "1", "2024-11-01", "2024-11-30")
        );
        List<List<String>> productsData = List.of(
                List.of("콜라", "1000", "10", "탄산2+1"),
                List.of("콜라", "1000", "10", "null"),
                List.of("빵", "1000", "10", "null"),
                List.of("사이다", "1000", "8", "탄산2+1"),
                List.of("껌", "1000", "7", "null"),
                List.of("오렌지주스", "1800", "9", "MD추천상품")
        );

        promotions = new Promotions(promotionsData, fixedDate);
        promotionChecker = new PromotionChecker();
        products = new Products(productsData, promotions);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "3", "4", "6", "7"})
    void 무료_프로모션_혜택이_적용되지_않는_상품의_개수를_입력받는다(String quantity) {
        // given
        Map<String, String> buyProductData = Map.of("콜라", quantity);
        BuyProducts buyProducts = new BuyProducts(buyProductData, promotions, products);

        // when
        List<PromotionResponse> promotionResponses = promotionChecker.checkPromotion(products, buyProducts);

        // then
        assertThat(promotionResponses).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"2", "5", "8"})
    void 무료_프로모션이_적용되는_상품의_개수를_입력받는다(String quantity) {
        // given
        Map<String, String> buyProductData = Map.of("콜라", quantity);
        BuyProducts buyProducts = new BuyProducts(buyProductData, promotions, products);

        // when
        List<PromotionResponse> promotionResponses = promotionChecker.checkPromotion(products, buyProducts);

        // then
        assertThat(promotionResponses)
                .hasSize(1)
                .containsExactly(new PromotionResponse("콜라", false, 0, true, 1));
    }

    @ParameterizedTest
    @CsvSource(value = {"13, 4", "15, 6"})
    void 정가로_구매해야하는_경우가_발생하는_개수를_입력받는다(String quantity, int expectedNoPromotion) {
        // given
        Map<String, String> buyProductData = Map.of("콜라", quantity);
        BuyProducts buyProducts = new BuyProducts(buyProductData, promotions, products);

        // when
        List<PromotionResponse> promotionResponses = promotionChecker.checkPromotion(products, buyProducts);

        // then
        assertThat(promotionResponses)
                .hasSize(1)
                .containsExactly(new PromotionResponse("콜라", true, expectedNoPromotion, false, 0));
    }
}
