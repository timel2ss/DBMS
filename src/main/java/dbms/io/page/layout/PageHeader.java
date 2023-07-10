package dbms.io.page.layout;

import dbms.io.page.Page;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class PageHeader {
    public static final int PAGE_HEADER_SIZE = 128; // 128 bytes
    private static final int DATA_SIZE = 24; // 24 bytes
    private static final int RESERVED_SIZE = PAGE_HEADER_SIZE - DATA_SIZE;

    private long parentPageAddr;
    private int isLeaf;
    private int numberOfKeys;

    /**
     * Leaf Page: right sibling page address
     * Internal Page: left-most child page address
     */
    private long pageAddr;

    public PageHeader(Page page) {
        ByteBuffer byteBuffer = page.getByteBuffer();
        parentPageAddr = Long.reverseBytes(byteBuffer.getLong());
        isLeaf = Integer.reverseBytes(byteBuffer.getInt());
        numberOfKeys = Integer.reverseBytes(byteBuffer.getInt());
        byteBuffer.get(new byte[RESERVED_SIZE]);
        pageAddr = Long.reverseBytes(byteBuffer.getLong());
    }
}
