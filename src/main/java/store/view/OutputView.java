package store.view;

import java.util.List;
import store.view.dto.response.BuyProductResponse;
import store.view.dto.response.ErrorResponse;
import store.view.dto.response.FreeProductResponse;
import store.view.dto.response.ProductCalculateResponse;

public class OutputView {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void welcomeStore() {
        System.out.println(LINE_SEPARATOR + "안녕하세요. W편의점입니다." + LINE_SEPARATOR + "현재 보유하고 있는 상품입니다." + LINE_SEPARATOR);
    }

    public void showInventory(String name, int price, String quantity, String promotionType) {
        System.out.printf(
                "- %s %,d원 %s %s"
                        + LINE_SEPARATOR,
                name, price, quantity, promotionType
        );
    }

    public void guideBuyProducts() {
        System.out.printf(
                LINE_SEPARATOR +
                "==============W 편의점================"
                        + LINE_SEPARATOR
                        + "%-10s %9s %9s%n"
                , "상품명", "수량" ,"금액"
        );
    }

    public void showBuyProducts(List<BuyProductResponse> buyProducts) {
        for (BuyProductResponse product : buyProducts) {
            System.out.printf(
                    "%-10s %9d %,14d" + LINE_SEPARATOR,
                    product.name(), product.quantity(), product.totalPrice()
            );
        }
    }

    public void guideFreeGetQuantity() {
        System.out.println("=============증\t\t정===============");
    }

    public void showFreeQuantity(List<FreeProductResponse> freeProductResponses) {
        for (FreeProductResponse freeProductResponse : freeProductResponses) {
            System.out.printf(
                    "%-10s %9d" + LINE_SEPARATOR,
                    freeProductResponse.name(), freeProductResponse.quantity()
            );
        }
    }

    public void showCalculateResult(ProductCalculateResponse calculateInfo) {
        System.out.printf(
                "====================================" + LINE_SEPARATOR
                        + "총구매액\t\t\t\t%d\t\t  %,d" + LINE_SEPARATOR
                        + "행사할인\t\t\t\t\t\t  -%,d" + LINE_SEPARATOR
                        + "멤버십할인\t\t\t\t\t\t  -%,d" + LINE_SEPARATOR
                        + "내실돈\t\t\t\t\t\t  %,d" + LINE_SEPARATOR
                , calculateInfo.buyCounts(), calculateInfo.buyProductsPrice(),
                 calculateInfo.promotionPrice(), calculateInfo.memberShip(), calculateInfo.totalPrice()
        );
    }

    public void showError(ErrorResponse errorResponse) {
        System.out.println(errorResponse.message());
    }
}
