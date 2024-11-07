package store.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import store.domain.vo.ProductQuantity;
import store.domain.vo.PromotionType;

public class Promotion {

    private final PromotionType promotionType;
    private final ProductQuantity buyQuantity;
    private final ProductQuantity getQuantity;
    private final boolean isPromotion;

    public Promotion(PromotionType promotionType, LocalDateTime currentDate, String buyQuantity, String getQuantity,
                     String startDate, String endDate) {
        this.promotionType = promotionType;
        this.buyQuantity = ProductQuantity.from(buyQuantity);
        this.getQuantity = ProductQuantity.from(getQuantity);
        this.isPromotion = isPromotion(currentDate, startDate, endDate);
    }

    private Promotion(PromotionType promotionType, ProductQuantity buyQuantity, ProductQuantity getQuantity, boolean isPromotion) {
        this.promotionType = promotionType;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.isPromotion = isPromotion;
    }

    public static Promotion noPromotion() {
        return new Promotion (PromotionType.none(), ProductQuantity.none(), ProductQuantity.none(), false);
    }

    public String promotionType() {
        return promotionType.promotionType();
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    private boolean isPromotion(LocalDateTime currentDate, String startDate, String endDate) {
        LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate).atTime(LocalTime.MAX);

        return currentDate.isAfter(start) && currentDate.isBefore(end);
    }
}
