package store.domain;

import store.domain.vo.PromotionType;

public enum PromotionInfo {

    COKE("콜라", PromotionType.from("탄산2+1")),
    CIDER("사이다", PromotionType.from("탄산2+1")),
    ORANGE_JUICE("오렌지주스", PromotionType.from("MD추천상품")),
    SPARKLING_WATER("탄산수", PromotionType.from("탄산2+1")),
    POTATO_CHIPS("감자칩", PromotionType.from("반짝할인")),
    CHOCOLATE_BAR("초코바", PromotionType.from("MD추천상품")),
    CUP_NOODLES("컵라면", PromotionType.from("MD추천상품"));

    private final String name;
    private final PromotionType promotionType;

    PromotionInfo(String name, PromotionType promotionType) {
        this.name = name;
        this.promotionType = promotionType;
    }

    public static PromotionType findPromotionByName(String name) {
        for (PromotionInfo product : PromotionInfo.values()) {
            if (product.getName().equals(name)) {
                return product.getgetPromotionType();
            }
        }

        return PromotionType.none();
    }

    private String getName() {
        return name;
    }

    private PromotionType getgetPromotionType() {
        return promotionType;
    }
}
