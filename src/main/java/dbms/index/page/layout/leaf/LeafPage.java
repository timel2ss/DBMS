package dbms.index.page.layout.leaf;

import dbms.index.page.Page;
import dbms.index.page.layout.PageHeader;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static dbms.index.page.layout.PageHeader.PAGE_HEADER_SIZE;
import static dbms.index.page.layout.leaf.LeafKeyValuePair.LEAF_KEY_VALUE_PAIR_SIZE;
import static dbms.io.DiskSpaceManager.PAGE_SIZE;

@Getter
public class LeafPage {
    private PageHeader pageHeader;
    private List<LeafKeyValuePair> keyValuePairs = new ArrayList<>();

    public LeafPage(Page page) {
        pageHeader = new PageHeader(page);
        for (int i = 0; i < pageHeader.getNumberOfKeys(); i++) {
            keyValuePairs.add(new LeafKeyValuePair(page));
        }
        int remainBytes = PAGE_SIZE - PAGE_HEADER_SIZE - LEAF_KEY_VALUE_PAIR_SIZE * pageHeader.getNumberOfKeys();
        page.getByteBuffer().get(new byte[remainBytes]);
    }
}
