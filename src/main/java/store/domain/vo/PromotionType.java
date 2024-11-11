package store.domain.vo;

import java.util.Objects;
import store.domain.exception.ProductErrorCode;
import store.domain.exception.ProductException;

public class PromotionType {

    private static final String NULL_PROMOTION_TYPE = "null";
    private static final String INDICATED_NULL_PROMOTION_TYPE = "";

    private final String promotionType;

    public static PromotionType from(String promotionType) {
        validateEmpty(promotionType);
        return new PromotionType(checkPromotionType(promotionType));
    }

    public static PromotionType none() {
        return new PromotionType(INDICATED_NULL_PROMOTION_TYPE);
    }

    private PromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getPromotionType() {
        return promotionType;
    }

    private static void validateEmpty(String quantity) {
        if (quantity == null || quantity.isBlank()) {
            throw new ProductException(ProductErrorCode.WRONG_INPUT);
        }
    }

    private static String checkPromotionType(String promotionType) {
        if (Objects.equals(promotionType, NULL_PROMOTION_TYPE)) {
            return INDICATED_NULL_PROMOTION_TYPE;
        }

        return promotionType;
    }
}
