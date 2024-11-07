package store.domain.vo;

import java.util.Objects;
import store.domain.exception.ProductErrorCode;
import store.domain.exception.ProductException;

public class ProductName {

    private static final String STRING_DELIMITER = "^[a-zA-Z가-힣]+$";

    private final String productName;


    public static ProductName from(String productName) {
        validateEmpty(productName);
        validateType(productName);
        return new ProductName(productName);
    }

    private ProductName(String productName) {
        this.productName = productName;
    }

    public String name() {
        return productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductName that = (ProductName) o;
        return productName.equals(that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName);
    }

    private static void validateEmpty(String quantity) {
        if (quantity == null || quantity.isBlank()) {
            throw new ProductException(ProductErrorCode.WRONG_INPUT);
        }
    }

    private static void validateType(String productName) {
        if (!productName.matches(STRING_DELIMITER)) {
            throw new ProductException(ProductErrorCode.NOT_MATCH_TYPE);
        }
    }
}
