package store.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.service.PromotionCalculator;
import store.domain.vo.ProductQuantity;
import store.view.dto.request.PromotionRequest;

public class Receipt {

    private final Map<Product, ProductQuantity> receipt;

    public Receipt(PromotionCalculator calculateService, Products products, BuyProducts buyProducts, List<PromotionRequest> requests) {
        this.receipt = makeReceipt(calculateService, products, buyProducts, requests);
    }

    public Map<Product, ProductQuantity> getReceipt() {
        return Collections.unmodifiableMap(receipt);
    }

    private Map<Product, ProductQuantity> makeReceipt(PromotionCalculator promotionCalculator, Products products, BuyProducts buyProducts, List<PromotionRequest> requests) {
        Map<Product, ProductQuantity> receipt = new LinkedHashMap<>();

        for (Product buyProduct : buyProducts.products()) {
            addReceiptPromotion(promotionCalculator, products, buyProduct, receipt, requests);
            addReceiptNoPromotion(products, buyProduct, receipt);
        }

        return receipt;
    }

    private void addReceiptPromotion(PromotionCalculator promotionCalculator, Products products, Product buyProduct, Map<Product, ProductQuantity> receipt, List<PromotionRequest> requests) {
        if (buyProduct.isPromotion()) {
            Product promotionProduct = products.getPromotionProducts(buyProduct.getName()).getFirst();
            List<Product> noPromotionProduct = products.getNoPromotionProducts(buyProduct.getName());
            int promotionQuantity = promotionCalculator.calculatePromotion(buyProduct, promotionProduct, noPromotionProduct, requests);
            receipt.put(buyProduct, ProductQuantity.from(String.valueOf(promotionQuantity)));
        }
    }

    private void addReceiptNoPromotion(Products products, Product buyProduct, Map<Product, ProductQuantity> receipt) {
        if (!buyProduct.isPromotion()) {
            Product noPromotionProduct = products.getNoPromotionProducts(buyProduct.getName()).getFirst();
            noPromotionProduct.buyProduct(buyProduct.getQuantity());
            receipt.put(buyProduct, ProductQuantity.none());
        }
    }
}
