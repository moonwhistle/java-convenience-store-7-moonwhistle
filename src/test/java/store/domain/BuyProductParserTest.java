package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.domain.exception.ProductErrorCode;

@SuppressWarnings("NonAsciiCharacters")
class BuyProductParserTest {

    @ParameterizedTest
    @MethodSource("provideParsedDate")
    void 입력_형식에_알맞는_입력이_들어온다(String buyProduct, Map<String, String> expected) {
        // given
        BuyProductParser parser = new BuyProductParser(buyProduct);

        // when
        Map<String, String> actual = parser.getProducts();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideParsedDate() {
        return Stream.of(
                Arguments.of("[콜라-1000]", Map.of("콜라", "1000")),
                Arguments.of("[콜라-1000],[사이다-1000]", Map.of("콜라", "1000", "사이다", "1000"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void 입력_형식에_맞지_않는_입력이_들어온다(String buyProduct) {
        // when & then
        assertThatThrownBy(() -> new BuyProductParser(buyProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ProductErrorCode.WRONG_INPUT.getMessage());
    }

    private static Stream<Arguments> provideInvalidData() {
        return Stream.of(
                Arguments.of("콜라-1000"),    // 대괄호 없는 형식
                Arguments.of(""),            // 빈 입력
                Arguments.of("[콜라-]"),      // 수량이 비어있는 경우
                Arguments.of("[-1000]"),      // 상품명이 비어있는 경우
                Arguments.of("[콜라1000]"),    // 구분자(-)가 없는 경우
                Arguments.of("콜라"),          // 상품명 혹은 수량이 없는 경우
                Arguments.of("[콜라-1000")    // 대괄호가 하나 없는 경우
        );
    }
}
