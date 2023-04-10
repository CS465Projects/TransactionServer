/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package transactionServer;

// import Transaction Server/ ArrayList/ HashMap

import account.AccountManager;
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
    
    public void setTransactionNumber( int transactionNumber )
    {
        // set the transaction number 
    }
    public int read ( int accountNumber )
    {
        Integer balance; 
        
        // check if value to be read was written by this transaction
        balance = writeSet.get(accountNumber);
        
        // if not, read the committed version of it
        if( balance == null )
        {
            balance = TransactionServer.accountManager.read(accountNumber);
            
        }
        if( !readSet.contains( accountNumber))
        {
            readSet.add(accountNumber);
        }
        return balance; 
    }
    
    public int write (int accountNumber, int newBalance )
    {
        int oldBalance = read(accountNumber);
        
        if( !writeSet.containsKey(accountNumber))
        {
            writeSet.put(accountNumber, newBalance );
        }
        return oldBalance;
        
    }
    
    public ArrayList getReadSet()
    {
        return readSet;
    }
    
    public HashMap getWriteSet()
    {
        return writeSet;
    }

    public int getTransactionID() {
        return transactionID;
    }
    
    public int getTransactionNumber() {
        return transactionNumber;
    }
    public int getLastCommittedTransactionNumber(){
        return lastCommittedTransactionNumber;
    }
    
    public StringBuffer getLog(){
        return log; 
    }
    
    public void log(String string) {
        int messageCount = TransactionServer.getMessageCount();
        
        log.append("\n").append(messageCount).append(" ").append(string);
        
        if(!TransactionServer.transactionView){
            System.out.println(messageCount +"Transaction #" + transactionNumber);
        }
        
        
        log.append(string);
    }
    
    
    
}
