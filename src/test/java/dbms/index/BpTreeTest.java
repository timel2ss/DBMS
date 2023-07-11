package dbms.index;

import dbms.io.DiskSpaceManager;
import dbms.io.page.layout.HeaderPage;
import dbms.io.page.layout.leaf.LeafPage;
import dbms.table.Record;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BpTreeTest {

    @ParameterizedTest(name = "key: {0}, pageId: {1}")
    @MethodSource("provideKeyAndPageId")
    @DisplayName("search")
    void search(long key, long pageId) {
        // given
        DiskSpaceManager DSM = new DiskSpaceManager("src/test/resources/sample");
        HeaderPage headerPage = new HeaderPage(DSM.readPage(0l));
        BpTree bpTree = new BpTree(DSM, headerPage);

        // when
        Record record = bpTree.search(key).get();

        // then
        LeafNode leafNode = new LeafNode(new LeafPage(DSM.readPage(pageId)));
        assertThat(record).isEqualTo(leafNode.find(key, DSM));
    }

    private static Stream<Arguments> provideKeyAndPageId() {
        return Stream.of(
                Arguments.of(1l, 1l),
                Arguments.of(50l, 5l),
                Arguments.of(8000, 506l)
        );
    }
}