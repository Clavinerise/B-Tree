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
        findNodeRecord(rootLocation);
    }
    
    public long read() throws IOException {
        return file.readLong();
    }
    
    public long readChildID(long record, int numChild) throws IOException {
        file.seek((16 + (record * 8 * entries)) + (8 + (8 * 3 * numChild)));
        return file.readLong();
    }
    
    public long readKey(long record, int numKey) throws IOException {
        file.seek((16 + (record * 8 * entries)) + (16 + (8 * 3 * numKey)));
        long key = file.readLong();
        return key;
    }
    
    public String readKeyValue(long record, RandomAccessFile valFile) throws IOException {
        // go to value offset record
        file.seek((16 + (record * 8 * entries)) + (24 + (8 * 3 * numKey)));
        // get value offset
        long offset = file.readLong();
        valFile.access(offset);
        String value = valFile.readValue();
        return value;
    }
                  
    public void write(long in) throws IOException {
        file.writeLong(in);
    }
    
    // returns the record #
    public long search(long record, long key){
        
    }
}
