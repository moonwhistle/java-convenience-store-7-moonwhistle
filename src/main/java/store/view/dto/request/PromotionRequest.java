package store.view.dto.request;

public record PromotionRequest(
        String name,
        boolean isNoPromotion,
        boolean isFreePromotion
) {
}
