package store.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import store.domain.vo.MemberShip;
import store.domain.vo.ProductQuantity;
import store.view.dto.response.BuyProductResponse;
import store.view.dto.response.FreeProductResponse;
import store.view.dto.response.ProductCalculateResponse;

public class CustomerReceipt {

    private final Receipt receipt;
    private final MemberShip memberShipDiscount;

    public CustomerReceipt(Receipt receipt, MemberShip memberShipDiscount) {
        this.receipt = receipt;
        this.memberShipDiscount = memberShipDiscount;
    }

    public List<BuyProductResponse> showBuyProducts() {
        List<BuyProductResponse> products = new ArrayList<>();
        for (Entry<Product, ProductQuantity> entry : receipt.getReceipt().entrySet()) {
            products.add(new BuyProductResponse(entry.getKey().getName(), entry.getKey().getQuantity(),
                    entry.getKey().getQuantity() * entry.getKey().getPrice()));
        }
        return Collections.unmodifiableList(products);
    }

    public List<FreeProductResponse> showFreeProductInfo() {
        List<FreeProductResponse> freeProductResponses = new ArrayList<>();
        for (Entry<Product, ProductQuantity> entry : receipt.getReceipt().entrySet()) {
            freeProductResponses.add(
                    new FreeProductResponse(entry.getKey().getName(), entry.getValue().getQuantity())
            );
        }
        return Collections.unmodifiableList(freeProductResponses);
    }

    public ProductCalculateResponse showCalculateInfo() {
        return new ProductCalculateResponse(
                productsAllQuantity(),
                productsAllPrice(),
                promotionPrice(),
                memberShipDiscount.getDisCountPrice(),
                totalPrice()
        );
    }

    private int totalPrice() {
        return productsAllPrice() - promotionPrice() - memberShipDiscount.getDisCountPrice();
    }

    private int productsAllPrice() {
        return receipt.getReceipt()
                .keySet()
                .stream()
                .mapToInt(product -> product.getPrice() * product.getQuantity())
                .sum();
    }

    private int promotionPrice() {
        return receipt.getReceipt()
                .entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue().getQuantity())
                .sum();
    }

    private int productsAllQuantity() {
        return receipt.getReceipt()
                .keySet()
                .stream()
                .mapToInt(Product::getQuantity)
                .sum();
    }
}
