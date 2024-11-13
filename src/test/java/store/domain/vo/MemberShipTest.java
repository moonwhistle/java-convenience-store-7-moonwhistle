package store.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.BuyProducts;
import store.domain.FileReader;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.Receipt;
import store.domain.service.MemberShipCalculator;
import store.domain.service.PromotionCalculator;
import store.view.dto.request.MemberShipRequest;
import store.view.dto.request.PromotionRequest;

@SuppressWarnings("NonAsciiCharacters")
class MemberShipTest {

    private static final String PRODUCTS_FILE_PATH = "/Users/moon/Desktop/java-convenience-store-7-moonwhistle/src/main/resources/products.md";
    private static final String PROMOTION_FILE_PATH = "/Users/moon/Desktop/java-convenience-store-7-moonwhistle/src/main/resources/promotions.md";

    private MemberShipCalculator memberShipCalculator;
    private Receipt receipt;

    @BeforeEach
    void setUp() {
        memberShipCalculator = new MemberShipCalculator();
        FileReader promotionsContents = new FileReader(PROMOTION_FILE_PATH);
        Promotions promotions = new Promotions(promotionsContents.fileContents(), LocalDateTime.now());

        FileReader productsContents = new FileReader(PRODUCTS_FILE_PATH);
        Products products = new Products(productsContents.fileContents(), promotions);

        List<PromotionRequest> promotionRequests = List.of(
                new PromotionRequest("콜라", false, false),
                new PromotionRequest("사이다", false, false),
                new PromotionRequest("탄산수", false, true)
        );

        Map<String, String> boughtItems = Map.of("콜라", "3", "사이다", "3", "탄산수", "2", "에너지바", "5");
        BuyProducts buyProducts = new BuyProducts(boughtItems, promotions, products);

        PromotionCalculator promotionCalculator = new PromotionCalculator();
        receipt = new Receipt(promotionCalculator, products, buyProducts, promotionRequests);
    }

    @Test
    void 멤버십_할인이_적용되지_않는_경우_할인금액이_0이다() {
        // given
        MemberShipRequest noMembershipRequest = new MemberShipRequest(false);

        // when
        MemberShip memberShip = MemberShip.form(memberShipCalculator, receipt, noMembershipRequest);

        // then
        assertThat(memberShip.getDisCountPrice()).isEqualTo(0);
    }

    @Test
    void 멤버십_할인이_적용되는_경우_올바른_할인금액을_계산한다() {
        // given
        MemberShipRequest membershipRequest = new MemberShipRequest(true);
        int expectedDiscount = (int) (2000 * 5 * 0.3);

        // when
        MemberShip memberShip = MemberShip.form(memberShipCalculator, receipt, membershipRequest);

        // then
        assertThat(memberShip.getDisCountPrice()).isEqualTo(expectedDiscount);
    }
}
