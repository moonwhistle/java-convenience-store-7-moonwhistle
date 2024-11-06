package store.domain.product.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.product.exception.ProductErrorCode;

@SuppressWarnings("NonAsciiCharacters")
class ProductNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"콜라", "사이다", "coke", "cider"})
    void 올바른_상품_이름을_반환한다(String name){
        // given
        ProductName productName = ProductName.from(name);

        // when
        String actual = productName.name();

        // then
        assertThat(actual).isEqualTo(name);
    }

    @Nested
    class 예외_처리_테스트를_진행한다 {

        @ParameterizedTest
        @NullAndEmptySource
        void 빈_값이_들어온다(String name) {
            // when & then
            assertThatThrownBy(() -> ProductName.from(name))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ProductErrorCode.NOT_FOUND_PRODUCT_NAME.getMessage());
        }

        @ParameterizedTest
        @ValueSource(strings = {"1", "+", "43", "-1"})
        void 이름_형식이_아닌_값이_들어온다(String name) {
            // when & then
            assertThatThrownBy(() -> ProductName.from(name))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ProductErrorCode.NOT_MATCH_TYPE.getMessage());
        }
    }
}
