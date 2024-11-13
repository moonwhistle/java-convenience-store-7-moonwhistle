package store.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.FileReader;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.vo.ProductQuantity;
import store.domain.vo.PromotionType;
import store.view.dto.request.MemberShipRequest;

@SuppressWarnings("NonAsciiCharacters")
class MemberShipCalculatorTest {

    private static final String PROMOTION_FILE_PATH = "/Users/moon/Desktop/java-convenience-store-7-moonwhistle/src/main/resources/promotions.md";

    private MemberShipCalculator memberShipCalculator;
    private Map<Product, ProductQuantity> receipt;

    @BeforeEach
    void setUp() {
        memberShipCalculator = new MemberShipCalculator();
        FileReader promotionsContents = new FileReader(PROMOTION_FILE_PATH);
        Promotions promotions = new Promotions(promotionsContents.fileContents(), LocalDateTime.now());

        Product nonPromotionProduct1 = new Product("사이다", "1000", "5", Promotion.noPromotion());
        Product nonPromotionProduct2 = new Product("물", "500", "10", Promotion.noPromotion());
        Product promotionProduct = new Product("콜라", "1500", "4", promotions.getPromotionByType(PromotionType.from("탄산2+1")));

        receipt = Map.of(
                nonPromotionProduct1, ProductQuantity.from("5"),
                nonPromotionProduct2, ProductQuantity.from("10"),
                promotionProduct, ProductQuantity.from("4")
        );
    }

    @Test
    void 멤버십_할인이_적용되지_않는_경우_할인금액이_0이다() {
        // given
        MemberShipRequest noMembershipRequest = new MemberShipRequest(false);

        // when
        int discountAmount = memberShipCalculator.calculateMemberShip(receipt, noMembershipRequest);

        // then
        assertThat(discountAmount).isEqualTo(0);
    }

    @Test
    void 멤버십_할인이_적용되는_경우_올바른_할인금액을_계산한다() {
        // given
        MemberShipRequest membershipRequest = new MemberShipRequest(true);
        int expectedDiscount = (int) (1000 * 5 * 0.3) + (int) (500 * 10 * 0.3);

        // when
        int discountAmount = memberShipCalculator.calculateMemberShip(receipt, membershipRequest);

        // then
        assertThat(discountAmount).isEqualTo(expectedDiscount);
    }

    @Test
    void 멤버십_할인이_8000원_초과_시_최대할인_금액으로_제한된다() {
        // given
        Product expensiveProduct = new Product("비싼상품", "50000", "1", Promotion.noPromotion());
        receipt = Map.of(expensiveProduct, ProductQuantity.from("1"));
        MemberShipRequest membershipRequest = new MemberShipRequest(true);

        // when
        int discountAmount = memberShipCalculator.calculateMemberShip(receipt, membershipRequest);

        // then
        assertThat(discountAmount).isEqualTo(8000);
    }
}
