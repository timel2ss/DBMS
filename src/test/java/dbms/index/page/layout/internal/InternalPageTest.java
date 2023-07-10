package dbms.index.page.layout.internal;

import dbms.index.page.Page;
import dbms.index.page.layout.HeaderPage;
import dbms.index.page.layout.PageHeader;
import dbms.io.DiskSpaceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class InternalPageTest {

    @Test
    @DisplayName("InternalPage를 읽을 수 있다")
    void internalPage() {
        // given
        DiskSpaceManager DSM = new DiskSpaceManager("src/test/resources/sample");
        HeaderPage headerPage = new HeaderPage(new Page(DSM.readPage(0l)));

        // when
        InternalPage internalPage = new InternalPage(new Page(DSM.readPage(headerPage.getRootPageAddr())));

        // then
        PageHeader pageHeader = internalPage.getPageHeader();
        List<InternalKeyValuePair> keyValuePairs = internalPage.getKeyValuePairs();

        assertThat(keyValuePairs.size()).isEqualTo(pageHeader.getNumberOfKeys());
        assertThat(keyValuePairs).extracting("key", "pageAddr")
                .contains(
                        tuple(2000l, 252l),
                        tuple(4000l, 379l),
                        tuple(6000l, 505l),
                        tuple(8000l, 631l)
                );
    }
}