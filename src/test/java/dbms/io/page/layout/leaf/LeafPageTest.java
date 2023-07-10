package dbms.io.page.layout.leaf;

import dbms.io.DiskSpaceManager;
import dbms.io.page.layout.HeaderPage;
import dbms.io.page.layout.PageHeader;
import dbms.io.page.layout.internal.InternalPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LeafPageTest {

    @Test
    @DisplayName("LeafPage를 읽을 수 있다")
    void LeafPage() {
        // given
        DiskSpaceManager DSM = new DiskSpaceManager("src/test/resources/sample");
        HeaderPage headerPage = new HeaderPage(DSM.readPage(0l));
        InternalPage tempPage1 = new InternalPage(DSM.readPage(headerPage.getRootPageAddr()));
        InternalPage tempPage2 = new InternalPage(DSM.readPage(tempPage1.getPageHeader().getPageAddr()));

        // when
        LeafPage leafPage = new LeafPage(DSM.readPage(tempPage2.getPageHeader().getPageAddr()));

        // then
        PageHeader pageHeader = leafPage.getPageHeader();
        List<LeafKeyValuePair> keyValuePairs = leafPage.getKeyValuePairs();

        assertThat(keyValuePairs.size()).isEqualTo(pageHeader.getNumberOfKeys());
        assertThat(keyValuePairs).extracting("key")
                .contains(
                        0l, 1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l, 9l, 10l, 11l, 12l, 13l, 14l, 15l
                );
    }

}