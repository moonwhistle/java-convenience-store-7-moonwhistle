package store.domain.product;

import store.domain.product.exception.ProductErrorCode;
import store.domain.product.exception.ProductException;

public enum ProductInfo {

    COKE("콜라", 1000),
    CIDER("사이다", 1000),
    ORANGE_JUICE("오렌지주스", 1800),
    SPARKLING_WATER("탄산수", 1200),
    WATER("물", 500),
    VITAMIN_WATER("비타민워터", 1500),
    POTATO_CHIPS("감자칩", 1500),
    CHOCOLATE_BAR("초코바", 1200),
    ENERGY_BAR("에너지바", 2000),
    MEAL_BOX("정식도시락", 6400),
    CUP_NOODLES("컵라면", 1700);

    private final String name;
    private final int price;

    ProductInfo(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public static int findPriceByName(String name) {
        for (ProductInfo product : ProductInfo.values()) {
            if (product.getName().equals(name)) {
                return product.getPrice();
            }
        }
        throw new ProductException(ProductErrorCode.NOT_FOUND_PRODUCT_NAME);
    }

    private String getName() {
        return name;
    }

    private int getPrice() {
        return price;
    }
}
