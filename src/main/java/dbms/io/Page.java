package dbms.io;

import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class Page {
    private ByteBuffer byteBuffer;

    public Page(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
}
