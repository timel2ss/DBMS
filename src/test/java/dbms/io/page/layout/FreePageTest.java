package dbms.io.page.layout;

import dbms.io.DiskSpaceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FreePageTest {

    @Test
    @DisplayName("FreePage를 읽을 수 있다")
    void freePage() {
        // given
        DiskSpaceManager DSM = new DiskSpaceManager("src/test/resources/sample");
        HeaderPage headerPage = new HeaderPage(DSM.readPage(0l));

        // when
        FreePage freePage = new FreePage(DSM.readPage(headerPage.getFreePageAddr()));

        // then
        assertThat(freePage.getNextFreePageAddr()).isEqualTo(633l);
    }

}