package store;

import store.controller.StoreController;
import store.domain.service.MemberShipCalculator;
import store.domain.service.PromotionCalculator;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        OutputView outputView = new OutputView();
        InputView inputView = new InputView();
        MemberShipCalculator memberShipCalculator = new MemberShipCalculator();
        PromotionCalculator promotionCalculateService = new PromotionCalculator();
        StoreController storeController = new StoreController(memberShipCalculator, promotionCalculateService, outputView, inputView);
        storeController.run();
    }
}
