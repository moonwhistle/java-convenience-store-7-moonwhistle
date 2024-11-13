package store.domain.service;

import java.util.ArrayList;
import java.util.List;
import store.domain.BuyProducts;
import store.domain.Product;
import store.domain.Products;
import store.view.dto.response.PromotionResponse;

public class PromotionChecker {

    private static final int ZERO = 0;

    public List<PromotionResponse> checkPromotion(Products products, BuyProducts buyProducts) {
        List<PromotionResponse> promotionResponses = new ArrayList<>();
        for (Product buyProduct : buyProducts.products()) {
            checkPromotionCase(products, buyProduct, promotionResponses);
        }
        return promotionResponses;
    }

    private void checkPromotionCase(Products products, Product buyProduct, List<PromotionResponse> promotionResponses) {
        if (buyProduct.isPromotion()) {
            Product promotionProduct = products.getPromotionProducts(buyProduct.getName()).getFirst();
            checkNoPromotion(buyProduct, promotionProduct, promotionResponses);
            checkFreePromotion(buyProduct, promotionResponses);
        }
    }

    private void checkFreePromotion(Product buyProduct, List<PromotionResponse> promotionResponses) {
        if (buyProduct.getQuantity() % buyProduct.getOnePromotionCycle() == buyProduct.getPromotionBuyQuantity()) {
            promotionResponses.add(
                    new PromotionResponse(buyProduct.getName(), false, ZERO, true, buyProduct.getPromotionGettableQuantity())
            );
        }
    }

    private void checkNoPromotion(Product buyProduct, Product promotionProduct, List<PromotionResponse> promotionResponses) {
        if (buyProduct.getQuantity() > promotionProduct.getQuantity()) {
            int noPromotionQuantity = buyProduct.getQuantity() - promotionProduct.getOnePromotionCycle() * promotionProduct.getPromotionQuantity();
            promotionResponses.add(
                    new PromotionResponse(buyProduct.getName(), true, noPromotionQuantity, false, ZERO)
            );
        }
    }
}
