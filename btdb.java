
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
        
        System.out.print("> ");
        while(in.hasNextLine()) {
            // reads instruction
            String instruct = in.next();
            if(instruct.equals("insert")) {
                // reads input key and value
                long key = in.nextLong();
                String value = in.nextLine();
                // checks if key already exists
                if(btFile.keyExist(key)){
                    System.out.printf("< ERROR: %d already exists.\n", key);
                } 
                else {
                    // adds to btfile
                    btFile.addKey(key, value);
                    // print confirmation
                    System.out.printf("< %d inserted.\n", key);
                }
            } 
            else if (instruct.equals("update")) {
                // reads key and value
                long key = in.nextLong();
                String value = in.nextLine();
                // checks if key already exists
                if(!btFile.keyExist(key)){
                    System.out.printf("< ERROR: %d does not exist.\n", key);
                } 
                else {
                    //update record in valfile through btfile
                    btFile.updateRecords(key, value);
                    // print confirmation
                    System.out.printf("< %d updated.\n", key);
                }
            } 
            else if (instruct.equals("select")) {
                long key = in.nextLong();
                if(!btFile.keyExist(key)){
                    System.out.printf("< ERROR: %d does not exist.\n", key);
                } 
                else {
                    String value = btFile.readKeyValue(key);
                    // print confirmation
                    System.out.printf("< %d =>%s\n", key, value);
                }
            } else if (instruct.equals("exit")) {
            	btFile.close();
                System.exit(0);
            } else {
            	in.nextLine();
                System.out.println("< ERROR: invalid command.");
            }
            System.out.print("> ");
        }
        in.close();
    }
}
