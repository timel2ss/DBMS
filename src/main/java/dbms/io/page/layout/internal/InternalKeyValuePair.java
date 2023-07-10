package dbms.io.page.layout.internal;

import dbms.io.page.Page;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class InternalKeyValuePair {
    public static final int INTERNAL_KEY_VALUE_PAIR_SIZE = 16; // 16 bytes
    private long key;
    private long pageAddr;

    public InternalKeyValuePair(Page page) {
        ByteBuffer byteBuffer = page.getByteBuffer();
        key = Long.reverseBytes(byteBuffer.getLong());
        pageAddr = Long.reverseBytes(byteBuffer.getLong());
    }
}
