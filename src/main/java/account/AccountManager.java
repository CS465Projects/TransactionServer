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
    
    /**
     *
     * @param accountNumber
     * @return
     */
    public static Integer read(int accountNumber) {
        int index, balance = 0;
   
        for( index=0; index < accounts.size(); index++)
        {
           if( accounts.get(index).getAccountNumber() == accountNumber )
           {
               balance = accounts.get(index).getBalance();
           }
        }
        return balance;  
    }
    
    // create accounts

    public void write(int accountNumber, int balance) {
        int index;
   
        for( index=0; index < accounts.size(); index++)
        {
           if( accounts.get(index).getAccountNumber() == accountNumber )
           {
              accounts.get(index).write(balance);
           }
        }
        
    }
}
