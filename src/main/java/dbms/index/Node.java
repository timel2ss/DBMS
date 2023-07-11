package dbms.index;

import dbms.io.DiskSpaceManager;
import dbms.table.Record;

public interface Node {

    Record find(long key, DiskSpaceManager DSM);
}
