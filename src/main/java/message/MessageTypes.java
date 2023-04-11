package message;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */


/**
 *
 * @author lalom
 */
public interface MessageTypes {
    
    public static final int  OPEN_TRANSACTION      = 1;
    public static final int  CLOSE_TRANSACTION     = 2;
    
    public static final int  READ_TRANSACTION      = 3;
    public static final int  WRITE_TRANSACTION     = 4;
    
    public static final int  TRANSACTION_COMMITTED = 5;
    public static final int  TRANSACTION_ABORTED   = 6;
    
    public static final int  READ_REQUEST          = 7;
    public static final int  WRITE_REQUEST         = 8;
    
    public static final int  READ_REQUEST_RESPONSE = 9;
    public static final int  WRITE_REQUEST_RESPONSE = 9;
    
    
}
