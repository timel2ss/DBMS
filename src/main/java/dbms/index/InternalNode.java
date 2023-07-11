package dbms.index;

import dbms.io.DiskSpaceManager;
import dbms.io.page.Page;
import dbms.io.page.PageType;
import dbms.io.page.layout.internal.InternalKeyValuePair;
import dbms.io.page.layout.internal.InternalPage;
import dbms.io.page.layout.leaf.LeafPage;
import dbms.table.Record;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class InternalNode implements Node {
    private List<Long> keys = new ArrayList<>();
    private List<Long> children = new ArrayList<>();

    private InternalPage page;

    public InternalNode(InternalPage page) {
        this.page = page;
        children.add(page.getPageHeader().getPageAddr());
        List<InternalKeyValuePair> keyValuePairs = page.getKeyValuePairs();
        keyValuePairs.forEach(pair -> {
            keys.add(pair.getKey());
            children.add(pair.getPageAddr());
        });
    }

    @Override
    public Record find(long key, DiskSpaceManager DSM) {
        int index = Collections.binarySearch(keys, key);
        index = index >= 0 ? index + 1 : Math.abs(index) - 1;
        Long childPageId = children.get(index);
        Page page = DSM.readPage(childPageId);

        if (page.getPageType() == PageType.INTERNAL_PAGE) {
            InternalNode childNode = new InternalNode(new InternalPage(page));
            return childNode.find(key, DSM);
        }
        LeafNode childNode = new LeafNode(new LeafPage(page));
        return childNode.find(key, DSM);
    }
}
