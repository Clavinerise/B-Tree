import java.util.*;
import java.io.*;

public class ValuesRecords {

    RandomAccessFile valfile;
    long countRecords;
    int RECORD_COUNT_OFFSET = 0;
    int length = 2; // in bytes
    int valuesStr = 256; // in bytes
    int valSize = length + valuesStr; // in bytes
    
    public ValuesRecords(String strFile) throws IOException {
        File file = new File(strFile);

        if (!file.exists()) {
            this.countRecords = 0;
            this.valfile = new RandomAccessFile(strFile, "rwd");
            this.valfile.seek(RECORD_COUNT_OFFSET);
            this.valfile.writeLong(this.countRecords);
        } else {
            this.valfile = new RandomAccessFile(strFile, "rwd");
            this.valfile.seek(RECORD_COUNT_OFFSET);
            this.countRecords = this.valfile.readLong();
        }
    }
    
    // go to the i-th value
    public void access(long i) throws IOException {
        this.valfile.seek(8 + i * valSize);
    }
    
    // write str into the valfile
    public void write(String str) throws IOException {
        // convert string to byte
        byte[] bArr = str.getBytes("UTF8");
        short len = (short) bArr.length;
        // write the length of the byte array from the convert
        valfile.writeShort(len);
        // write the byte array
        valfile.write(bArr);
    }

    // returns the string value
    public String readValue() throws IOException {
        // get actual size of the string to read
        short len = valfile.readShort();
        // allocate a byte array with length = size of string
        byte[] bArr = new byte[len];
        // read byte array
        valfile.read(bArr);
        // use string contructor and specify charset name to utf8
        String message = new String(bArr, "UTF-8");
        return message;
    }
}
