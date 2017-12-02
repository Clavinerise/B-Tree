
import java.util.*;
import java.io.*;

public class btdb {
    int RECORD_COUNT_OFFSET = 0;
    long countRecords;
    RandomAccessFile valFile;

    public static void main(String args[]) throws IOException{
        
        Scanner in = new Scanner(System.in);

        String bt = args[0];
        String val = args[1];
        
        // creates/reads b-tree file
        BTRecords btFile = new BTRecords(bt);
        // creates/reads value file
        ValuesRecords valFile = new ValuesRecords(val);
        
        while(in.hasNextLine()) {
            // reads instruction
            String instruct = in.next();
            if(instruct.equals("insert")) {
                // reads input key and value
                long key = in.nextLong();
                String value = in.nextLine();
                // checks if key already exists
                if(search(key) >= 0){
                    System.out.printf("ERROR: %d already exists.");
                } else {
                    // adds to bt file
                    
                    // adds to val File
                    valFile.access(valFile.countRecords);
                    valFile.write(value);
                    // print confirmation
                    System.out.println(key + " inserted.", key);
                }
            } else if (instruct.equals("update")) {
                // reads key and value
                long key = in.nextLong();
                String value = in.nextLine();
                // checks if key already exists
                if(search(key) <= 0){
                    System.out.printf("ERROR: %d does not exist.", key);
                } else {
                    // find key offset in bt file
                    
                    //update record in val file
                    valFile.access();
                    valFile.write(value);
                    // print confirmation
                    System.out.println(key + " updated.");
                }
            } else if (instruct.equals("select")) {
                long key = in.nextLong();
                if(search(key) <= 0){
                    System.out.printf("ERROR: %d does not exist.", key);
                } else {
                    //get record number
                    
                    //get key value
                    String value = btfile.readKeyValue( , valfile);
                    // print confirmation
                    System.out.printf("%d => %s", key, value);
                }
            } else if (instruct.equals("exit")) {
                System.exit(0);
            } else {
                System.out.println("ERROR: invalid command.");
            }
        }
    }
}
