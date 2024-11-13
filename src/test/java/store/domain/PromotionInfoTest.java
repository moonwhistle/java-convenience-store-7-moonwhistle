package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.exception.ProductErrorCode;
import store.domain.exception.ProductException;

@SuppressWarnings("NonAsciiCharacters")
class PromotionInfoTest {

    @ParameterizedTest
    @ValueSource(strings = {"콜라", "사이다", "오렌지주스", "정식도시락", "탄산수"})
    void 상품_이름으로_올바른_가격을_찾는다(String name) {
        // given
        List<String> expectedPrice = List.of(
                "1000", "1800", "1200", "1500", "2000", "6400", "1700"
        );

        // when
        String actualPrice = ProductInfo.findPriceByName(name);

        // then
        assertThat(expectedPrice).contains(actualPrice);
    }

    @Test
    void 존재하지_않는_이름을_찾으려_할_때_예외를_던진다() {
        // given
        String invalidProductName = "없는상품";

        // when & then
        assertThatThrownBy(() -> ProductInfo.findPriceByName(invalidProductName))
                .isInstanceOf(ProductException.class)
                .hasMessageContaining(ProductErrorCode.NOT_FOUND_PRODUCT_NAME.getMessage());
    }
}
