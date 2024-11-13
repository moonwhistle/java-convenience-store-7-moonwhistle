package store.view.dto.response;

public record PromotionResponse(
        String name,
        boolean isNoPromotion,
        int noPromotionQuantity,
        boolean isFreePromotion,
        int freePromotionQuantity
) {
}
