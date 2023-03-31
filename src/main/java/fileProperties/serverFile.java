/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fileProperties;

import static java.awt.PageAttributes.MediaType.C;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author lalom
 */
public class serverFile {
   
    public static String ipAddress;
    public static int portNumber;
    public static int numAccounts;
    public static int initialBalances;
    
    public serverFile(String fileName) throws IOException {
     
    try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\lalom\\OneDrive\\Documents\\NetBeansProjects\\TransactionApp\\src\\main\\java\\serverProperties.txt"));

            String line = reader.readLine();
            while (line != null) { 
                String[] parts = line.split("=");
                if( line.contains("port") )
                {
                 portNumber = Integer.parseInt(parts[1].trim());
                 
                }
                else if( line.contains( "IP") )
                {
                 ipAddress = parts[1].trim();
                }
                else if( line.contains( "Accounts" ))
                {
                 numAccounts = Integer.parseInt(parts[1].trim());
                }
                else if( line.contains("balance"))
                {
                    initialBalances = Integer.parseInt(parts[1].trim());
                }
                
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getIpAddress() {
        return ipAddress;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public int getNumAccounts() {
        return numAccounts;
    }

    public int getInitialBalances() {
        return initialBalances;
    }
    
}
    
    
    // getter functions 
