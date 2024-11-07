package store.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.exception.ProductErrorCode;

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

    @ParameterizedTest
    @ValueSource(strings = {"콜라", "사이다", "coke", "cider"})
    void 객체_필드값이_같으면_같은_인스턴스라고_판단한다(String name){
        // when
        ProductName one = ProductName.from(name);
        ProductName two = ProductName.from(name);

        // then
        assertThat(one).isEqualTo(two);
    }

    @Nested
    class 예외_처리_테스트를_진행한다 {

        @ParameterizedTest
        @NullAndEmptySource
        void 빈_값이_들어온다(String name) {
            // when & then
            assertThatThrownBy(() -> ProductName.from(name))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ProductErrorCode.WRONG_INPUT.getMessage());
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
