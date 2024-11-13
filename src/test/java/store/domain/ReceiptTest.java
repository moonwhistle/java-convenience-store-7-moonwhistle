package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.service.PromotionCalculator;
import store.domain.vo.ProductQuantity;
import store.view.dto.request.PromotionRequest;

@SuppressWarnings("NonAsciiCharacters")
class ReceiptTest {

    private static final String PRODUCTS_FILE_PATH = "/Users/moon/Desktop/java-convenience-store-7-moonwhistle/src/main/resources/products.md";
    private static final String PROMOTION_FILE_PATH = "/Users/moon/Desktop/java-convenience-store-7-moonwhistle/src/main/resources/promotions.md";

    private Products products;
    private BuyProducts buyProducts;
    private PromotionCalculator promotionCalculator;
    private List<PromotionRequest> promotionRequests;

    @BeforeEach
    void setUp() {
        FileReader promotionsContents = new FileReader(PROMOTION_FILE_PATH);
        Promotions promotions = new Promotions(promotionsContents.fileContents(), LocalDateTime.now());

        FileReader productsContents = new FileReader(PRODUCTS_FILE_PATH);
        products = new Products(productsContents.fileContents(), promotions);

        Map<String, String> boughtItems = Map.of("콜라", "3", "사이다", "3", "탄산수", "2");
        buyProducts = new BuyProducts(boughtItems, promotions, products);

        promotionRequests = List.of(
                new PromotionRequest("콜라", false, false),
                new PromotionRequest("사이다", false, false),
                new PromotionRequest("탄산수", false, true)
        );
        promotionCalculator = new PromotionCalculator();
    }

    @Test
    void 사용자_요청에_따라_영수증이_정확하게_생성된다() {
        // given
        Receipt receipt = new Receipt(promotionCalculator, products, buyProducts, promotionRequests);

        // when
        Map<Product, ProductQuantity> actualReceipt = receipt.getReceipt();

        // then
        assertThat(actualReceipt).hasSize(3);

        Product colaProduct = products.getPromotionProducts("콜라").getFirst();
        assertThat(colaProduct.getQuantity()).isEqualTo(7);

        Product ciderProduct = products.getPromotionProducts("사이다").getFirst();
        assertThat(ciderProduct.getQuantity()).isEqualTo(5);

        Product sparklingWaterProduct = products.getPromotionProducts("탄산수").getFirst();
        assertThat(sparklingWaterProduct.getQuantity()).isEqualTo(2);
    }
}
