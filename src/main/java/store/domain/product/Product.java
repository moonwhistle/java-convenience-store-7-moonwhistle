package store.domain.product;

import store.domain.product.vo.ProductName;
import store.domain.product.vo.ProductQuantity;

public class Product {

    private final ProductName name;
    private final ProductQuantity quantity;

    public Product(String name, String quantity) {
        this.name = ProductName.from(name);
        this.quantity = ProductQuantity.from(quantity);
    }

    public String name() {
        return name.name();
    }

    public String quantity() {
        return quantity.quantity();
    }
}
