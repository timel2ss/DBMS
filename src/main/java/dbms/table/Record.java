package dbms.table;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Record {
    private long key;
    private byte[] value;

    public Record(long key, byte[] value) {
        this.key = key;
        this.value = value;
    }
}
