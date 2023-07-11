package dbms.io.page;

import dbms.io.DiskSpaceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PageTest {

    @ParameterizedTest(name = "{0}번 page는 {1}이다")
    @MethodSource("providePageIdAndPageType")
    @DisplayName("주어진 page의 Page Header를 읽어 Page Type을 반환한다.")
    void getPageType1(long pageId, PageType expectedType) {
        // given
        DiskSpaceManager DSM = new DiskSpaceManager("src/test/resources/sample");
        Page page = DSM.readPage(pageId);

        // when
        PageType pageType = page.getPageType();

        // then
        assertThat(pageType).isEqualTo(expectedType);
    }

    private static Stream<Arguments> providePageIdAndPageType() {
        return Stream.of(
                Arguments.of(253l, PageType.INTERNAL_PAGE),
                Arguments.of(1l, PageType.LEAF_PAGE)
        );
    }


}