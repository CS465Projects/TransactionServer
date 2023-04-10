/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transactionServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import message.MessageTypes;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import message.Message;


/**
 *
 * @author lalom
 */
public class TransactionManager implements MessageTypes{
    // counter for transactions IDs
    private static int transactionIdCounter = 0;
    
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
        int transactionNumber;
        int lastCommittedTransactionNumber;
        int transactionNumberIndex;
        
       
        ArrayList<Integer> readSet = transaction.getReadSet();
        HashMap<Integer, Integer> checkedTransactionWriteSet;
        Iterator<Integer> readSetIterator;
        
        Transaction checkedTransaction;
        Integer checkedAccount;
        
         // assign a transaction number to this one
         transactionNumber = ++transactionNumberCounter;
         transaction.setTransactionNumber(transactionNumber);
         
        // get the transaction number of the last committed tranactiong right before this one started
        lastCommittedTransactionNumber = transaction.getLastCommittedTransactionNumber();
     
        // run through all overlapping transactions
        for( transactionNumberIndex = lastCommittedTransactionNumber+1; transactionNumberIndex < transactionNumber; transactionNumberIndex++ )
        {
            // get transaction with transaction number of transactionNumberIndex
            checkedTransaction = committedTransactions.get(transactionNumberIndex);
            
            // make sure transaction with transactionNumberIndex was not aborted before
            if( checkedTransaction != null )
            {
                // check our own read set againt the write set of the checkedTranscation
                checkedTransactionWriteSet = checkedTransaction.getWriteSet();
                
                readSetIterator = readSet.iterator();
                
                // while loop 
                while( readSetIterator.hasNext() )
                {
                    // is an account in the read set part of the write set in the checkedTransaction>
                    checkedAccount = readSetIterator.next();
                    if( checkedTransactionWriteSet.containsKey( checkedAccount ))
                    {
                        transaction.log("[TransactionManager.validateTransaction] Transaction #" + transaction.getTransactionID() +
                                "failed: r/w conflic on account #" + checkedAccount + "with Transaction #" +
                                checkedTransaction.getTransactionID());
                        return false;
                    }
                }
             }
        }
        
        
        transaction.log("[TransactionManager.validateTransaction] Transaction #" + transaction.getTransactionID() + " successfully validated");
        return true ;
    }
    
    /**
     * writes the write set of a transaction into the operational data
     * 
     * @param transaction Transaction to be written
     */
    public void writeTransaction( Transaction transaction )
    {
        // initalize variables
        HashMap<Integer, Integer> transactionWriteSet = transaction.getWriteSet();
        int account;
        int balance; 
        
        // get all the entries of this write set
        for( Map.Entry<Integer, Integer> entry : transactionWriteSet.entrySet())
        {
            account = entry.getKey();
            balance = entry.getValue();
            
            // write this record into operational data
            TransactionServer.accountManager.write(account, balance);
            
            transaction.log("[TransactionManager.writeTransaction] Transaction #" + transaction.getTransactionID() + " written");
        }
    }
    
    /**
     * Objects of this inner class run transaction, one thread runs one transaction
     * on behalf of the client
     */
    public class TransactionManagerWorker extends Thread
    {
        // netowrking communcation realted fields
        Socket client = null; 
        ObjectInputStream readFromNet = null;
        ObjectOutputStream writeToNet = null;
        Message message = null;
        
        // transaction related fields
        Transaction transaction = null;
        int accountNumber = 0;
        int balance = 0;
        
        
        // flag for jumping out of while loop after this transaction closed
        boolean keepgoing = true;
        
        // the constructor just opens up the network channels
        private TransactionManagerWorker(Socket client)
        {
            this.client = client;
            try
            {
                readFromNet = new ObjectInputStream( client.getInputStream());
                writeToNet = new ObjectOutputStream(client.getOutputStream());
                
            } catch (IOException e)
            {
                System.out.println("[TransactionManagerWorker.run] Failed to open object streams ");
                System.exit(1);
            }
            // setting up object streams try/catch
        }
        
        public void run()
        {
            System.out.println("Thread is running from" + client.getInetAddress());
            // loop is left when transaction closes
            while(true)
            {
                try
                {
                    message = (Message) readFromNet.readObject();
                    System.out.println("Message from client: "+message.getType());
                }
                catch (IOException | ClassNotFoundException e )
                        {
                            System.out.print("[TransactionManagerWorker.run] Client shut down, shutting down as well/// ");
                            
                            System.exit(0);
                            return;
                        } 
                // prcoessing message
                switch (message.getType())
                {
            
                // case OPEN_TRANSACTION
                    case OPEN_TRANSACTION:
                        synchronized (runningTransactions)
                        {
                            // assign a new transaction ID, also pass in the last assigned transaction number
                            // as to the latter, that number may refer to a (prior, non-overlappuing) transaction that needed to be aborted
                            transaction = new Transaction(++transactionIdCounter, transactionNumberCounter );
                            runningTransactions.add(transaction);
                        }
                        try
                        {
                            writeToNet.writeObject( transaction.getTransactionID() );
                        }
                        catch (IOException e)
                        {
                            System.err.println("[TransactionManagerWorker.run] OPEN_TRANSACTION #" + transaction.getTransactionID() + "- Error with"
                                    + "writing transactionID ");
                        }
                        transaction.log("[TransactionManagerWorker.run]" + "OPEN_TRANSACTION" + "#" +
                                transaction.getTransactionID() );
                        break;
                        
            
                // case CLOSE_TRANSACTION
                        case CLOSE_TRANSACTION:
                        synchronized (runningTransactions)
                        {
                            runningTransactions.remove(transaction);
                            
                            if( validateTransaction(transaction))
                            {
                                // add this transaction to the committed transactions
                                committedTransactions.put(transaction.getTransactionNumber(), transaction);
                                
                                // write data to operational data
                                writeTransaction(transaction);
                                
                                // tell client transaciton committed
                                try{
                                    writeToNet.writeObject( TRANSACTION_COMMITTED );
                                }
                                catch (IOException e) {
                                    System.err.println("{TransactionMangerWorker.run] CLOSED_TRANSACTION #" + transaction.getTransactionID() + "- Error with"
                                    + "commiting transactionID");
                                    
                                }
                                transaction.log("[TransactionManagerWorker.run]" + "CLOSE_TRANSACTION" + "#" +
                                transaction.getTransactionID() );
                            }
                        }
                        
           
                        break;
                }
            }
               
            
                
            
        }
        
    }
}
