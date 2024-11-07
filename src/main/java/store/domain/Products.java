package store.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import store.domain.vo.PromotionType;

public class Products {

    private static final int QUANTITY_INDEX = 2;

    private final List<Product> products;

    public Products(List<List<String>> contents, Promotions promotions) {
        this.products = makeProducts(contents, promotions);
    }

    public int productCountByName(String name) {
        List<Product> sameNameProducts = productsByName(name);

        return sameNameProducts.stream()
                .mapToInt(Product::quantity)
                .sum();
    }

    private List<Product> productsByName(String name) {
        List<Product> sameNameProducts = new ArrayList<>();

        for (Product product : products) {
            if (product.name().equals(name)) {
                sameNameProducts.add(product);
            }
        }

        return sameNameProducts;
    }

    public List<Product> products() {
        return Collections.unmodifiableList(products);
    }

    private List<Product> makeProducts(List<List<String>> contents, Promotions promotions) {
        List<Product> products = new ArrayList<>();

        for (List<String> content : contents) {
            Promotion promotion = promotions.getPromotionByType(PromotionType.from(content.getLast()));
            Product product = new Product(content.getFirst(), content.get(1), content.get(QUANTITY_INDEX), promotion);
            products.add(product);
        }

        return products;
    }
}
