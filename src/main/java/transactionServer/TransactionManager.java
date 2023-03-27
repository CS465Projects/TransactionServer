/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transactionServer;

import com.mycompany.transactionapp.MessageTypes;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author lalom
 */
public class TransactionManager implements MessageTypes{
    // counter for transactions IDs
    private static int tansactionIdCounter = 0;
    
    // list of transaction
    private static final ArrayList<Transaction>         runningTransactions   = new ArrayList<>();
    private static final ArrayList<Transaction>         abortedTransactions   = new ArrayList<>();
    private static final HashMap<Integer, Transaction>  committedTransactions = new HashMap(); // specific to OCC
    
    
    // transaction number counter specifc to OCC
    private static int transactionNumberCounter = 0;
    
    
    /**
     * Default constructor
     */
    public TransactionManager() {}
    
    /**
     * Helper method returning aborted transactions
     * 
     * @return the list of aborted transactions 
     */
    public ArrayList<Transaction> getAbortedTransactions()
    {
        return abortedTransactions;
    }
    
    
    /**
     * Run the transaction for an incoming client request
     * 
     * @param client Socket object representing connection to client
     */
    public synchronized void runTransaction( Socket client )
    {
        (new TransactionManagerWorker( client )).start();
    }
    
    /**
     * validates a transaction according to OCC, implementing backwards validation
     * 
     * @param transaction Transaction to be validates
     * @return a flag indicating whether validation was successful
     */
    public boolean validateTransaction( Transaction transaction )
    {
        // initialize variables
        
        
        // assign a transaction number to this one
        
        // get the transaction number of the last committed tranactiong right before this one started
        
        // run through all overlapping transactions
        
            // get transaction with transaction number of transactionNumberIndex
        
            // make sure transaction with transactionNumberIndex was not aborted before
        
                // check our own read set againt the write set of the checkedTranscation
        
                // while loop 
                     
                    // is an account in the read set part of the write set in the checkedTransaction>
        
        
        
        
        return true ;// sub
    }
    
    /**
     * writes the write set of a transaction into the operational data
     * 
     * @param transaction Transaction to be written
     */
    public void writetransaction( Transaction transaction )
    {
        // initalize variables
        
        // get all the entries of this write set
        
            // write this record into operational data
    }
    
    /**
     * Objects of this inner class run transaction, one thread runs one transaction
     * on behalf of the client
     */
    public class TransactionManagerWorker extends Thread
    {
        // netowrking communcation realted fields
        Socket client;
        
        // transaction related fields
        
        // flag for jumping out of while loop after this transaction closed
        
        // the constructor just opens up the network channels
        private TransactionManagerWorker(Socket client)
        {
            this.client = client;
            
            // setting up object streams try/catch
        }
        
        public void run()
        {
            // loop is left when transaction closes
            
                // reading message try/catch
            
                // processing message
                // switch 
            
                // case OPEN_TRANSACTION
            
                // case CLOSE_TRANSACTION
            
                
            
        }
        
    }
}
