package dbms.io.page.layout.internal;

import dbms.io.page.Page;
import dbms.io.page.layout.PageHeader;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static dbms.io.DiskSpaceManager.PAGE_SIZE;
import static dbms.io.page.layout.PageHeader.PAGE_HEADER_SIZE;

@Getter
public class InternalPage {
    private PageHeader pageHeader;
    private List<InternalKeyValuePair> keyValuePairs = new ArrayList<>();

    public InternalPage(Page page) {
        pageHeader = new PageHeader(page);
        for (int i = 0; i < pageHeader.getNumberOfKeys(); i++) {
            keyValuePairs.add(new InternalKeyValuePair(page));
        }
        int remainBytes = PAGE_SIZE - PAGE_HEADER_SIZE - InternalKeyValuePair.INTERNAL_KEY_VALUE_PAIR_SIZE * pageHeader.getNumberOfKeys();
        page.getByteBuffer().get(new byte[remainBytes]);
    }
}
