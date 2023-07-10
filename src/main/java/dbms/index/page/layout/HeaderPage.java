package dbms.index.page.layout;

import dbms.index.page.Page;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class HeaderPage {
    private long freePageAddr;
    private long rootPageAddr;
    private long numberOfPages;

    public HeaderPage(Page page) {
        ByteBuffer byteBuffer = page.getByteBuffer();
        freePageAddr = Long.reverseBytes(byteBuffer.getLong());
        rootPageAddr = Long.reverseBytes(byteBuffer.getLong());
        numberOfPages = Long.reverseBytes(byteBuffer.getLong());
    }
}
