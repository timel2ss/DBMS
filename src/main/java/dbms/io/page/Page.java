package dbms.io.page;

import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class Page {
    private ByteBuffer byteBuffer;

    public Page(byte[] data) {
        this.byteBuffer = ByteBuffer.wrap(data);
    }

    public PageType getPageType() {
        byteBuffer.position(8);
        int isLeaf = Integer.reverseBytes(byteBuffer.getInt());
        PageType pageType = isLeaf == 1 ? PageType.LEAF_PAGE : PageType.INTERNAL_PAGE;
        byteBuffer.position(0);
        return pageType;
    }
}
