import java.util.*;
import java.io.*;

public class ValuesRecords {

    RandomAccessFile file;
    long countRecords;
    int RECORD_COUNT_OFFSET = 0;
    int valSize = 256;

    public ValuesRecords(String strFile) throws IOException {
        File file = new File(strFile);

        if (!file.exists()) {
            this.countRecords = 0;
            this.file = new RandomAccessFile(strFile, "rwd");
            this.file.seek(RECORD_COUNT_OFFSET);
            this.file.writeLong(this.countRecords);
        } else {
            this.file = new RandomAccessFile(strFile, "rwd");
            this.file.seek(RECORD_COUNT_OFFSET);
            this.countRecords = this.file.readLong();
        }
    }
    

    public void access(int i) throws IOException {
        this.file.seek(8 + i * valSize);
    }

    public void write(String str) throws IOException {
        short len = str.length();
        // convert string to byte
        byte[] bArr = str.getBytes("UTF8");
        // write the length of the byte array from the convert
        file.writeShort(len);
        // write the byte array
        file.write(bArr);
        // update header to reflect the count of stored records
        countRecords++;
    }

    public String readValue() throws IOException {
        // get actual size of the string to read
        short len = file.readShort();
        // allocate a byte array with length = size of string
        byte[] bArr = new byte[len];
        // read byte array
        file.read(bArr);
        // use string contructor and specify charset name to utf8
        String message = new String(bArr, "UTF-8")
        return message;
    }
}
