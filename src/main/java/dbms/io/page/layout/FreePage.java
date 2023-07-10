package dbms.io.page.layout;

import dbms.io.page.Page;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class FreePage {
    private long nextFreePageAddr;

    public FreePage(Page page) {
        ByteBuffer byteBuffer = page.getByteBuffer();
        nextFreePageAddr = Long.reverseBytes(byteBuffer.getLong());
    }
}
