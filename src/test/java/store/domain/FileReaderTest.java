package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class FileReaderTest {

    @Test
    void 파일_내용을_올바르게_읽는다() {
        // given
        String filePath = "/Users/moon/Desktop/java-convenience-store-7-moonwhistle/src/main/resources/test_products.md";
        FileReader fileReader = new FileReader(filePath);
        List<List<String>> expectedContents = List.of(
                List.of("콜라", "1000", "10", "탄산2+1"),
                List.of("콜라", "1000", "10", "null"),
                List.of("빵", "1000", "10", "null"),
                List.of("사이다", "1000", "8", "탄산2+1"),
                List.of("껌", "1000", "7", "null"),
                List.of("오렌지주스", "1800", "9", "MD추천상품")
        );

        // when
        List<List<String>> actualContents = fileReader.fileContents();

        // then
        assertThat(actualContents).isEqualTo(expectedContents);
    }
}
