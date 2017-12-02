import java.util.*;
import java.io.*;

public class BTRecords {
    
    RandomAccessFile file;
    long countRecords; // number of node records in the btree
    int RECORD_COUNT_OFFSET = 0;
    long rootLocation; // location of the root record
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
    
    // go to the i-th node
    public void findNodeRecord(long i) throws IOException {
        //seek the B-Tree node record whose ID is i
        file.seek(16 + i*8*entries);
    }
    
    // go to the root node
    public void goToRoot() throws IOException {
        findNodeRecord(rootLocation);
    }
    
    // read long
    // might remove this
    public long read() throws IOException {
        return file.readLong();
    }
    
    // returns the record's numChild's ID
    // record -> node record, numChild -> ith child
    public long readChildID(long record, int numChild) throws IOException {
        file.seek((16 + (record * 8 * entries)) + (8 + (8 * 3 * numChild)));
        return file.readLong();
    }
    
    // returns the numKey key of record
    // record -> node record, numKey -> ith key
    public long readKey(long record, int numKey) throws IOException {
        file.seek((16 + (record * 8 * entries)) + (16 + (8 * 3 * numKey)));
        long key = file.readLong();
        return key;
    }
    
    // returns the value of the numKey of record
    public String readKeyValue(long record, long numKey, RandomAccessFile valFile) throws IOException {
        // go to value offset record
        file.seek((16 + (record * 8 * entries)) + (24 + (8 * 3 * numKey)));
        // get value offset
        long offset = file.readLong();
        valFile.access(offset);
        String value = valFile.readValue();
        return value;
    }
    
    // writes a key into the file
    public void write(long in) throws IOException {
        file.writeLong(in);
    }
    
    // searches for a key
    // should return the node record # (?)
    public long search(long record, long key){
        
    }
}
