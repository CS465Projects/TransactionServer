/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package transactionServer;

// import Transaction Server/ ArrayList/ HashMap

import java.util.ArrayList;
import java.util.HashMap;



/**
 *
 * @author lalom
 */
public class Transaction {
    
    // transaction ID and OCC specific transaction numbers
    int transactionID;
    int transactionNumber;
    int lastCommittedTransactionNumber;
    
    // the sets of tentative data
    ArrayList<Integer>        readSet = new ArrayList<>();
    HashMap<Integer, Integer> writeSet = new HashMap<>();
    
    StringBuffer log = new StringBuffer("");
    
    Transaction( int transactionID, int lastComiteedTransactionNumber )
    {
        this.transactionID = transactionID;
        this.lastCommittedTransactionNumber = lastCommittedTransactionNumber;
    }
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
    
    
    public int read ( int accountNumber )
    {
        return 0; // sub 
    }
    
    public int write (int accountNumber, int newBalance )
    {
        return 0;// sub
        
    }
    
    public ArrayList getReadSet()
    {
        return readSet;
    }
    
    public HashMap getWriteSet()
    {
        return writeSet;
    }
}
