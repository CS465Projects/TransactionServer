/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import account.AccountManager;
import static message.MessageTypes.TRANSACTION_ABORTED;
import static message.MessageTypes.TRANSACTION_COMMITTED;

/**
 *
 * @author lalom
 */
public class TransactionClient {
    
    // client has it own ip and port #
    private static String host;
    private static int    port;
   
    public static TransactionServerProxy Proxy = new TransactionServerProxy("192.168.0.4", 5555); 
    
    // constructor 
    public TransactionClient( String host, int port )
    {
        this.host = host;
        this.port = port;
    }
    
    static class Transactions implements Runnable {
        private int accountNumber;
        private int amount;
        // each client has a proxy to communicate with the server
       
        public Transactions( int accountNumber, int amounnt )
        {
            this.accountNumber = accountNumber;
            this.amount = amount;
        }
        public void run() { 
           Proxy = new TransactionServerProxy("192.168.0.4", 5555); 
           boolean aborted = true; 
           while( aborted )
           {
           // loop while troop if get mind 
           Proxy.openTransaction();
           
           // need to check if opentransaction processs before read and write operations
           
           Proxy.read( accountNumber);
           Proxy.write(accountNumber, -amount);
           Proxy.read(accountNumber+1);
           Proxy.write(accountNumber+1, amount);
           
          // stop running the transaction if committed
           if( Proxy.closeTransaction() == TRANSACTION_COMMITTED )
                {
                 aborted = false;
                }
           }
           
        }
    }
    
    
   public static void main(String[] args)
   {
       TransactionClient client = new TransactionClient( "127.0.0.1", 8081 );
       // 
       int branchSum=0; 
       
       
       
       
       
       
       for( int index = 0; index < 20; index++ )
       {
           client.Proxy.openTransaction();
           
           // need to check if opentransaction processs before read and write operations
           
            client.Proxy.read( index);
           client.Proxy.write(index, 10);
           branchSum += client.Proxy.read(index+1);
           client.Proxy.write(index+1,10);
           
           
          // stop running the transaction if committed
           client.Proxy.closeTransaction();
       }
       
       
       System.out.println(" BRANCH SUM : "+ branchSum);
    
       
       
       
       
   }
}
