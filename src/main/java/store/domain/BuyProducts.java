package store.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BuyProducts {

    private final List<Product> products;

    public BuyProducts(Map<String, String> splitProducts) {
        this.products = createBuyProducts(splitProducts);
    }

    public List<Product> products() {
        return Collections.unmodifiableList(products);
    }

    private List<Product> createBuyProducts(Map<String, String> splitProducts) {
        List<Product> products = new ArrayList<>();

        for (Entry<String, String> entry : splitProducts.entrySet()) {
            Product product = new Product(entry.getKey(), entry.getValue());
            products.add(product);
        }

        return products;
    }
}
