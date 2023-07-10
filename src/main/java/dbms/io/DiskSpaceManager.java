package dbms.io;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class DiskSpaceManager implements AutoCloseable {
    public static final short PAGE_SIZE = 4096;
    public static final byte PAGES_PER_FILE = 100;

    private Map<Integer, RandomAccessFile> openedFiles = new HashMap<>();

    public DiskSpaceManager(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                log.error("Could not create directory (name: {})", dirName);
                throw new RuntimeException();
            }
            log.info("Directory created. Location: {}", dir.getAbsolutePath());
        }

        File[] files = dir.listFiles();
        if (files == null) {
            log.error("{} is a file, not a directory.", dirName);
            throw new RuntimeException();
        }

        for (File file : files) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rws");
                int fileId = parseFileId(file);
                openedFiles.put(fileId, randomAccessFile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void close() throws Exception {
        for (RandomAccessFile file : openedFiles.values()) {
            file.close();
        }
    }

    public byte[] readPage(long pageId) {
        int fileOffset = getFileOffset(pageId);
        RandomAccessFile file = openedFiles.get(fileOffset);
        try {
            file.seek(fileOffset * PAGE_SIZE);
            byte[] buf = new byte[PAGE_SIZE];
            ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
            file.getChannel().read(byteBuffer);
            return buf;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writePage(long pageId, byte[] data) {
        int fileOffset = getFileOffset(pageId);
        RandomAccessFile file = openedFiles.get(fileOffset);
        try {
            file.seek(fileOffset * PAGE_SIZE);
            ByteBuffer byteBuffer = ByteBuffer.wrap(data);
            file.getChannel().write(byteBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private int getFileOffset(long pageId) {
        return (int) pageId % PAGES_PER_FILE;
    }

    private int parseFileId(File file) {
        String fileName = file.getName();
        String fileNameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
        // filename: [DatabaseName]-[FileID].dat
        return Integer.parseInt(fileNameWithoutExt.split("-")[1]);
    }

}
