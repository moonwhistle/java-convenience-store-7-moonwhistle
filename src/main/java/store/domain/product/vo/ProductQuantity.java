package store.domain.product.vo;

import store.domain.product.exception.ProductErrorCode;
import store.domain.product.exception.ProductException;

public class ProductQuantity {

    private static final int ZERO = 0;
    private static final String ZERO_QUANTITY = "재고 없음";
    private static final String NUMBER_DELIMITER = "^[0-9]+$";

    private final int quantity;

    public static ProductQuantity from(String quantity) {
        validateEmpty(quantity);
        validateType(quantity);
        return new ProductQuantity(Integer.parseInt(quantity));
    }

    private ProductQuantity(int quantity) {
        this.quantity = quantity;
    }


    public String quantity() {
        if (quantity == ZERO) {
            return ZERO_QUANTITY;
        }

        return String.valueOf(quantity);
    }

    private static void validateEmpty(String quantity) {
        if (quantity == null || quantity.isBlank()) {
            throw new ProductException(ProductErrorCode.EMPTY_QUANTITY);
        }
    }

    private static void validateType(String quantity) {
        if (!quantity.matches(NUMBER_DELIMITER)) {
            throw new ProductException(ProductErrorCode.NOT_MATCH_TYPE);
        }
    }
}
