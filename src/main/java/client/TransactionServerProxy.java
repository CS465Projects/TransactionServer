/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import com.mycompany.transactionapp.MessageTypes;
/**
 * This class represents the proxy that acts on behalf of the transaction server on the client side.
 * It provides an implementation of the coordinator interface to the client, hiding the fact
 * that there is a network in between.
 * From the client's perspective, an object of this class IS the transaction
 * @author 
 */
public class TransactionServerProxy implements MessageTypes{
    // initalize global varaibles
    
    // host and port
    String host = null;
    int port;
    
    // socket/ input and output streams
    // transaction id
    
    /**
     * Constructor
     * 
     * @param host IP address of the transaction server
     * @param port port number of the transaction server
     * 
     */
    TransactionServerProxy( String host, int port )
    {
        this.host = host;
        this.port = port;
        
    }
    
    /**
     * Open a transaction
     * 
     * @return the transaction ID
     */
    public int openTransaction()
    {
        // try /catch 
        
        return 0; // sub 
    }
    
    /**
     * Request this transaction to be closed
     * 
     * @return the status, i.e. either TRANSACTION_COMMITTED or TRANSACTION_ABORTED
     */
    public int closeTransaction()
    {
        return 0;// sub 
    }
    
    /**
     * Reading a value from an account
     * 
     * @param accountNumber
     * @return the balance of the account
     * @throws transaction.server.lock.TransactionAbortedException
     */
    public int read( int accountNumber ) // throws TransactionAbortedException
    {
        return 0; // stubt
    }
    
    /**
     * Writing the value to account
     * @param accountNumber
     * @param amount
     * 
     * @throws transaction.server.lock.TransactionAbortedException 
     */
    public void write( int accountNumber, int amount )// throws TransactionAbortedException 
    {
        
    }
    
    
}
