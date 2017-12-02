
import java.util.*;
import java.io.*;

public class btdb {

    public static void main(String args[]) throws IOException{
        
        Scanner in = new Scanner(System.in);

        String bt = args[0];
        String val = args[1];
        
        // creates/reads b-tree file
        BTRecords btFile = new BTRecords(bt, val);
        // creates/reads value file
        //ValuesRecords valFile = new ValuesRecords(val);
        
        while(in.hasNextLine()) {
            // reads instruction
            String instruct = in.next();
            if(instruct.equals("> insert")) {
                // reads input key and value
                long key = in.nextLong();
                String value = in.nextLine();
                // checks if key already exists
                if(search(key) >= 0){
                    System.out.printf("< ERROR: %d already exists.");
                } else {
                    // adds to btfile
                    btFile.addKey(key, value);
                    // print confirmation
                    System.out.println("< %d inserted.", key);
                }
            } else if (instruct.equals("> update")) {
                // reads key and value
                long key = in.nextLong();
                String value = in.nextLine();
                // checks if key already exists
                if(search(key) <= 0){
                    System.out.printf("< ERROR: %d does not exist.", key);
                } else {
                    //update record in valfile through btfile
                    btFile.updateRecords(key, value);
                    // print confirmation
                    System.out.println("< %d updated.", key);
                }
            } else if (instruct.equals("> select")) {
                long key = in.nextLong();
                if(search(key) <= 0){
                    System.out.printf("< ERROR: %d does not exist.", key);
                } else {
                    //get record number
                    
                    //get key value
                    String value = btfile.readKeyValue( , valfile);
                    // print confirmation
                    System.out.printf("%d => %s", key, value);
                }
            } else if (instruct.equals("> exit")) {
                System.exit(0);
            } else {
                System.out.println("< ERROR: invalid command.");
            }
        }
    }
}
