package db;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Richard
 */
public class DataBaseSQL {
    
    private Connection conn;
    public DataBaseSQL(){
    
    try {
            
        conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/trondb?user=root&password=Kaman1234!&serverTimezone=UTC"
            );
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    
    public void insert(String winnerName,String loserName,long elapsedTime) throws ClassNotFoundException, SQLException{
        try{
            PreparedStatement winnerStat = conn.prepareStatement(
                  "INSERT IGNORE INTO"
                           + " trondb.tronlb (PlayerName,PlayerScore) " 
                           + " VALUES (" 
                           + " ' " + winnerName + " ' " + "," + elapsedTime +")"
                    
            );
            winnerStat.execute();
        }catch(SQLException e){
            System.out.println("Problems at insertion");
        }
    }
    
    public void leaderboardStat(){
        try{
            
            Statement statement = conn.createStatement();
            ResultSet rset = statement.executeQuery("SELECT * FROM trondb.tronlb ORDER BY PlayerScore DESC");
            String[][] results = new String[10][3];
            
            for(int i=0;i<10;i++){
                if(rset.next()){
                    results[i][0] = Integer.toString(i+1);
                    results[i][1]=rset.getString("PlayerName");
                    results[i][2] = rset.getString("PlayerScore"); 
                }
                
            }      
            showLeaderBoard(results);
        } catch (SQLException e){
            System.out.println("ERROR IS: "+ e);
        }
    }
    
    public void showLeaderBoard(String [][]scores){
        String tableContet[] = {"Position","PlayerName","PlayerScore"};
        JTable leaderBoard = new JTable(scores,tableContet);
        JOptionPane.showMessageDialog(
                    null, new JScrollPane(leaderBoard), "Best Scores", JOptionPane.INFORMATION_MESSAGE
            );

    }
    
}


