package store.view;

public class OutputView {

    public void welcomeStore() {
        System.out.println("안녕하세요. W편의점입니다." + System.lineSeparator() + "현재 보유하고 있는 상품입니다." + System.lineSeparator());
    }

    public void showInventory(String name, int price, String quantity, String promotionType) {
        System.out.printf("- %s %,d %s개 %s" + System.lineSeparator(), name, price, quantity, promotionType);
    }
}
