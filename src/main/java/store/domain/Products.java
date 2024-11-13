package store.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.vo.PromotionType;

public class Products {

    private static final int ZERO_PRICE = 0;
    private static final int PRICE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    private final List<Product> products;

    public Products(List<List<String>> contents, Promotions promotions) {
        this.products = makeProducts(contents, promotions);
    }

    private List<Product> makeProducts(List<List<String>> contents, Promotions promotions) {
        List<Product> products = new ArrayList<>();

        for (List<String> content : contents) {
            Promotion promotion = promotions.getPromotionByType(PromotionType.from(content.getLast()));
            Product product = new Product(content.getFirst(), content.get(PRICE_INDEX), content.get(QUANTITY_INDEX), promotion);
            products.add(product);
        }

        return products;
    }

    public List<Product> getNoPromotionProducts(String name) {
        return getProductsByName(name).stream()
                .filter(product -> !product.isPromotion())
                .collect(Collectors.toList());
    }

    public List<Product> getPromotionProducts(String name) {
        return getProductsByName(name).stream()
                .filter(Product::isPromotion)
                .collect(Collectors.toList());
    }

    public int countProductsByName(String name) {
        return getProductsByName(name).stream()
                .mapToInt(Product::getQuantity)
                .sum();
    }

    public List<Product> displayProducts() {
        List<Product> displayedProducts = new ArrayList<>();
        for (Product product : products) {
            displayedProducts.add(product);
            getNoPromotionProducts(product, displayedProducts);
        }
        return displayedProducts;
    }

    public List<Product> getProducts() {
        return products;
    }

    private List<Product> getProductsByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .toList();
    }

    private void getNoPromotionProducts(Product product, List<Product> displayedProducts) {
        List<Product> noPromotionProducts = getNoPromotionProducts(product.getName());
        if (product.isPromotion() && noPromotionProducts.isEmpty()) {
            displayedProducts.add(new Product(
                    product.getName(),
                    String.valueOf(product.getPrice()),
                    String.valueOf(ZERO_PRICE),
                    Promotion.noPromotion()));
        }
    }
}
