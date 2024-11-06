package store.domain.product.vo;

import store.domain.product.exception.ProductErrorCode;
import store.domain.product.exception.ProductException;

public class ProductName {

    private static final String STRING_DELIMITER = "^[a-zA-Z가-힣]+$";

    private final String productName;


    public static ProductName from(String productName) {
        validateEmpty(productName);
        validateType(productName);
        return new ProductName(productName);
    }

    public String name() {
        return productName;
    }

    private ProductName(String productName) {
        this.productName = productName;
    }

    private static void validateEmpty(String quantity) {
        if (quantity == null || quantity.isBlank()) {
            throw new ProductException(ProductErrorCode.NOT_FOUND_PRODUCT_NAME);
        }
    }

    private static void validateType(String productName) {
        if (!productName.matches(STRING_DELIMITER)) {
            throw new ProductException(ProductErrorCode.NOT_MATCH_TYPE);
        }
    }
}
