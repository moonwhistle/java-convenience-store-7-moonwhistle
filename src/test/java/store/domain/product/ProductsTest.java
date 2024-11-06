package store.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.product.vo.PromotionType;

@SuppressWarnings("NonAsciiCharacters")
class ProductsTest {

    private static Map<Product, PromotionType> productsData;

    @BeforeEach
    void setUp() {
        productsData = Map.of(
                new Product("콜라", "10"), PromotionType.from("탄산2+1"),
                new Product("빵", "10"), PromotionType.from("null"),
                new Product("사이다", "8"), PromotionType.from("탄산2+1"),
                new Product("껌", "7"), PromotionType.from("null"),
                new Product("오렌지주스", "9"), PromotionType.from("MD추천상품")
        );
    }

    @Test
    void 파일_경로에_해당하는_상품들을_생성한다() {
        // given
        String filePath = "/Users/moon/Desktop/java-convenience-store-7-moonwhistle/src/main/resources/test_products.md";
        Products products = new Products(filePath);
        Map<Product, PromotionType> fileProduct = products.products();
        List<String> fileProductName = fileProduct.keySet()
                .stream()
                .map(Product::name)
                .toList();
        List<String> fileProductQuantity = fileProduct.keySet()
                .stream()
                .map(Product::quantity)
                .toList();
        List<String> fileProductPromotionType = fileProduct.values()
                .stream()
                .map(PromotionType::promotionType)
                .toList();

        // when & then
        assertThat(fileProduct.size()).isEqualTo(productsData.size());
        productsData.forEach((testProduct, testPromotionType) -> {
            assertThat(fileProductName).contains(testProduct.name());
            assertThat(fileProductQuantity).contains(testProduct.quantity());
            assertThat(fileProductPromotionType).contains(testPromotionType.promotionType());
        });
    }
}
