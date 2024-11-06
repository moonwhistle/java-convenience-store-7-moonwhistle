package store.domain.product.exception;

public enum ProductErrorCode {

    NOT_FOUND_FILE_PATH("[ERROR] 파일을 읽는 도중 문제가 발생했습니다."),
    NOT_FOUND_PRODUCT_NAME("[ERROR] 해당 이름의 상품이 존재하지 않습니다."),
    NOT_FOUND_PROMOTION_TYPE("[ERROR] 프로모션 타입을 확인할 수 없습니다."),
    EMPTY_QUANTITY("[ERROR] 재고 수량을 불러올 수 없습니다."),
    NOT_MATCH_TYPE("[ERROR] 할당된 값의 형식이 올바르지 않습니다.");

    private final String message;

    ProductErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
