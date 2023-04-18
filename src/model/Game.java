package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TimerTask;
import javax.swing.Timer;
import res.ResourceLoader;

public class Game {
    private final HashMap<String, HashMap<Integer, GameLevel>> gameLevels;
    private GameLevel gameLevel = null;
    protected javax.swing.Timer refresherTimer = null;
    
    //private Timer timer = new Timer();

    /**
     * Initialiseing the game.
     */
    public Game() {
        gameLevels = new HashMap<>();
        readLevels();
    }

    /**
     * Loading the game
     * @param gameID 
     */
    public void loadGame(GameID gameID){
        
        gameLevel = new GameLevel(gameLevels.get(gameID.difficulty).get(gameID.level));
    }
    
    /**
     * Printing one of the 10 levels
     */
    public void printGameLevel(){ gameLevel.printLevel(); }
    
    
    /**
     * Coloring the field which is touched by on of the vehicles
     * @param x coord
     * @param y coord
     * @param stg the way it Touches it.
     */
    public void ColorToField(int x,int y,String stg){
        if(stg.equals("RTopToLeft")){
            gameLevel.level[x][y] = LevelItem.RTopToLeft;
        }else if(stg.equals("RDownToLeft")){
            gameLevel.level[x][y] = LevelItem.RDownToLeft; 
        }else if(stg.equals("RDownToRight")){
            gameLevel.level[x][y] = LevelItem.RDownToRight ; 
        }else if(stg.equals("RTopToRight")){
            gameLevel.level[x][y] = LevelItem.RTopToRight ; 
        }
         else if(stg.equals("CTopToLeft")){
            gameLevel.level[x][y] = LevelItem.CTopToLeft;
        }else if(stg.equals("CDownToLeft")){
            gameLevel.level[x][y] = LevelItem.CDownToLeft; 
        }else if(stg.equals("CDownToRight")){
            gameLevel.level[x][y] = LevelItem.CDownToRight ; 
        }else if(stg.equals("CTopToRight")){
            gameLevel.level[x][y] = LevelItem.CTopToRight ; 
        }
         else if(stg.equals("MTopToLeft")){
            gameLevel.level[x][y] = LevelItem.MTopToLeft;
        }else if(stg.equals("MDownToLeft")){
            gameLevel.level[x][y] = LevelItem.MDownToLeft; 
        }else if(stg.equals("MDownToRight")){
            gameLevel.level[x][y] = LevelItem.MDownToRight ; 
        }else if(stg.equals("MTopToRight")){
            gameLevel.level[x][y] = LevelItem.MTopToRight ; 
        }
         else if(stg.equals("GTopToLeft")){
            gameLevel.level[x][y] = LevelItem.GTopToLeft;
        }else if(stg.equals("GDownToLeft")){
            gameLevel.level[x][y] = LevelItem.GDownToLeft; 
        }else if(stg.equals("GDownToRight")){
            gameLevel.level[x][y] = LevelItem.GDownToRight ; 
        }else if(stg.equals("GTopToRight")){
            gameLevel.level[x][y] = LevelItem.GTopToRight; 
        }
        else if(stg.equals("GHorizontal")){
            gameLevel.level[x][y] = LevelItem.GHorizontal;
        }else if(stg.equals("GVertical")){
            gameLevel.level[x][y] = LevelItem.GVertical;
        }else if(stg.equals("RHorizontal")){
            gameLevel.level[x][y] = LevelItem.RHorizontal;
        }else if(stg.equals("RVertical")){
            gameLevel.level[x][y] = LevelItem.RVertical;
        }else if(stg.equals("CHorizontal")){
            gameLevel.level[x][y] = LevelItem.CHorizontal;
        }else if(stg.equals("CVertical")){
            gameLevel.level[x][y] = LevelItem.CVertical;
        }else if(stg.equals("MHorizontal")){
            gameLevel.level[x][y] = LevelItem.MHorizontal;
        }else if(stg.equals("MVertical")){
            gameLevel.level[x][y] = LevelItem.MVertical;
        }
        
    }
    
    public boolean isValidPosition1(){
        return gameLevel.isValidPosition1();
    } 
    public boolean isValidPosition2(){
        return gameLevel.isValidPosition2();
    } 
    
    

    /**
     * A step for player1.
     * @param d1 Player1 's current direction
     * @return 
     */
    public boolean step1(Direction d1){
        return (gameLevel.movePlayer1(d1));
    }
    /**
     * A step for player2.
     * @param d2 Player1 's current direction
     * @return 
     */
    public boolean step2(Direction d2){
        return (gameLevel.movePlayer2(d2));
    }
    
    // ------------------------------------------------------------------------
    // Getter methods
    // ------------------------------------------------------------------------
    
    public Collection<String> getDifficulties(){ return gameLevels.keySet(); }
    
    public Collection<Integer> getLevelsOfDifficulty(String difficulty){
        if (!gameLevels.containsKey(difficulty)) return null;
        return gameLevels.get(difficulty).keySet();
    }
    
    public boolean isLevelLoaded(){ return gameLevel != null; }
    public int getLevelRows(){ return gameLevel.rows; }
    public int getLevelCols(){ return gameLevel.cols; }
    public LevelItem getItem(int row, int col){ return gameLevel.level[row][col]; }
    public GameID getGameID(){ return (gameLevel != null) ? gameLevel.gameID : null; }

    public Position getPlayer1Pos(){
        return new Position(gameLevel.player1.x, gameLevel.player1.y); 
    }
    
    public Position getPlayer2Pos(){
        return new Position(gameLevel.player2.x, gameLevel.player2.y); 
    }
    
    // ------------------------------------------------------------------------
    // Utility methods to load game levels from res/levels.txt resource file.
    // ------------------------------------------------------------------------

    /**
     * Reading the levels from the way they were stored.
     */
    private void readLevels(){
        InputStream is;
        is = ResourceLoader.loadResource("res/levels.txt");
        
        try (Scanner sc = new Scanner(is)){
            String line = readNextLine(sc);
            ArrayList<String> gameLevelRows = new ArrayList<>();
            
            while (!line.isEmpty()){
                GameID id = readGameID(line);
                if (id == null) return;

                gameLevelRows.clear();
                line = readNextLine(sc);
                while (!line.isEmpty() && line.trim().charAt(0) != ';'){
                    gameLevelRows.add(line);                    
                    line = readNextLine(sc);
                }
                addNewGameLevel(new GameLevel(gameLevelRows, id));
            }
        } catch (Exception e){
            System.out.println("Problem rised.");
        }
        
    }
    /**
     * Reading a new game level.
     * @param gameLevel 
     */
    private void addNewGameLevel(GameLevel gameLevel){
        HashMap<Integer, GameLevel> levelsOfDifficulty;
        if (gameLevels.containsKey(gameLevel.gameID.difficulty)){
            levelsOfDifficulty = gameLevels.get(gameLevel.gameID.difficulty);
            levelsOfDifficulty.put(gameLevel.gameID.level, gameLevel);
        } else {
            levelsOfDifficulty = new HashMap<>();
            levelsOfDifficulty.put(gameLevel.gameID.level, gameLevel);
            gameLevels.put(gameLevel.gameID.difficulty, levelsOfDifficulty);
        }
    }
  
    /**
     * Reading the the next line from the map;
     * @param sc
     * @return 
     */
    private String readNextLine(Scanner sc){
        String line = "";
        while (sc.hasNextLine() && line.trim().isEmpty()){
            line = sc.nextLine();
        }
        return line;
    }
    
    /**
     * Reading the gameID
     * @param line 
     * @return 
     */
    private GameID readGameID(String line){
        line = line.trim();
        if (line.isEmpty() || line.charAt(0) != ';') return null;
        Scanner s = new Scanner(line);
        s.next(); 
        if (!s.hasNext()) return null;
        String difficulty = s.next().toUpperCase();
        if (!s.hasNextInt()) return null;
        int id = s.nextInt();
        return new GameID(difficulty, id);
    }    
}