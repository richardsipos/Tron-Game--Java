package model;

public class Position {
    public int x, y;

    /**
     * Seting the current position of the Players
     * @param x
     * @param y 
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }    
    /**
     * Position of the player
     * @param d
     * @return 
     */
    public Position translate(Direction d){
        return new Position(x + d.x, y + d.y);
    }
    
}
