/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package account;

/**
 *
 * @author lalom
 */
public class Account {
    
    private int accountNumber;
    private int balance;
    
    public Account( int accountNumber, int balance )
    {
        // initalize account infomation
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
  
    //
    public int getBalance()
    {
        return balance;
    }
    
    public int getAccountNumber()
    {
        return accountNumber;
    }
    
    public void write( int amount )
    {
        balance += amount;
    }
    
    
    
}
