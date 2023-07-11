package dbms.index;

import dbms.io.DiskSpaceManager;
import dbms.io.page.layout.leaf.LeafKeyValuePair;
import dbms.io.page.layout.leaf.LeafPage;
import dbms.table.Record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeafNode implements Node {
    private List<Long> keys = new ArrayList<>();
    private List<byte[]> values = new ArrayList<>();
    private long rightSiblingId;

    private LeafPage page;

    public LeafNode(LeafPage page) {
        this.page = page;
        rightSiblingId = page.getPageHeader().getPageAddr();
        List<LeafKeyValuePair> keyValuePairs = page.getKeyValuePairs();
        keyValuePairs.forEach(pair -> {
            keys.add(pair.getKey());
            values.add(pair.getValue());
        });
    }

    @Override
    public Record find(long key, DiskSpaceManager DSM) {
        int index = Collections.binarySearch(keys, key);
        if (index < 0) {
            return null;
        }
        return new Record(key, values.get(index));
    }
}
