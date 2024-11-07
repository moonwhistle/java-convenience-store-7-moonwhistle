package store.domain.exception;

public enum ProductErrorCode {

    NOT_FOUND_FILE_PATH("[ERROR] 파일을 읽는 도중 문제가 발생했습니다."),
    NOT_FOUND_PRODUCT_NAME("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    OVER_QUANTITY("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    NOT_MATCH_TYPE("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    WRONG_INPUT("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");

    private final String message;

    ProductErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
