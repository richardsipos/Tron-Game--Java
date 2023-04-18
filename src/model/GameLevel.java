package model;

import java.util.ArrayList;

public class GameLevel {
    public final GameID        gameID;
    public final int           rows, cols;
    public final LevelItem[][] level;
    public Position            player1 = new Position(0, 0);
    public Position            player2 = new Position(0, 0);
    
    /**
     * Associating keys from the map with different obstacles and creating them.
     * @param gameLevelRows
     * @param gameID 
     */
    public GameLevel(ArrayList<String> gameLevelRows, GameID gameID){
        this.gameID = gameID;
        int c = 0;
        for (String s : gameLevelRows) if (s.length() > c) c = s.length();
        rows = gameLevelRows.size();
        cols = c;
        level = new LevelItem[rows][cols];
        
        for (int i = 0; i < rows; i++){
            String s = gameLevelRows.get(i);
            for (int j = 0; j < s.length(); j++){
                switch (s.charAt(j)){
                    case '@': player1 = new Position(j, i);
                              level[i][j] = LevelItem.EMPTY; break;
                    case '%': player2 = new Position(j, i);
                              level[i][j] = LevelItem.EMPTY; break;          
                    case '#': level[i][j] = LevelItem.WALL; break;
                    default:  level[i][j] = LevelItem.EMPTY; break;
                }
            }
            for (int j = s.length(); j < cols; j++){
                level[i][j] = LevelItem.EMPTY;
            }
        }
    }
    
    /**
     * Specialising the game level.
     * @param gl 
     */
    public GameLevel(GameLevel gl) {
        gameID = gl.gameID;
        rows = gl.rows;
        cols = gl.cols;
        level = new LevelItem[rows][cols];
        player1 = new Position(gl.player1.x, gl.player1.y);
        player2 = new Position(gl.player2.x, gl.player2.y);
        for (int i = 0; i < rows; i++){
            System.arraycopy(gl.level[i], 0, level[i], 0, cols);
        }
    }

    /**
     * The position of Player1
     * @return 
     */
    public boolean isValidPosition1(){

        return (player1.x >= 0 && player1.y >= 0 && player1.x < cols && player1.y < rows && level[player1.y][player1.x]==LevelItem.EMPTY);
    }
    
    /**
     * The position of Player2
     * @return 
     */
    public boolean isValidPosition2(){

        return (player2.x >= 0 && player2.y >= 0 && player2.x < cols && player2.y < rows && level[player2.y][player2.x]==LevelItem.EMPTY);
    }

    /**
     * Moving Player1 in the specialised direction
     * @param d1
     * @return 
     */
    public boolean movePlayer1(Direction d1){
        Position curr = player1;
        Position next = curr.translate(d1);
        player1 = next;
        return true;
    }
    /**
     * Moving Player1 in the specialised direction
     * @param d2
     * @return 
     */
    public boolean movePlayer2(Direction d2){
        Position curr = player2;
        Position next = curr.translate(d2);
        player2 = next;
        return true;
    }
    
    
    /**
     * Printing the levels of the game.
     */
    public void printLevel(){
        int x = player1.x, y = player1.y;
        int z = player2.x, v = player2.y;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if (i == y && j == x)
                    System.out.print('@');
                else if(z==i && v==j){
                    System.out.print('%');
                }
                else 
                    System.out.print(level[i][j].representation);
            }
            System.out.println("");
        }
    }

    
}