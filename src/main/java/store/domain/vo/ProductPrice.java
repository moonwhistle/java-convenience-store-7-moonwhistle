package store.domain.vo;

import store.domain.exception.ProductErrorCode;
import store.domain.exception.ProductException;

public class ProductPrice {

    private static final int ZERO = 0;
    private static final String NUMBER_DELIMITER = "^[0-9]+$";

    private final int price;

    public static ProductPrice from(String price) {
        validateEmpty(price);
        validateType(price);
        return new ProductPrice(Integer.parseInt(price));
    }

    public static ProductPrice none() {
        return new ProductPrice(ZERO);
    }

    private ProductPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    private static void validateEmpty(String price) {
        if (price == null || price.isBlank()) {
            throw new ProductException(ProductErrorCode.WRONG_INPUT);
        }
    }

    private static void validateType(String price) {
        if (!price.matches(NUMBER_DELIMITER)) {
            throw new ProductException(ProductErrorCode.NOT_MATCH_TYPE);
        }
    }
}
