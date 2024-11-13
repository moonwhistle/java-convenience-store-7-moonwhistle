package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.domain.vo.PromotionType;

@SuppressWarnings("NonAsciiCharacters")
class ProductTest {

    private Promotions promotions;

    @BeforeEach
    void setUp() {
        LocalDateTime fixedLocalDateTime = LocalDate.parse("2024-11-07").atStartOfDay();
         promotions = new Promotions(
                List.of(
                        List.of("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"),
                        List.of("MD추천상품", "1", "1", "2024-01-01", "2024-12-31"),
                        List.of("null", "0", "0", "2024-01-01", "2024-10-31")
                ),
                fixedLocalDateTime
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"콜라, 1000, 500", "사이다, 1000, 500", "과자, 2000, 1000"})
    void 만들어진_상품이_올바른_값을_반환한다(String name, String quantity, String price) {
        // given
        Product product = new Product(name, price, quantity, Promotion.noPromotion());

        // when & then
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.showQuantity()).isEqualTo(quantity + "개");
        assertThat(product.getPrice()).isEqualTo(Integer.parseInt(price));
        assertThat(product.getPromotionType()).isEqualTo("");
    }

    @Test
    void 같은_이름을_가진_상품은_동일하다고_인식한다() {
        // given
        Product product1 = new Product("콜라", "1000", "10", Promotion.noPromotion());
        Product product2 = new Product("콜라", "2000", "5", Promotion.noPromotion());

        // when & then
        assertThat(product1).isEqualTo(product2);
        assertThat(product1.hashCode()).isEqualTo(product2.hashCode());
    }

    @ParameterizedTest
    @CsvSource(value = {"10, 3, 7", "5, 5, 0"})
    void 상품을_구매하면_수량이_감소한다(String initialQuantity, String buyQuantity, String expectedRemaining) {
        // given
        Product product = new Product("콜라", "1000", initialQuantity, Promotion.noPromotion());

        // when
        product.buyProduct(Integer.parseInt(buyQuantity));

        // then
        assertThat(product.getQuantity()).isEqualTo(Integer.parseInt(expectedRemaining));
    }

    @ParameterizedTest
    @CsvSource(value = {"10, 3, 13", "0, 5, 5"})
    void 무료_상품을_추가하면_수량이_증가한다(String initialQuantity, String addQuantity, String expectedTotal) {
        // given
        Product product = new Product("콜라", "1000", initialQuantity, Promotion.noPromotion());

        // when
        product.freeProduct(Integer.parseInt(addQuantity));

        // then
        assertThat(product.getQuantity()).isEqualTo(Integer.parseInt(expectedTotal));
    }

    @Test
    void 프로모션이_있는_경우_프로모션_관련_정보를_확인한다() {
        // given
        Promotion promotion = promotions.getPromotionByType(PromotionType.from("탄산2+1"));
        Product product = new Product("콜라", "1000", "10", promotion);

        // when & then
        assertThat(product.isPromotion()).isTrue();
        assertThat(product.getPromotionBuyQuantity()).isEqualTo(2);
        assertThat(product.getPromotionGettableQuantity()).isEqualTo(1);
        assertThat(product.getPromotionQuantity()).isEqualTo(3);
    }
}
