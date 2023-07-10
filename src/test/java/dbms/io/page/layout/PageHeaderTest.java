package dbms.io.page.layout;

import dbms.io.DiskSpaceManager;
import dbms.io.page.layout.internal.InternalPage;
import dbms.io.page.layout.leaf.LeafPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageHeaderTest {
    @Test
    @DisplayName("InternalPage의 PageHeader를 읽을 수 있다")
    void internalPageHeader() {
        // given
        DiskSpaceManager DSM = new DiskSpaceManager("src/test/resources/sample");
        HeaderPage headerPage = new HeaderPage(DSM.readPage(0l));

        // when
        InternalPage internalPage = new InternalPage(DSM.readPage(headerPage.getRootPageAddr()));
        PageHeader pageHeader = internalPage.getPageHeader();

        // then
        assertThat(pageHeader.getParentPageAddr()).isEqualTo(0l); // root
        assertThat(pageHeader.getIsLeaf()).isEqualTo(0);
        assertThat(pageHeader.getNumberOfKeys()).isEqualTo(4);
        assertThat(pageHeader.getPageAddr()).isEqualTo(3l);
    }

    @Test
    @DisplayName("LeafPage의 PageHeader를 읽을 수 있다")
    void LeafPageHeader() {
        // given
        DiskSpaceManager DSM = new DiskSpaceManager("src/test/resources/sample");
        HeaderPage headerPage = new HeaderPage(DSM.readPage(0l));
        InternalPage tempPage1 = new InternalPage(DSM.readPage(headerPage.getRootPageAddr()));
        InternalPage tempPage2 = new InternalPage(DSM.readPage(tempPage1.getPageHeader().getPageAddr()));

        // when
        LeafPage leafPage = new LeafPage(DSM.readPage(tempPage2.getPageHeader().getPageAddr()));
        PageHeader pageHeader = leafPage.getPageHeader();

        // then
        assertThat(pageHeader.getParentPageAddr()).isEqualTo(3l);
        assertThat(pageHeader.getIsLeaf()).isEqualTo(1);
        assertThat(pageHeader.getNumberOfKeys()).isEqualTo(16);
        assertThat(pageHeader.getPageAddr()).isEqualTo(2l);
    }

}