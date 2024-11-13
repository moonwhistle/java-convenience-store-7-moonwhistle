package store.domain;

import java.util.Objects;
import store.domain.vo.ProductName;
import store.domain.vo.ProductPrice;
import store.domain.vo.ProductQuantity;

public class Product {

    private final ProductName name;
    private final ProductPrice price;
    private final ProductQuantity quantity;
    private final Promotion promotion;

    public Product(String name, String price, String quantity, Promotion promotion) {
        this.name = ProductName.from(name);
        this.price = ProductPrice.from(price);
        this.quantity = ProductQuantity.from(quantity);
        this.promotion = promotion;
    }

    public String getName() {
        return name.getName();
    }

    public int getPrice() {
        return price.getPrice();
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }

    public String showQuantity() {
        return quantity.showQuantity();
    }

    public String getPromotionType() {
        return promotion.getPromotionType();
    }

    public boolean isPromotion() {
        return promotion.isPromotion();
    }

    public int getPromotionBuyQuantity() {
        return promotion.getBuyQuantity();
    }

    public int getPromotionGettableQuantity() {
        return promotion.getGettableQuantity();
    }

    public int getPromotionQuantity() {
        return (quantity.getQuantity() / getOnePromotionCycle()) * promotion.getGettableQuantity();
    }

    public int getOnePromotionCycle() {
        return promotion.getOnePromotionCycle();
    }

    public void buyProduct(int buyQuantity) {
        quantity.minusQuantity(buyQuantity);
    }

    public void freeProduct(int addQuantity) {
        quantity.addQuantity(addQuantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product that = (Product) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
