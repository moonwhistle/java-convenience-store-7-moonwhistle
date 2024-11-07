package store.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.exception.ProductErrorCode;

@SuppressWarnings("NonAsciiCharacters")
class ProductValidatorTest {

    private Products products;

    @BeforeEach
    void setUp() {
        LocalDateTime fixedLocalDateTime = LocalDate.parse("2024-11-07").atStartOfDay();

        List<List<String>> productsData = List.of(
                List.of("콜라", "1000", "10", "탄산2+1"),
                List.of("콜라", "1000", "10", "null"),
                List.of("빵", "1000", "10", "null"),
                List.of("사이다", "1000", "8", "탄산2+1"),
                List.of("껌", "1000", "7", "null"),
                List.of("오렌지주스", "1800", "9", "MD추천상품")
        );

        Promotions promotions = new Promotions(
                List.of(
                        List.of("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"),
                        List.of("MD추천상품", "1", "1", "2024-01-01", "2024-12-31"),
                        List.of("null", "0", "0", "2024-01-01", "2024-12-31")
                ),
                fixedLocalDateTime
        );

        products = new Products(productsData, promotions);
    }

    @Nested
    class 예외_테스트를_진행한다 {

        @Test
        void 입력받은_상품이_존재하지_않는다() {
            // given
            List<Product> buyProducts = List.of(
                    new Product("없는상품", "2")
            );
            ProductValidator productValidator = new ProductValidator(products);

            // when & then
            assertThatThrownBy(() -> productValidator.validateExistence(buyProducts))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ProductErrorCode.NOT_FOUND_PRODUCT_NAME.getMessage());
        }

        @Test
        void 입력받은_상품의_개수가_재고의_개수를_초과한다() {
            // given
            List<Product> buyProducts = List.of(
                    new Product("콜라", "21")
            );
            ProductValidator productValidator = new ProductValidator(products);

            // when & then
            assertThatThrownBy(() -> productValidator.validateQuantity(buyProducts))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ProductErrorCode.OVER_QUANTITY.getMessage());
        }
    }
}
