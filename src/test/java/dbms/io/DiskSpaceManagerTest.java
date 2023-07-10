package dbms.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DiskSpaceManagerTest {

    @TempDir
    File dbFolder;

    @Test
    @DisplayName("DiskSpaceManager 생성 시, DB 이름의 폴더가 존재하지 않으면 새로 만든다")
    void createDSM() throws Exception {
        // given
        String dirName = "DBName";

        // when
        DiskSpaceManager DSM = new DiskSpaceManager(dirName);

        // then
        File directory = new File(dirName);
        assertThat(directory).isNotNull();
        assertThat(directory.isDirectory()).isTrue();
        assertThat(directory.getName()).isEqualTo(dirName);

        DSM.close();
        directory.delete();
    }

    @Test
    @DisplayName("DiskSpaceManager 생성 시, DB 이름의 폴더가 존재하면 폴더 안의 모든 파일을 열어 Map에 보관한다")
    void createDSM2() throws Exception {
        // given
        File file1 = new File(dbFolder.getAbsolutePath() + "/data-1.dat");
        file1.createNewFile();
        File file2 = new File(dbFolder.getAbsolutePath() + "/data-2.dat");
        file2.createNewFile();

        // when
        DiskSpaceManager DSM = new DiskSpaceManager(dbFolder.getAbsolutePath());

        // then
        Map<Integer, RandomAccessFile> openedFiles = DSM.getOpenedFiles();
        assertThat(openedFiles.size()).isEqualTo(2);
        assertThat(openedFiles.keySet()).contains(1, 2);

        DSM.close();
    }

    @Test
    @DisplayName("Page 단위의 Read/Write를 수행할 수 있다")
    void readWrite() throws Exception {
        // given
        File file1 = new File(dbFolder.getAbsolutePath() + "/data-0.dat");
        file1.createNewFile();

        byte[] data = new byte[DiskSpaceManager.PAGE_SIZE];
        for (int i = 0; i < data.length; ++i) {
            data[i] = (byte) (Integer.valueOf(i).hashCode() & 0xFF);
        }

        DiskSpaceManager DSM = new DiskSpaceManager(dbFolder.getAbsolutePath());

        // when
        DSM.writePage(0, data);
        byte[] readData = DSM.readPage(0);

        // then
        assertThat(readData).isEqualTo(data);

        DSM.close();
    }

}