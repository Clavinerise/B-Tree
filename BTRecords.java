import java.util.*;
import java.io.*;

public class BTRecords {
    
    RandomAccessFile file;
    long countRecords;
    int RECORD_COUNT_OFFSET = 0;
    long rootLocation;
    int order = 7;
    int entries = 2+3*(order-1);
    
    public BTRecords(String strFile) throws IOException {
        File file = new File(strFile);

        if (!file.exists()) {
            this.countRecords = 0;
            this.file = new RandomAccessFile(strFile, "rwd");
            this.file.seek(RECORD_COUNT_OFFSET);
            this.file.writeLong(this.countRecords);
            this.rootLocation = 0;
            this.file.writeLong(this.rootLocation);
        } else {
            this.file = new RandomAccessFile(strFile, "rwd");
            this.file.seek(RECORD_COUNT_OFFSET);
            this.countRecords = this.file.readLong();
            this.rootLocation = this.file.readLong();
        }
    }
    
    public void findNodeRecord(long i) throws IOException {
        //seek the B-Tree node record whose ID is i
        file.seek(16 + i*8*entries);
    }
    
    public void goToRoot() throws IOException {
        long rootRec = file.readLong(file.seek(8));
        findNodeRecord(rootRec);
    }
    
    public long read() throws IOException {
        return file.readLong();
    }
    
    public void write(long in) throws IOException {
        file.writeLong(in);
    }
}
