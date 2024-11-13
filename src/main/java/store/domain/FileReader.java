package store.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import store.domain.exception.ProductErrorCode;
import store.domain.exception.ProductException;

public class FileReader {

    private static final String CONTENT_SPLIT_DELIMITER = ",";

    private final List<List<String>> fileContents;

    public FileReader(String filePath) {
        this.fileContents = makeFileContents(filePath);
    }

    public List<List<String>> fileContents() {
        return Collections.unmodifiableList(fileContents);
    }

    private List<List<String>> makeFileContents(String filePath) {
        List<List<String>> fileContents = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new java.io.FileReader(filePath))) {
            skipOneLine(fileReader);
            makeFileContent(fileReader, fileContents);
        } catch (IOException e) {
            throw new ProductException(ProductErrorCode.NOT_FOUND_FILE_PATH);
        }

        return fileContents;
    }

    private void makeFileContent(BufferedReader fileReader, List<List<String>> fileContents) throws IOException {
        String line;

        while ((line = fileReader.readLine()) != null) {
            List<String> contentInfo = splitProducts(line);
            fileContents.add(contentInfo);
        }
    }

    private void skipOneLine(BufferedReader fileReader) throws IOException {
        fileReader.readLine();
    }

    private List<String> splitProducts(String productInformation) {
        return List.of(productInformation.split(CONTENT_SPLIT_DELIMITER));
    }
}
