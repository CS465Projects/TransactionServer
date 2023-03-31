/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package account;

import java.util.ArrayList;

/**
 *
 * @author lalom
 */
public class AccountManager {
    
    // table of all acounts
    public static ArrayList<Account> accounts;
    
    // constructor
    public AccountManager()
    {
        // initalize table 
        accounts = new ArrayList();
    }
    
    // 
    public static void addAccount( Account account )
    {
        accounts.add( account );
    }
    
    public void removeAccount( Account account )
    {
        accounts.remove( account );
    }
    
    public static ArrayList<Account> getAllAccounts()
    {
        return accounts;
    }
    
    public static void createAccounts( int totalAccounts, int balance )
    {
        int index;
        Account newAccount; 
        
        for( index=0; index < totalAccounts; index++)
        {
            newAccount = new Account(index, balance);
            
           
            addAccount( newAccount );
        }
    }
    
    public static boolean readWriteRequests( int requests, int accountNumber )
    {
        return false; // stub 
    }
    
    // create accounts
}
