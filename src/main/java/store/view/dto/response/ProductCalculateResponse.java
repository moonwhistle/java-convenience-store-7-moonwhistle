package store.view.dto.response;

public record ProductCalculateResponse(
        int buyCounts,
        int buyProductsPrice,
        int promotionPrice,
        int memberShip,
        int totalPrice
) {
}
