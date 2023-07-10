package dbms.index.page;

import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class Page {
    private ByteBuffer byteBuffer;

    public Page(byte[] data) {
        this.byteBuffer = ByteBuffer.wrap(data);
    }

    public Page(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
}
