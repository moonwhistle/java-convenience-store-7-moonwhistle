package store.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.domain.exception.ProductErrorCode;
import store.domain.exception.ProductException;

public class BuyProductParser {

    private static final int NAME_INDEX = 1;
    private static final int PRICE_INDEX = 2;
    private static final String INPUT_DELIMITER = ",";
    private static final String ORDER_FORM = "\\[(.+)-(.+)]";

    private final Map<String, String> products;

    public BuyProductParser(String buyProduct) {
        this.products = makeProductsForm(buyProduct);
    }

    public Map<String, String> getProducts() {
        return Collections.unmodifiableMap(products);
    }

    private Map<String, String> makeProductsForm(String buyProducts) {
        Map<String, String> products = new LinkedHashMap<>();
        parseProducts(buyProducts, products);
        return products;
    }

    private void parseProducts(String buyProducts, Map<String, String> products) {
        Pattern pattern = Pattern.compile(ORDER_FORM);
        parseByRest(buyProducts).forEach(product -> {
            validateFrom(product);
            Matcher matcher = pattern.matcher(product);
            validateMatcher(matcher);
            products.put(matcher.group(NAME_INDEX).trim(), matcher.group(PRICE_INDEX).trim());
        });
    }

    private void validateMatcher(Matcher matcher) {
        if (!matcher.matches()) {
            throw new ProductException(ProductErrorCode.WRONG_INPUT);
        }
    }

    private List<String> parseByRest(String buyProduct) {
        if (buyProduct.contains(INPUT_DELIMITER)) {
            return List.of(buyProduct.split(INPUT_DELIMITER));
        }
        return List.of(buyProduct);
    }

    private void validateFrom(String buyProducts) {
        if (!buyProducts.matches(ORDER_FORM)) {
            throw new ProductException(ProductErrorCode.WRONG_INPUT);
        }
    }
}
