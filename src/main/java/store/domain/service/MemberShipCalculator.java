package store.domain.service;

import java.util.Map;
import java.util.Map.Entry;
import store.domain.Product;
import store.domain.vo.ProductQuantity;
import store.view.dto.request.MemberShipRequest;

public class MemberShipCalculator {

    private static final int ZERO = 0;
    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    private static final int MAX_DISCOUNT_AMOUNT = 8000;


    public int calculateMemberShip(Map<Product, ProductQuantity> receipt, MemberShipRequest request) {
        if (request.isMemberShip()) {
            return calculateNoPromotionProduct(receipt);
        }
        return ZERO;
    }

    private int calculateNoPromotionProduct(Map<Product, ProductQuantity> receipt) {
        int memberShipDisCount = ZERO;
        for (Entry<Product, ProductQuantity> entry : receipt.entrySet()) {
            if (!entry.getKey().isPromotion()) {
                memberShipDisCount += (int) (entry.getKey().getPrice() * entry.getKey().getQuantity()
                        * MEMBERSHIP_DISCOUNT_RATE);
            }
        }
        return Math.min(memberShipDisCount, MAX_DISCOUNT_AMOUNT);
    }
}
