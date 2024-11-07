package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@SuppressWarnings("NonAsciiCharacters")
class ProductsTest {

    private List<List<String>> productsData;
    private Promotions promotions;

    @BeforeEach
    void setUp() {
        LocalDateTime fixedLocalDateTime = LocalDate.parse("2024-11-07").atStartOfDay();
        productsData = List.of(
                List.of("콜라", "1000", "10", "탄산2+1"),
                List.of("콜라", "1000", "10", "null"),
                List.of("빵", "1000", "10", "null"),
                List.of("사이다", "1000", "8", "탄산2+1"),
                List.of("껌", "1000", "7", "null"),
                List.of("오렌지주스", "1800", "9", "MD추천상품")
        );
        promotions = new Promotions(
                List.of(
                        List.of("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"),
                        List.of("MD추천상품", "1", "1", "2024-01-01", "2024-12-31"),
                        List.of("null", "0", "0", "2024-01-01", "2024-12-31")
                ),
                fixedLocalDateTime
        );
    }

    @Test
    void 파일_데이터로_객체를_생성한다() {
        // given
        Products products = new Products(productsData, promotions);

        // when
        List<Product> actual = products.products();

        // when
        assertThat(actual.size()).isEqualTo(5);
    }

    @ParameterizedTest
    @CsvSource(value = {"콜라, 20", "사이다, 8","오렌지주스, 9"})
    void 상품_이름에_맞는_재고_개수를_가져온다(String name, int quantity) {
        // given
        Products products = new Products(productsData, promotions);

        // when
        int actual = products.productCountByName(name);

        // then
        assertThat(actual).isEqualTo(quantity);
    }
}
