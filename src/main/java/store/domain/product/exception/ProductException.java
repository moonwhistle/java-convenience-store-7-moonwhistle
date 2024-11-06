package store.domain.product.exception;

public class ProductException extends IllegalArgumentException {

    public ProductException(ProductErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
