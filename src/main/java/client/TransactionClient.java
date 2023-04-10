/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

/**
 *
 * @author lalom
 */
public class TransactionClient {
    
    // client has it own ip and port #
    private static String host;
    private static int    port;
    
    // each client has a proxy to communicate with the server
    TransactionServerProxy Proxy = new TransactionServerProxy("192.168.0.4", 5555); 
    
    // set up a way for client to input an account number and an amount to initiate a transaction
    
    
    // constructor 
    public TransactionClient( String host, int port )
    {
        this.host = host;
        this.port = port;
    }
    
    
   public static void main(String[] args)
   {
       // make api calls to server proxy 
       TransactionClient client = new TransactionClient("127.0.0.1", 8081 );
       
       // One transaction
       client.Proxy.openTransaction();
       client.Proxy.write(1, -100);
       /*
       client.Proxy.write(2, 100);
       client.Proxy.write(3, -100);
       client.Proxy.write(2, 100);
        */
       client.Proxy.closeTransaction();
       
       
       
       
       
       
       
       
       
       
       
       client.Proxy.closeTransaction();
      
       // try a transaction to a server 
       
       
       
       
   }
}
