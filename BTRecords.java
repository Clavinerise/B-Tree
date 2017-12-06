
import java.io.*;

public class BTRecords {
    
    RandomAccessFile btfile;
    ValuesRecords valFile;
    long countRecords; // records inside btfile
    int RECORD_COUNT_OFFSET = 0;
    long rootLocation; // location of the root record
    
    int order = 7;
    int entries = 2+3*(order-1);
    Btree atree;
    
    public BTRecords(String btreeFile, String valfile) throws IOException {
        valFile = new ValuesRecords(valfile);
        File file = new File(btreeFile);
        atree = new Btree(order);

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
        atree.insert(atree.root, key, valFile.countRecords);
        rootLocation = atree.root.record;
        countRecords = atree.record + 1;
        // valFile add
        valFile.access(valFile.countRecords);
        valFile.write(value);
        valFile.countRecords++;
        // update btfile
        btUpdate();
    }
    
    public void btUpdate() throws IOException{
    	placeKeysAndOffset(atree.root.record, atree.takeKeys(atree.root), atree.takeOffset(atree.root));
    	placeChildren(atree.root.record, atree.takeChildren(atree.root));
    	
    }
    
    public boolean keyExist(long key) throws IOException{
    	int[] recpos = new int[2];
        recpos = atree.recNDPos(key);
        if (recpos[0] == -1){
            return false;
        }
        else {
        	return true;
        }
    }
    
    // UPDATE instruction
    public boolean updateRecords(long key, String value) throws IOException {
        // get offset key from btFile
        int[] recpos = new int[2];
        recpos = atree.recNDPos(key);
        int record  = recpos[0];
        int position = recpos[1];
        if (record == -1){
            return false;
        }
        else{
            // go to value offset record
            btfile.seek((16 + (record * 8 * entries)) + (24 + (8 * 3 * position)));
            // get value offset
            long offset = btfile.readLong();
            valFile.access(offset);
            valFile.write(value);
            return true;
        }
    }
    
    // SELECT instruction
    public String readKeyValue(long key) throws IOException {
        int[] recpos = new int[2];
        recpos = atree.recNDPos(key);
        int record  = recpos[0];
        int position = recpos[1];
        if (record == -1){
            return "null";
        }
        else{
            // go to value offset record
            btfile.seek((16 + (record * 8 * entries)) + (24 + (8 * 3 * position)));
            // get value offset
            long offset = btfile.readLong();
            valFile.access(offset);
            String value = valFile.readValue();
            return value;
        }
    }
    
    // extracts all keys from btfile and returns an array
    public long[] extractKeys(int record) throws IOException{
        long[] nodeKeys = new long[entries];
        for(int i = 0; i < entries - 1; i++) {
            btfile.seek(16 + (record * 8 * entries));
            long r = btfile.readLong();
            nodeKeys[i] = r;
        }
        return nodeKeys;
    }
    
    // places the keys and their corresponding offsets into a record
    public void placeKeysAndOffset(int record, long[] keys, long[] offset) throws IOException {
        for(int i = 0; i < order-1; i++) {
            btfile.seek((16 + (record * 8 * entries)) + (16 + (8 * 3 * i)));
            btfile.writeLong(keys[i]);
            btfile.writeLong(offset[i]);
        }
    }
    
    // places children in the indicated record
    public void placeChildren(int record, long[] children) throws IOException {
    	 for(int i = 0; i < order; i++) {
    		 btfile.seek((16 + (record * 8 * entries)) + (8 + (8 * 3 * i)));
    		 btfile.writeLong(children[i]);
    		 }
    	 }
    
    public long getNumRecords() throws IOException{
        btfile.seek(0);
        return btfile.readLong();
    }
    
    // returns the location of the root
    public long rootLocation() throws IOException {
        btfile.seek(8);
        return btfile.readLong();
    }
    
    // places the parent record number into the record for the indicated node
    public void placeParent(long record, long parentRecord) throws IOException {
        btfile.seek(16 + (record * 8 * entries));
        btfile.writeLong(parentRecord);
    }
    
    // extracts parent record number from the record
    public long getParent(long record) throws IOException {
        btfile.seek(16 + (record * 8 * entries));
        return btfile.readLong();
    }
}
