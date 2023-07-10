package dbms.io.page.layout;

import dbms.io.DiskSpaceManager;
import dbms.io.page.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HeaderPageTest {

    @Test
    @DisplayName("HeaderPage를 읽을 수 있다")
    void headerPage() {
        // given
        DiskSpaceManager DSM = new DiskSpaceManager("src/test/resources/sample");
        Page page = DSM.readPage(0l);

        // when
        HeaderPage headerPage = new HeaderPage(page);

        // then
        assertThat(headerPage.getFreePageAddr()).isEqualTo(632l);
        assertThat(headerPage.getRootPageAddr()).isEqualTo(253l);
        assertThat(headerPage.getNumberOfPages()).isEqualTo(634l);
    }

}