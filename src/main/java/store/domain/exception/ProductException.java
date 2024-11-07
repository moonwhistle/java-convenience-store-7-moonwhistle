package store.domain.exception;

public class ProductException extends IllegalArgumentException {

    public ProductException(ProductErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
