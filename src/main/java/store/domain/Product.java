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

    public Product(String name, String quantity) {
        this.name = ProductName.from(name);
        this.price = ProductPrice.none();
        this.quantity = ProductQuantity.from(quantity);
        this.promotion = Promotion.noPromotion();
    }

    public String name() {
        return name.name();
    }

    public int price() {
        return price.price();
    }

    public int quantity() {
        return quantity.quantity();
    }

    public String showQuantity() {
        return quantity.showQuantity();
    }

    public String promotionType() {
        return promotion.promotionType();
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
