import java.util.*;
import java.io.*;

public class Records {
    
    int countRecords;
    private RandomAccessFile file;
    private long RECORD_COUNT_OFFSET;
    private long ctrRecord;
    
    public void access(String strFile) throws IOException {
        
        File file = new File(strFile);
        
        if(!file.exists()){
            this.countRecords = 0;
            this.file = new RandomAccessFile(strFile, "rwd");
            this.file.seek(RECORD_COUNT_OFFSET);
            this.file.writeLong(this.ctrRecord);
        } else {
            this.file = new RandomAccessFile(strFile, "rwd");
            this.file.seek(RECORD_COUNT_OFFSET);
            this.ctrRecord = this.file.readLong();
        }
    }
}
