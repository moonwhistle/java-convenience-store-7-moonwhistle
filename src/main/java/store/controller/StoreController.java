package store.controller;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.ArrayList;
import java.util.List;
import store.domain.BuyProductParser;
import store.domain.BuyProducts;
import store.domain.FileReader;
import store.domain.CustomerReceipt;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.Receipt;
import store.domain.service.MemberShipCalculator;
import store.domain.service.PromotionCalculator;
import store.domain.service.PromotionChecker;
import store.domain.vo.MemberShip;
import store.view.InputView;
import store.view.OutputView;
import store.view.dto.request.PromotionRequest;
import store.view.dto.response.ErrorResponse;
import store.view.dto.request.MemberShipRequest;
import store.view.dto.response.PromotionResponse;

public class StoreController {

    private static final String PRODUCTS_FILE_PATH = "/Users/moon/Desktop/java-convenience-store-7-moonwhistle/src/main/resources/products.md";
    private static final String PROMOTION_FILE_PATH = "/Users/moon/Desktop/java-convenience-store-7-moonwhistle/src/main/resources/promotions.md";
    private static final String YES = "Y";

    private final MemberShipCalculator memberShipCalculator;
    private final PromotionCalculator promotionCalculator;
    private final OutputView outputView;
    private final InputView inputView;

    public StoreController(MemberShipCalculator memberShipCalculator, PromotionCalculator promotionCalculator,
                           OutputView outputView, InputView inputView) {
        this.memberShipCalculator = memberShipCalculator;
        this.promotionCalculator = promotionCalculator;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        boolean continueShopping = true;

        Promotions promotions = getPromotions();
        Products products = getProducts(promotions);

        while (continueShopping) {
            showInventory(products);
            BuyProducts buyProducts = buyProducts(promotions, products);
            CustomerReceipt customerReceipt = makeCustomerReceipt(products, buyProducts);
            showReceipt(customerReceipt);
            continueShopping = inputView.continueShopping()
                    .equals(YES);
        }

    }

    private Promotions getPromotions() {
        FileReader promotionsContents = new FileReader(PROMOTION_FILE_PATH);
        return new Promotions(promotionsContents.fileContents(), DateTimes.now());
    }

    private Products getProducts(Promotions promotions) {
        FileReader productsContents = new FileReader(PRODUCTS_FILE_PATH);
        return new Products(productsContents.fileContents(), promotions);
    }

    private void showInventory(Products products) {
        outputView.welcomeStore();
        products.displayProducts()
                .forEach(product -> outputView.showInventory(
                        product.getName(),
                        product.getPrice(),
                        product.showQuantity(),
                        product.getPromotionType()
                ));
    }

    private CustomerReceipt makeCustomerReceipt(Products products, BuyProducts buyProducts) {
        List<PromotionRequest> requests = checkPromotionCase(products, buyProducts);

        Receipt receipt = new Receipt(promotionCalculator, products, buyProducts, requests);
        MemberShipRequest request = new MemberShipRequest(inputView.disCountMemberShip().equals(YES));
        MemberShip memberShip = MemberShip.form(memberShipCalculator, receipt, request);
        return new CustomerReceipt(receipt, memberShip);
    }

    private List<PromotionRequest> checkPromotionCase(Products products, BuyProducts buyProducts) {
        PromotionChecker promotionChecker = new PromotionChecker();
        List<PromotionResponse> responses = promotionChecker.checkPromotion(products, buyProducts);
        List<PromotionRequest> requests = new ArrayList<>();
        for (PromotionResponse response : responses) {
            checkNoPromotionRequest(response, requests);
            checkFreePromotionRequest(response, requests);
        }
        return requests;
    }

    private void checkFreePromotionRequest(PromotionResponse response, List<PromotionRequest> requests) {
        if (response.isFreePromotion()) {
            boolean isFreePromotion = inputView.buyPromotionQuantity(response.name(),
                    response.freePromotionQuantity()).equals(YES);
            requests.add(new PromotionRequest(response.name(), false, isFreePromotion));
        }
    }

    private void checkNoPromotionRequest(PromotionResponse response, List<PromotionRequest> requests) {
        if (response.isNoPromotion()) {
            boolean isNoPromotion = inputView.notApplyPromotionQuantity(response.name(),
                    response.noPromotionQuantity()).equals(YES);
            requests.add(new PromotionRequest(response.name(), isNoPromotion, false));
        }
    }

    private void showReceipt(CustomerReceipt customerReceipt) {
        outputView.guideBuyProducts();
        outputView.showBuyProducts(customerReceipt.showBuyProducts());
        outputView.guideFreeGetQuantity();
        outputView.showFreeQuantity(customerReceipt.showFreeProductInfo());
        outputView.showCalculateResult(customerReceipt.showCalculateInfo());
    }

    private BuyProducts buyProducts(Promotions promotions, Products products) {
        outputView.getCustomerProducts();
        while (true) {
            try {
                BuyProductParser parser = new BuyProductParser(inputView.buyProducts());
                return new BuyProducts(parser.getProducts(), promotions, products);
            } catch (IllegalArgumentException e) {
                ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
                outputView.showError(errorResponse);
            }
        }
    }
}
