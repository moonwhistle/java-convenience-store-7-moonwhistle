package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.service.MemberShipCalculator;
import store.domain.service.PromotionCalculator;
import store.domain.vo.MemberShip;
import store.view.dto.request.MemberShipRequest;
import store.view.dto.request.PromotionRequest;
import store.view.dto.response.ProductCalculateResponse;

@SuppressWarnings("NonAsciiCharacters")
class CustomerReceiptTest {

    private static final String PRODUCTS_FILE_PATH = "/Users/moon/Desktop/java-convenience-store-7-moonwhistle/src/main/resources/products.md";
    private static final String PROMOTION_FILE_PATH = "/Users/moon/Desktop/java-convenience-store-7-moonwhistle/src/main/resources/promotions.md";

    private Receipt receipt;
    private MemberShipCalculator memberShipCalculator;

    @BeforeEach
    void setUp() {
        PromotionCalculator promotionCalculator = new PromotionCalculator();

        FileReader promotionsContents = new FileReader(PROMOTION_FILE_PATH);
        Promotions promotions = new Promotions(promotionsContents.fileContents(), LocalDateTime.now());

        FileReader productsContents = new FileReader(PRODUCTS_FILE_PATH);
        Products products = new Products(productsContents.fileContents(), promotions);

        Map<String, String> boughtItems = Map.of("콜라", "3", "사이다", "3", "탄산수", "2", "에너지바", "5");
        BuyProducts buyProducts = new BuyProducts(boughtItems, promotions, products);

        List<PromotionRequest> promotionRequests = List.of(
                new PromotionRequest("콜라", false, false),
                new PromotionRequest("사이다", false, false),
                new PromotionRequest("탄산수", false, true)
        );

        receipt = new Receipt(promotionCalculator, products, buyProducts, promotionRequests);
        memberShipCalculator = new MemberShipCalculator();
    }

    @Test
    void 계산서가_정확하게_생성된다() {
        // given
        MemberShip memberShipDiscount = MemberShip.form(memberShipCalculator, receipt, new MemberShipRequest(false));
        CustomerReceipt customerReceipt = new CustomerReceipt(receipt, memberShipDiscount);

        // when
        ProductCalculateResponse calculateResponse = customerReceipt.showCalculateInfo();

        // then
        assertThat(calculateResponse.buyCounts()).isEqualTo(14);
        assertThat(calculateResponse.buyProductsPrice()).isEqualTo(19600);
        assertThat(calculateResponse.promotionPrice()).isEqualTo(3200);
        assertThat(calculateResponse.memberShip()).isEqualTo(0);
        assertThat(calculateResponse.totalPrice()).isEqualTo(16400);
    }
}
