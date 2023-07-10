package dbms.io.page;

import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class Page {
    private ByteBuffer byteBuffer;

    public Page(byte[] data) {
        this.byteBuffer = ByteBuffer.wrap(data);
    }
}
