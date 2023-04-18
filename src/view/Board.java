package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JPanel;
import model.Game;
import model.LevelItem;
import model.Position;
import res.ResourceLoader;
import model.Direction;

public class Board extends JPanel {
    private Game game;
    private Image player1,player2;
    private final Image  wall, empty;
    private double scale;
    private int scaled_size;
    private final int tile_size = 32;
    
    /**
     * Initialise the Board
     * @param g the game we weill play
     * @throws IOException potential error
     */
    public Board(Game g) throws IOException{
        game = g;
        scale = 1.0;
        scaled_size = (int)(scale * tile_size);
        player1 = ResourceLoader.loadImage("res/playerUp.jpg");
        player2 = ResourceLoader.loadImage("res/playerUp.jpg");
        wall = ResourceLoader.loadImage("res/wall.jpg");
        empty = ResourceLoader.loadImage("res/bgPattern.jpg");
    }
    /**
     * The scale we are playing.
     * @param scale the size of the multiple.
     * @return 
     */
    public boolean setScale(double scale){
        this.scale = scale;
        scaled_size = (int)(scale * tile_size);
        return refresh();
    }
    /**
     * Getter
     * @return 
     */
    public int getScaled_size(){
        return scaled_size;
    }
    /**
     * Check if Player 1's position is valid
     * @return 
     */
    public boolean isValidPositionP1(){
        return game.isValidPosition1();
    }
    /**
     * Check if Playes 2's position is valid
     * @return 
     */
    public boolean isValidPositionP2(){
        return game.isValidPosition2();
    }
    
    /**
     * It refreshes the map.
     * @return 
     */
    public boolean refresh(){
        if (!game.isLevelLoaded()) return false;
        Dimension dim = new Dimension(game.getLevelCols() * scaled_size, game.getLevelRows() * scaled_size);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
        return true;
    }
    
    /**
     * It sets the image of the direction of the player to the new one.
     * @param d1 the current Direction
     * @throws IOException 
     */
    public void setPlayer1DirectionIMG(Direction d1) throws IOException {
        if(d1==Direction.LEFT){
            player1=ResourceLoader.loadImage("res/playerLeft.jpg");
        }else if(d1==Direction.RIGHT){
            player1=ResourceLoader.loadImage("res/playerRight.jpg");
        }else if(d1==Direction.UP){
            player1=ResourceLoader.loadImage("res/playerUp.jpg");
        }else if(d1==Direction.DOWN){
            player1=ResourceLoader.loadImage("res/playerDown.jpg");
        }
    }
    /**
     * It sets the image of the direction of the player to the new one.
     * @param d2 the current Direction
     * @throws IOException 
     */
    public void setPlayer2DirectionIMG(Direction d2) throws IOException {
        if(d2==Direction.LEFT){
            player2=ResourceLoader.loadImage("res/playerLeft.jpg");
        }else if(d2==Direction.RIGHT){
            player2=ResourceLoader.loadImage("res/playerRight.jpg");
        }else if(d2==Direction.UP){
            player2=ResourceLoader.loadImage("res/playerUp.jpg");
        }else if(d2==Direction.DOWN){
            player2=ResourceLoader.loadImage("res/playerDown.jpg");
        }
    }
    
    
    
    
    /**
     * The method which will draw the map in totality. It will draw it from the images in the res package.
     * @param g gives us better coordinate and geometry positioning
     */
    @Override
    protected void paintComponent(Graphics g){
        if (!game.isLevelLoaded()) return;
        Graphics2D gr = (Graphics2D)g;
        int w = game.getLevelCols();
        int h = game.getLevelRows();
        Position p1 = game.getPlayer1Pos();
        Position p2 = game.getPlayer2Pos();
        for (int y = 0; y < h; y++){
            for (int x = 0; x < w; x++){
                Image img = null;
                LevelItem li = game.getItem(y, x);
                try{
                    switch (li){
                        case WALL -> img = wall;
                        case EMPTY -> img = empty;
                        case RTopToLeft -> img = ResourceLoader.loadImage("res/bgPatternRTopToLeft.jpg");
                        case RTopToRight -> img = ResourceLoader.loadImage("res/bgPatternRTopToRight.jpg");
                        case RDownToLeft -> img = ResourceLoader.loadImage("res/bgPatternRDownToLeft.jpg");
                        case RDownToRight -> img = ResourceLoader.loadImage("res/bgPatternRDownToRight.jpg");
                        case CTopToLeft -> img = ResourceLoader.loadImage("res/bgPatternCTopToLeft.jpg");
                        case CTopToRight -> img = ResourceLoader.loadImage("res/bgPatternCTopToRight.jpg");
                        case CDownToLeft -> img = ResourceLoader.loadImage("res/bgPatternCDownToLeft.jpg");
                        case CDownToRight -> img = ResourceLoader.loadImage("res/bgPatternCDownToRight.jpg");
                        case MTopToLeft -> img = ResourceLoader.loadImage("res/bgPatternMTopToLeft.jpg");
                        case MTopToRight -> img = ResourceLoader.loadImage("res/bgPatternMTopToRight.jpg");
                        case MDownToLeft -> img = ResourceLoader.loadImage("res/bgPatternMDownToLeft.jpg");
                        case MDownToRight -> img = ResourceLoader.loadImage("res/bgPatternMDownToRight.jpg");
                        case GTopToLeft -> img = ResourceLoader.loadImage("res/bgPatternGTopToLeft.jpg");
                        case GTopToRight -> img = ResourceLoader.loadImage("res/bgPatternGTopToRight.jpg");
                        case GDownToLeft -> img = ResourceLoader.loadImage("res/bgPatternGDownToLeft.jpg");
                        case GDownToRight -> img = ResourceLoader.loadImage("res/bgPatternGDownToRight.jpg");
                        case RHorizontal -> img = ResourceLoader.loadImage("res/bgPatternRHorizontal.jpg");
                        case RVertical -> img = ResourceLoader.loadImage("res/bgPatternRVertical.jpg");
                        case GHorizontal -> img = ResourceLoader.loadImage("res/bgPatternGHorizontal.jpg");
                        case GVertical -> img = ResourceLoader.loadImage("res/bgPatternGVertical.jpg");
                        case CHorizontal -> img = ResourceLoader.loadImage("res/bgPatternCHorizontal.jpg");
                        case CVertical -> img = ResourceLoader.loadImage("res/bgPatternCVertical.jpg");
                        case MHorizontal -> img = ResourceLoader.loadImage("res/bgPatternMHorizontal.jpg");
                        case MVertical -> img = ResourceLoader.loadImage("res/bgPatternMVertical.jpg");
                                
                        }
                }catch(Exception e){
                    System.out.println("Something Went Wrong In The Swictch statemenet");
                }
                
                if (p1.x == x && p1.y == y) img = player1;
                if (p2.x == x && p2.y == y) img = player2;
                if (img == null) continue;
                gr.drawImage(img, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
            }
        }
    }
    
}