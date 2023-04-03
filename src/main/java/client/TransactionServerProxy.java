/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import message.MessageTypes;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import message.Message;
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
    private Socket dbConnection = null;
    private ObjectOutputStream writeToNet = null;
    private ObjectInputStream readFromNet = null;
    private Integer transactionID = 0;
    

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
        try {
            dbConnection = new Socket( host, port);
            writeToNet = new ObjectOutputStream( dbConnection.getOutputStream() );
            readFromNet = new ObjectInputStream( dbConnection.getInputStream() );
            
        }catch (IOException ex) {
            System.out.println( "[TransactionServerProxy.openTransactions] Error occured when opening object streams");
            ex.printStackTrace();
        }
        try {
            writeToNet.writeObject( new Message( OPEN_TRANSACTION, null ));
            transactionID = (Integer) readFromNet.readObject();
            
        } catch( IOException | ClassNotFoundException | NullPointerException ex) {
            System.out.println( "[TransactionServerProxy.openTransaction] Error when wrtie/reading messages");
            ex.printStackTrace();
        }
        
        return transactionID;
    }
    
    /**
     * Request this transaction to be closed
     * 
     * @return the status, i.e. either TRANSACTION_COMMITTED or TRANSACTION_ABORTED
     */
    public int closeTransaction()
    {
        int returnStatus = TRANSACTION_COMMITTED;
        
        try {
            writeToNet.writeObject( new Message( CLOSE_TRANSACTION, null ));
            returnStatus = (Integer) readFromNet.readObject();
            
            readFromNet.close();
            writeToNet.close();
            dbConnection.close();
        } catch (Exception ex) {
            System.out.println("[TransactionServerProxy.closeTransaction] Error occured");
            ex.printStackTrace();
        }
        return returnStatus; 
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
        Message message = new Message(READ_REQUEST, accountNumber );
        
        try {
            writeToNet.writeObject( message );
            message = (Message) readFromNet.readObject();
        } catch (Exception ex ) {
            System.out.println( "[TransactionSeverProxy.read] Error occurred");
            ex.printStackTrace();
        }
        
        if( message.getType() ==  READ_REQUEST_RESPONSE )
        {
            return (Integer) message.getContent();
        }
        else
        {
            return TRANSACTION_ABORTED; 
        }
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
        Object[] content = new Object[]{ accountNumber, amount };
        Message message = new Message( WRITE_REQUEST, content );
        
        try{
            writeToNet.writeObject( message );
            message = (Message) readFromNet.readObject();
            
        } catch( IOException | ClassNotFoundException ex ) {
            System.out.println( "[TransactionServerProxy.write] Error Occurred: IOException | ClassNotFoundException ");
            ex.printStackTrace();
            System.err.print("\n\n");
        }
        
        if(message.getType() == TRANSACTION_ABORTED)
        {
            System.out.println("Aborted Transaction");
        }
        
        
    }
    
    
}
