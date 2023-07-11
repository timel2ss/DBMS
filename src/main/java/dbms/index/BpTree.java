package dbms.index;

import dbms.io.DiskSpaceManager;
import dbms.io.page.Page;
import dbms.io.page.PageType;
import dbms.io.page.layout.HeaderPage;
import dbms.io.page.layout.internal.InternalPage;
import dbms.io.page.layout.leaf.LeafPage;
import dbms.table.Record;

import java.util.Optional;

public class BpTree {
    private DiskSpaceManager diskSpaceManager;
    private HeaderPage headerPage;

    public BpTree(DiskSpaceManager diskSpaceManager, HeaderPage headerPage) {
        this.diskSpaceManager = diskSpaceManager;
        this.headerPage = headerPage;
    }

    public Optional<Record> search(long key) {
        Page page = diskSpaceManager.readPage(headerPage.getRootPageAddr());
        PageType pageType = page.getPageType();
        if (pageType == PageType.INTERNAL_PAGE) {
            InternalNode rootNode = new InternalNode(new InternalPage(page));
            return Optional.ofNullable(rootNode.find(key, diskSpaceManager));
        }
        LeafNode rootNode = new LeafNode(new LeafPage(page));
        return Optional.ofNullable(rootNode.find(key, diskSpaceManager));
    }

}
