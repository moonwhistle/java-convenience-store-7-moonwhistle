package store.domain.service;

import java.util.List;
import store.domain.Product;
import store.view.dto.request.PromotionRequest;

public class PromotionCalculator {

    public int calculatePromotion(Product buyProduct, Product promotionProduct, List<Product> noPromotionProduct, List<PromotionRequest> requests) {
        if (buyProduct.getQuantity() > promotionProduct.getQuantity()) {
            int promotionQuantity = promotionProduct.getPromotionQuantity();
            int noPromotionQuantity = buyProduct.getQuantity() - promotionProduct.getOnePromotionCycle() * promotionProduct.getPromotionQuantity();
            return calculatePromotionOrRegular(buyProduct, promotionProduct, noPromotionProduct, noPromotionQuantity,
                    promotionQuantity, requests);
        }
        return calculateFreePromotion(buyProduct, promotionProduct, requests);
    }

    private int calculatePromotionOrRegular(Product buyProduct, Product promotionProduct, List<Product> noPromotionProduct, int noPromotionQuantity, int promotionQuantity, List<PromotionRequest> requests) {
        if (isNoPromotionRequested(buyProduct, requests)) {
            return buyNotApplyPromotion(buyProduct, promotionProduct, noPromotionProduct, promotionQuantity);
        }
        buyProduct.buyProduct(noPromotionQuantity);
        promotionProduct.buyProduct(buyProduct.getQuantity());
        return buyProduct.getPromotionQuantity();
    }

    private int buyNotApplyPromotion(Product buyProduct, Product promotionProduct, List<Product> noPromotionProduct, int promotionQuantity) {
        noPromotionProduct.getFirst().buyProduct(buyProduct.getQuantity() - promotionProduct.getQuantity());
        promotionProduct.buyProduct(promotionProduct.getQuantity());
        return promotionQuantity;
    }

    private int calculateFreePromotion(Product buyProduct, Product promotionProduct, List<PromotionRequest> requests) {
        if (buyProduct.getQuantity() % buyProduct.getOnePromotionCycle() == buyProduct.getPromotionBuyQuantity()) {
            return buyFreePromotion(buyProduct, promotionProduct, requests);
        }
        promotionProduct.buyProduct(buyProduct.getQuantity());
        return buyProduct.getQuantity() / buyProduct.getOnePromotionCycle();
    }

    private int buyFreePromotion(Product buyProduct, Product promotionProduct, List<PromotionRequest> requests) {
        if (isFreePromotionRequested(buyProduct, requests)) {
            return getFreePromotion(buyProduct, promotionProduct);
        }
        promotionProduct.buyProduct(buyProduct.getQuantity());
        return buyProduct.getQuantity() / buyProduct.getOnePromotionCycle();
    }

    private int getFreePromotion(Product buyProduct, Product promotionProduct) {
        promotionProduct.buyProduct(buyProduct.getPromotionGettableQuantity() + buyProduct.getQuantity());
        buyProduct.freeProduct(buyProduct.getPromotionGettableQuantity());
        return buyProduct.getPromotionQuantity();
    }

    private boolean isNoPromotionRequested(Product buyProduct, List<PromotionRequest> requests) {
        return requests.stream()
                .anyMatch(request -> request.name().equals(buyProduct.getName()) && request.isNoPromotion());
    }

    private boolean isFreePromotionRequested(Product buyProduct, List<PromotionRequest> requests) {
        return requests.stream()
                .anyMatch(request -> request.name().equals(buyProduct.getName()) && request.isFreePromotion());
    }
}
