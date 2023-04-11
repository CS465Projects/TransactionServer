/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transactionServer;

import account.Account;
import account.AccountManager;
import fileProperties.serverFile;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import transactionServer.TransactionManager.TransactionManagerWorker;

/**
 *
 * @author lalom
 */
public class TransactionServer {
    
    private static String serverIp;
    
    private static int serverPort;
    
    public static int messageCount = 0;
    
    static boolean transactionView = false;  
    
    public static AccountManager accountManager;
    
    
    
    public static int getMessageCount() {
        
        return messageCount;
    }
    
    /**
     *
     * @param File
     */
    public TransactionServer( serverFile File )
    {
        // create an account manager for the server
        accountManager = new AccountManager();
        // set up accounts
        accountManager.createAccounts(File.getNumAccounts(),
                                      File.getInitialBalances());
        
        System.out.println("Transaction Server");
        
        // try / catch
        try
        {
            // create a server socket to list and accept connection
            ServerSocket transServSock = new ServerSocket( File.getPortNumber() );
            
            // set up a transaction manager
            TransactionManager manager = new TransactionManager();
            
            // listen to connections / calls 
            while(true)
            {
                System.out.println("Waiting for transactions");
                // socket for clinet
                Socket clientSocket = transServSock.accept();
                messageCount++;
                // send the socket to the manager
                manager.runTransaction( clientSocket ); 
               
            }
        
        // create a thread with client sockets   
        } catch( Exception ex )
        {
            ex.printStackTrace();
        }
        
        
        
    }
    
    public static void main( String[] args) throws IOException{
        serverFile file = new serverFile("C:\\Users\\lalom\\OneDrive\\Documents\\NetBeansProjects\\TransactionApp\\src\\main\\java\\serverProperties.txt" );
        
        new TransactionServer( file );
        
        
    }
    
    // create inital accounts
    
    
}
