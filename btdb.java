
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
        
        BTRecords btFile = new BTRecords(bt);
        ValuesRecords valFile = new ValuesRecords(val);
        
        while(in.hasNextLine()) {
            String instruct = in.next();
            if(instruct.equals("insert")) {
                int key = in.nextInt();
                String message = in.nextLine();
                valFile.write(message);
                System.out.println(key + " inserted.");
            } else if (instruct.equals("update")) {
                int key = in.nextInt();
                String message = in.nextLine();
                
                System.out.println(key + " updated.");
            } else if (instruct.equals("select")) {
                int key = in.nextInt();
                
                
                System.out.println(key + " =>");
            } else if (instruct.equals("exit")) {
                System.exit(0);
            } else {
                System.out.println("ERROR: invalid command.");
            }
        }
    }
}
