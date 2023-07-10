package dbms.io.page.layout.leaf;

import dbms.io.page.Page;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class LeafKeyValuePair {
    public static final int LEAF_KEY_VALUE_PAIR_SIZE = 128; // 128 bytes

    private long key;
    private byte[] value;

    public LeafKeyValuePair(Page page) {
        ByteBuffer byteBuffer = page.getByteBuffer();
        key = Long.reverseBytes(byteBuffer.getLong());
        value = new byte[120];
        byteBuffer.get(value);
    }
}
