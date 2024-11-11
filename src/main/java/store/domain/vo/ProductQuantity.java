package store.domain.vo;

import store.domain.exception.ProductErrorCode;
import store.domain.exception.ProductException;

public class ProductQuantity {

    private static final int ZERO = 0;
    private static final String ZERO_QUANTITY = "재고 없음";
    private static final String NUMBER_DELIMITER = "^[0-9]+$";
    private static final String INDICATE_SHOW_QUANTITY = "개";

    private int quantity;

    public static ProductQuantity from(String quantity) {
        validateEmpty(quantity);
        validateType(quantity);
        return new ProductQuantity(Integer.parseInt(quantity));
    }

    public static ProductQuantity none() {
        return new ProductQuantity(ZERO);
    }

    private ProductQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void minusQuantity(int buyQuantity) {
       quantity -= buyQuantity;
    }

    public void addQuantity(int freeQuantity) {
        quantity += freeQuantity;
    }

    public String showQuantity() {
        if (quantity == ZERO) {
            return ZERO_QUANTITY;
        }

        return quantity + INDICATE_SHOW_QUANTITY;
    }

    private static void validateEmpty(String quantity) {
        if (quantity == null || quantity.isBlank()) {
            throw new ProductException(ProductErrorCode.WRONG_INPUT);
        }
    }

    private static void validateType(String quantity) {
        if (!quantity.matches(NUMBER_DELIMITER)) {
            throw new ProductException(ProductErrorCode.NOT_MATCH_TYPE);
        }
    }
}
