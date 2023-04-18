/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import view.MainWindow;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Richard
 */
public class Main {
    /**
     * The main program.
     * @param args
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        try {
            MainWindow mainWindow = new MainWindow();
            
                        
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
