package store.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import store.domain.exception.ProductErrorCode;
import store.domain.exception.ProductException;

public class BuyProducts {

    private final List<Product> products;

    public BuyProducts(Map<String, String> splitProducts, Promotions promotions, Products products) {
        this.products = createBuyProducts(splitProducts, promotions);
        validateExistence(products);
        validateQuantity(products);
    }

    public List<Product> products() {
        return Collections.unmodifiableList(products);
    }

    private List<Product> createBuyProducts(Map<String, String> splitProducts, Promotions promotions) {
        List<Product> products = new ArrayList<>();

        for (Entry<String, String> entry : splitProducts.entrySet()) {
            Product product = new Product(
                    entry.getKey(),
                    ProductInfo.findPriceByName(entry.getKey()),
                    entry.getValue(),
                    promotions.getPromotionByType(PromotionInfo.findPromotionByName(entry.getKey())));
            products.add(product);
        }

        return products;
    }

    private void validateExistence(Products inventoryProducts) {
        for (Product buyProduct : products) {
            if (!inventoryProducts.getProducts().contains(buyProduct)) {
                throw new ProductException(ProductErrorCode.NOT_FOUND_PRODUCT_NAME);
            }
        }
    }

    private void validateQuantity(Products inventoryProducts) {
        for (Product buyProduct : products) {
            int availableQuantity = inventoryProducts.countProductsByName(buyProduct.getName());
            if (availableQuantity < buyProduct.getQuantity()) {
                throw new ProductException(ProductErrorCode.OVER_QUANTITY);
            }
        }
    }
}
