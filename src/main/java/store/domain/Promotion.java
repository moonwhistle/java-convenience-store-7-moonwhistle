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

    public static Promotion from(PromotionType promotionType, LocalDateTime currentDate, String buyQuantity, String getQuantity,
                     String startDate, String endDate) {

        return new Promotion(promotionType, ProductQuantity.from(buyQuantity), ProductQuantity.from(getQuantity), isPromotion(currentDate, startDate, endDate));
    }

    public static Promotion noPromotion() {
        return new Promotion(PromotionType.none(), ProductQuantity.none(), ProductQuantity.none(), false);
    }

    private Promotion(PromotionType promotionType, ProductQuantity buyQuantity, ProductQuantity getQuantity,
                      boolean isPromotion) {
        this.promotionType = promotionType;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.isPromotion = isPromotion;
    }

    public String getPromotionType() {
        return promotionType.getPromotionType();
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public int getBuyQuantity() {
        return buyQuantity.getQuantity();
    }

    public int getGettableQuantity() {
        return getQuantity.getQuantity();
    }

    public int getOnePromotionCycle() {
        return buyQuantity.getQuantity() + getQuantity.getQuantity();
    }

    private static boolean isPromotion(LocalDateTime currentDate, String startDate, String endDate) {
        LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate).atTime(LocalTime.MAX);

        return currentDate.isAfter(start) && currentDate.isBefore(end);
    }
}
