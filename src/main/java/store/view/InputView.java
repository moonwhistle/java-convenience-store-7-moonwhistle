package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    public String buyProducts() {
        return Console.readLine();
    }

    public String notApplyPromotionQuantity(String name, int quantity) {
        System.out.printf(
                LINE_SEPARATOR + "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)" + LINE_SEPARATOR,
                name,
                quantity
        );
        return Console.readLine();
    }

    public String buyPromotionQuantity(String name, int quantity) {
        System.out.printf(
                LINE_SEPARATOR + "현재 %s(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)" + LINE_SEPARATOR,
                name,
                quantity
        );
        return Console.readLine();
    }

    public String disCountMemberShip() {
        System.out.println(LINE_SEPARATOR + "멤버십 할인을 받으시겠습니까? (Y/N)");
        return Console.readLine();
    }

    public String continueShopping() {
        System.out.println(LINE_SEPARATOR + "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return Console.readLine();
    }
}
