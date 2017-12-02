import java.util.*;
import java.io.*;

public class BTRecords {
    
    RandomAccessFile btfile;
    ValuesRecords valFile;
    long countRecords; // number of node records in the btree
    int RECORD_COUNT_OFFSET = 0;
    long rootLocation; // location of the root record
    int order = 7;
    int entries = 2+3*(order-1);
    
    public BTRecords(String btreeFile, String valfile) throws IOException {
        valFile = new ValuesRecords(valfile);
        File file = new File(btreeFile);

        if (!file.exists()) {
            this.countRecords = 0;
            this.btfile = new RandomAccessFile(btreeFile, "rwd");
            this.btfile.seek(RECORD_COUNT_OFFSET);
            this.btfile.writeLong(this.countRecords);
            this.rootLocation = 0;
            this.btfile.writeLong(this.rootLocation);
        } else {
            this.btfile = new RandomAccessFile(btreeFile, "rwd");
            this.btfile.seek(RECORD_COUNT_OFFSET);
            this.countRecords = this.btfile.readLong(); 
            this.rootLocation = this.btfile.readLong();
        }
    }
    
    // INSERT instruction
    public void addKey(long key, String value) throws IOException {
        // BTree add
        
        // valFile add
        valFile.access(valFile.countRecords);
        valFile.write(value);
    }
    
    // UPDATE instruction
    public void updateRecords(long key, String value) throws IOException {
        // get offset key from btFile
        
        // valFile update
        valFile.access();
        valFile.write(value);
    }
    
    // SELECT instruction
    public String readKeyValue(long key, long record, long numKey) throws IOException {
        // go to value offset record
        btfile.seek((16 + (record * 8 * entries)) + (24 + (8 * 3 * numKey)));
        // get value offset
        long offset = btfile.readLong();
        valFile.access(offset);
        String value = valFile.readValue();
        return value;
    }
    
    // go to the i-th node record
    public void findNodeRecord(long i) throws IOException {
        //seek the B-Tree node record whose ID is i
        btfile.seek(16 + i*8*entries);
    }
    
    // go to the root node
    public void goToRoot() throws IOException {
        findNodeRecord(rootLocation);
    }
    
    // returns the record's numChild ID
    // record -> node record, numChild -> ith child
    public long readChildID(long record, int numChild) throws IOException {
        btfile.seek((16 + (record * 8 * entries)) + (8 + (8 * 3 * numChild)));
        return btfile.readLong();
    }
    
    // returns the numKey key of record
    // record -> node record, numKey -> ith key
    public long readKey(long record, int numKey) throws IOException {
        btfile.seek((16 + (record * 8 * entries)) + (16 + (8 * 3 * numKey)));
        long key = btfile.readLong();
        return key;
    }
    
    public boolean hasChild(long node) throws IOException{
        btfile.seek((16 + (node * 8 * entries)) + 8);
        if (btfile.readLong() == -1) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean hasParent(long node) throws IOException{
        btfile.seek((16 + (node * 8 * entries)));
        if (btfile.readLong() == -1) {
            return false;
        } else {
            return true;
        }
    }
    
    // checks if node is full
    public boolean isFull(long node) throws IOException{
        if (readChildID(node, 4) == -1) {
            return false;
        } else {
            return true;
        }
    }
    
    // writes a key into the btfile
    // should be modified to work for Btree
    public void write(long in) throws IOException {
        btfile.writeLong(in);
    }
    
    // searches for a key
    // should return the node record # (?)
    // or maybe return boolean
    public long search(long record, long key) throws IOException{
        
    }
}
