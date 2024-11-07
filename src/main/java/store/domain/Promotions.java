package store.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import store.domain.vo.PromotionType;

public class Promotions {

    private static final int BUY_INDEX = 1;
    private static final int FREE_GET_INDEX = 2;
    private static final int START_DATE_INDEX = 3;

    private final List<Promotion> promotions;

    public Promotions(List<List<String>> contents, LocalDateTime currentTime) {
        this.promotions = makePromotions(contents, currentTime);
    }

    public Promotion getPromotionByType(PromotionType type) {
        return promotions.stream()
                .filter(promotion -> promotion.promotionType().equals(type.promotionType()))
                .findFirst()
                .orElse(Promotion.noPromotion());
    }

    private List<Promotion> makePromotions(List<List<String>> contents, LocalDateTime currentTime) {
        List<Promotion> promotions = new ArrayList<>();
        makePromotion(contents, currentTime, promotions);
        return promotions;
    }

    private void makePromotion(List<List<String>> contents, LocalDateTime currentTime, List<Promotion> promotions) {
        for (List<String> content : contents) {
            Promotion promotion = new Promotion(
                    PromotionType.from(content.getFirst()),
                    currentTime,
                    content.get(BUY_INDEX),
                    content.get(FREE_GET_INDEX),
                    content.get(START_DATE_INDEX),
                    content.getLast()
            );
            promotions.add(promotion);
        }
    }
}
