package store.domain;

import java.util.List;
import store.domain.exception.ProductErrorCode;
import store.domain.exception.ProductException;

public class ProductValidator {

    private final Products products;

    public ProductValidator(Products products) {
        this.products = products;
    }

    public void validateExistence(List<Product> buyProducts) {
        for (Product buyProduct : buyProducts) {
            if (!products.products().contains(buyProduct)) {
                throw new ProductException(ProductErrorCode.NOT_FOUND_PRODUCT_NAME);
            }
        }
    }

    public void validateQuantity(List<Product> buyProducts) {
        for (Product buyProduct : buyProducts) {
            int availableQuantity = products.productCountByName(buyProduct.name());
            if (availableQuantity < buyProduct.quantity()) {
                throw new ProductException(ProductErrorCode.OVER_QUANTITY);
            }
        }
    }
}
