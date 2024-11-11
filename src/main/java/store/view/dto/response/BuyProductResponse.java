package store.view.dto.response;

public record BuyProductResponse(
        String name,
        int quantity,
        int totalPrice
) {
}
