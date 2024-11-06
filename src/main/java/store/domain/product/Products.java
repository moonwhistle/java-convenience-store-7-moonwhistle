package store.domain.product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.product.vo.PromotionType;
import store.domain.product.exception.ProductErrorCode;
import store.domain.product.exception.ProductException;

public class Products {

    private static final String PRODUCTS_SPLIT_DELIMITER = ",";
    private static final int QUANTITY_INDEX = 2;

    private final Map<Product, PromotionType> products;

    public Products(String filePath) {
        this.products = makeProducts(filePath);
    }

    public Map<Product, PromotionType> products() {
        return Collections.unmodifiableMap(products);
    }

    private Map<Product, PromotionType> makeProducts(String filePath) {
        Map<Product, PromotionType> products = new LinkedHashMap<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            skipOneLine(fileReader);
            makeProduct(fileReader, products);
        } catch (IOException e) {
            throw new ProductException(ProductErrorCode.NOT_FOUND_FILE_PATH);
        }

        return products;
    }

    private void makeProduct(BufferedReader fileReader, Map<Product, PromotionType> products) throws IOException {
        String line;

        while ((line = fileReader.readLine()) != null) {
            List<String> productInfo = splitProducts(line);
            Product product = new Product(productInfo.getFirst(), productInfo.get(QUANTITY_INDEX));
            PromotionType promotionType = PromotionType.from(productInfo.getLast());
            products.put(product, promotionType);
        }
    }

    private void skipOneLine(BufferedReader fileReader) throws IOException {
        fileReader.readLine();
    }

    private List<String> splitProducts(String productInformation) {
        return List.of(productInformation.split(PRODUCTS_SPLIT_DELIMITER));
    }
}
