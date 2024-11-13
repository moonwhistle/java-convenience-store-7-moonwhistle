package store.domain.vo;

import store.domain.Receipt;
import store.domain.service.MemberShipCalculator;
import store.view.dto.request.MemberShipRequest;

public class MemberShip {

    private final int disCountPrice;

    public static MemberShip form(MemberShipCalculator calculator, Receipt receipt, MemberShipRequest request) {
        return new MemberShip(calculator.calculateMemberShip(receipt.getReceipt(), request));
    }

    private MemberShip(int disCountPrice) {
        this.disCountPrice = disCountPrice;
    }

    public int getDisCountPrice() {
        return disCountPrice;
    }
}
