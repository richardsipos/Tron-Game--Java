package view;

import db.DataBaseSQL;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import model.Direction;
import model.Game;
import model.GameID;
import model.Position;

public class MainWindow extends JFrame{
    private final Game game;
    private Board board;
    private final JLabel gameStatLabel;   
    private final JFrame gameStartMenu;
    
    private final JTextField Player1TextField;
    private final JTextField Player2TextField;
    
    //GameRelatedVariables
    private Timer timer;
    private long elapsedTime=0;
    private long startTime;
    private String Player1Name = "";
    private String Player2Name = "";
    private String winnerPlayer = "";
    private String loserPlayer = "";
    private Color Player1Color = Color.WHITE;
    private Color Player2Color = Color.WHITE;
    private Direction d1 = Direction.UP;
    private Direction d2 = Direction.UP;
    private Direction d1Old = Direction.UP;
    private Direction d2Old = Direction.UP;
    private final JButton doneButton;
    private final JButton leaderBoardButton;
    private DataBaseSQL dbSQL;
    
    public MainWindow() throws IOException{
        
        
        
        //here comes the starting menu
        try{
            dbSQL = new DataBaseSQL();
        }catch(Exception e){
            System.out.println("Problems appeared");
        }
        gameStartMenu = new JFrame();
        JPanel temp = new JPanel();
       
        JLabel Player1TextFieldLabel = new JLabel("First player's name:");
        Player1TextField = new JTextField(25);
        JRadioButton r1 = new JRadioButton("Red");
        JRadioButton r2 = new JRadioButton("Green");
        JRadioButton r3 = new JRadioButton("Magenta");
        JRadioButton r4 = new JRadioButton("Cyan");
        ButtonGroup bg = new ButtonGroup();
        bg.add(r1);bg.add(r2);bg.add(r3);bg.add(r4);
        
        temp.add(Player1TextFieldLabel);
        temp.add(Player1TextField);
        temp.add(r1);
        temp.add(r2);
        temp.add(r3);
        temp.add(r4);
        
        JLabel Player2TextFieldLabel = new JLabel("Second player's name:");
        Player2TextField = new JTextField(25);
        JRadioButton r5 = new JRadioButton("Red");
        JRadioButton r6 = new JRadioButton("Green");
        JRadioButton r7 = new JRadioButton("Magenta");
        JRadioButton r8 = new JRadioButton("Cyan");
        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(r5);bg2.add(r6);bg2.add(r7);bg2.add(r8);
        
        temp.add(Player2TextFieldLabel);
        temp.add(Player2TextField);
        temp.add(r5);
        temp.add(r6);
        temp.add(r7);
        temp.add(r8);
        
        
        doneButton = new JButton("Start");
        leaderBoardButton = new JButton("LeaderBoard");
        
        temp.add(doneButton);
        temp.add(leaderBoardButton);
        gameStartMenu.setSize(700,150);
        gameStartMenu.setLayout(new BorderLayout());
        gameStartMenu.add(temp);
        gameStartMenu.setVisible(true);
        gameStartMenu.setResizable(false);
        
        
       
        
        
        
        //here ends the starting menu
        
        
        
        //here begins the game
        game = new Game();
        
        
        setTitle("Tron");
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        URL url = MainWindow.class.getClassLoader().getResource("res/bgPattern.jpg");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        
        //menubar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Játék");
        JMenu menuGameLevel = new JMenu("Pálya");
        JMenu menuGameScale = new JMenu("Nagyítás");
        createGameLevelMenuItems(menuGameLevel);
        createScaleMenuItems(menuGameScale, 1.0, 2.0, 0.5);

        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Kilépés") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuGame.add(menuGameLevel);
        menuGame.add(menuGameScale);
        menuGame.addSeparator();
        menuGame.add(menuGameExit);
        menuBar.add(menuGame);
        setJMenuBar(menuBar);
        
        
        
        setLayout(new BorderLayout(0, 10));
        gameStatLabel = new JLabel("label");
        

        add(gameStatLabel, BorderLayout.NORTH);
        try { add(board = new Board(game), BorderLayout.CENTER); } catch (IOException ex) {}
        
        
        
        addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyPressed(KeyEvent ke) {
                super.keyPressed(ke); 
                if (!game.isLevelLoaded()) return;
                int kk1 = ke.getKeyCode();
                int kk2 = ke.getKeyCode();
                switch (kk1){
                    case KeyEvent.VK_LEFT:  d1 = Direction.LEFT; break;
                    case KeyEvent.VK_RIGHT: d1 = Direction.RIGHT; break;
                    case KeyEvent.VK_UP:    d1 = Direction.UP; break;
                    case KeyEvent.VK_DOWN:  d1 = Direction.DOWN; break;
                    case KeyEvent.VK_ESCAPE: game.loadGame(game.getGameID());//d1=Direction.UP; d1Old=Direction.UP;
                }
                if(d1Old==Direction.LEFT && d1==Direction.RIGHT || d1Old==Direction.RIGHT && d1==Direction.LEFT 
                        || d1Old==Direction.UP && d1==Direction.DOWN || d1Old==Direction.DOWN && d1==Direction.UP){
                    d1=d1Old;
                }
                switch (kk2){
                    case KeyEvent.VK_A:  d2 = Direction.LEFT; break;
                    case KeyEvent.VK_D: d2 = Direction.RIGHT; break;
                    case KeyEvent.VK_W:    d2 = Direction.UP; break;
                    case KeyEvent.VK_S:  d2 = Direction.DOWN; break;
                    case KeyEvent.VK_ESCAPE: game.loadGame(game.getGameID());//d2=Direction.UP; d2Old=Direction.UP;
                }
                if(d2Old==Direction.LEFT && d2==Direction.RIGHT || d2Old==Direction.RIGHT && d2==Direction.LEFT 
                        || d2Old==Direction.UP && d2==Direction.DOWN || d2Old==Direction.DOWN && d2==Direction.UP){
                    d2=d2Old;
                }
                board.repaint();
            }
        });

        setResizable(false);
        setLocationRelativeTo(null);
        game.loadGame(new GameID("EASY", 1));
        board.refresh();
        pack();

//        menuBar.addActionListener(new ActionListener() {
//            System.out.println("Clicked");
//        });
        
        
        leaderBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbSQL.leaderboardStat();
                
            }
        
        });
        doneButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                if((r5.isSelected() || r6.isSelected() || r7.isSelected() || r8.isSelected()) && (r1.isSelected() || r2.isSelected() || r3.isSelected() || r4.isSelected())){
                    Player2Name=Player1TextField.getText();
                    Player1Name=Player2TextField.getText();
                    if(r1.isSelected()){
                        Player2Color=Color.RED;
                    }else if(r2.isSelected()){
                        Player2Color=Color.GREEN;
                    }else if(r3.isSelected()){
                        Player2Color=Color.MAGENTA;
                    }else if(r4.isSelected()){
                        Player2Color=Color.CYAN;
                    }
                    if(r5.isSelected()){
                        Player1Color=Color.RED;
                    }else if(r6.isSelected()){
                        Player1Color=Color.GREEN;
                    }else if(r7.isSelected()){
                        Player1Color=Color.MAGENTA;
                    }else if(r8.isSelected()){
                        Player1Color=Color.CYAN;
                    }
                    
                    if(Player1Color!=Player2Color){
                        startTime = System.currentTimeMillis();
                        timer = new Timer(10, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                gameStatLabel.setText(elapsedTimePunctual()-startTime + " ms");
                            }
                        });
                        startTime = System.currentTimeMillis();
                        timer.start();
                        
                        setVisible(true);
                        gameStartMenu.setVisible(false);
                        new Timer(1000,taskPerformer).start();
                    }
                    
                    
                }
                
            } 
        });
        
 
    }
    
    private long elapsedTimePunctual(){
        return System.currentTimeMillis();
    }
    

    
    private void createGameLevelMenuItems(JMenu menu){
        for (String s : game.getDifficulties()){
            JMenu difficultyMenu = new JMenu(s);
            menu.add(difficultyMenu);
            for (Integer i : game.getLevelsOfDifficulty(s)){
                JMenuItem item = new JMenuItem(new AbstractAction("Level-" + i) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.loadGame(new GameID(s, i));
                        board.refresh();
                        pack();
                    }
                });
                difficultyMenu.add(item);
            }
        }
    }
    
    private void createScaleMenuItems(JMenu menu, double from, double to, double by){
        while (from <= to){
            final double scale = from;
            JMenuItem item = new JMenuItem(new AbstractAction(from + "x") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (board.setScale(scale)) pack();
                }
            });
            menu.add(item);
            
            if (from == to) break;
            from += by;
            if (from > to) from = to;
        }
    }
    
    
    
    /**
     * This function will be called again and again every 1 sec and it will move the players to their corresponding direction. It will also 
     * choose the right name of the image which will be showed on the map.
     */
    ActionListener taskPerformer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt){
            elapsedTime++;
            //oneSecPassed()
            try{
                
                board.setPlayer1DirectionIMG(d1);
                board.setPlayer2DirectionIMG(d2);
                
                
                //dragging the line
                Position p1 = game.getPlayer1Pos();
                Position p2 = game.getPlayer2Pos();

                if (!board.isValidPositionP1()){ 
                    winnerPlayer=Player2Name;
                    loserPlayer = Player1Name;
                    try{
                        
                        dbSQL.insert(winnerPlayer,loserPlayer,elapsedTime);
                    }catch(Exception e){
                        System.out.println("The exception is the following"+e);
                    }
                    JOptionPane.showMessageDialog(MainWindow.this, "Gratulálok!"+Player2Name+" nyert!", "Gratulálok!", JOptionPane.INFORMATION_MESSAGE);
                    new Timer(1000,taskPerformer).stop();
                    System.exit(0);
                }


                if (!board.isValidPositionP2()){ 
                    winnerPlayer=Player1Name;
                    loserPlayer = Player2Name;
                    try{
                        DataBaseSQL dbSQL = new DataBaseSQL();
                        dbSQL.insert(winnerPlayer,loserPlayer,elapsedTime);
                    }catch(Exception e){
                        System.out.println("The exception is the following"+e);
                    }
                    
                    JOptionPane.showMessageDialog(MainWindow.this, "Gratulálok! "+Player1Name+" nyert!", "Gratulálok!", JOptionPane.INFORMATION_MESSAGE);
                    new Timer(1000,taskPerformer).stop();
                    System.exit(0);
                }
                
                if(d1Old!=null){
                    if(Player1Color==Color.RED){
                        
                        if(d1Old==Direction.UP && d1==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "RDownToLeft");
                        }
                        else if(d1Old==Direction.UP && d1==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "RDownToRight");
                        }
                        else if(d1Old==Direction.DOWN && d1==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "RTopToLeft");
                        }
                        else if(d1Old==Direction.DOWN && d1==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "RTopToRight");
                        }else if(d1==Direction.UP && d1Old==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "RTopToRight");
                        }
                        else if(d1==Direction.UP && d1Old==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "RTopToLeft");
                        }
                        else if(d1==Direction.DOWN && d1Old==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "RDownToRight");
                        }
                        else if(d1==Direction.DOWN && d1Old==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "RDownToLeft");
                        }
                        else if(d1Old==Direction.UP && d1==Direction.UP || d1Old==Direction.DOWN && d1==Direction.DOWN){
                            game.ColorToField(p1.y, p1.x, "RVertical");
                        }
                        else if(d1Old==Direction.LEFT && d1==Direction.LEFT || d1Old==Direction.RIGHT && d1==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "RHorizontal");
                        }
                    }else if(Player1Color==Color.GREEN){
                        
                        if(d1Old==Direction.UP && d1==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "GDownToLeft");
                        }
                        else if(d1Old==Direction.UP && d1==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "GDownToRight");
                        }
                        else if(d1Old==Direction.DOWN && d1==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "GTopToLeft");
                        }
                        else if(d1Old==Direction.DOWN && d1==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "GTopToRight");
                        }else if(d1==Direction.UP && d1Old==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "GTopToRight");
                        }
                        else if(d1==Direction.UP && d1Old==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "GTopToLeft");
                        }
                        else if(d1==Direction.DOWN && d1Old==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "GDownToRight");
                        }
                        else if(d1==Direction.DOWN && d1Old==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "GDownToLeft");
                        }
                        else if(d1Old==Direction.UP && d1==Direction.UP || d1Old==Direction.DOWN && d1==Direction.DOWN){
                            game.ColorToField(p1.y, p1.x, "GVertical");
                        }
                        else if(d1Old==Direction.LEFT && d1==Direction.LEFT || d1Old==Direction.RIGHT && d1==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "GHorizontal");
                        }
                    }else if(Player1Color==Color.CYAN){
                        
                        if(d1Old==Direction.UP && d1==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "CDownToLeft");
                        }
                        else if(d1Old==Direction.UP && d1==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "CDownToRight");
                        }
                        else if(d1Old==Direction.DOWN && d1==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "CTopToLeft");
                        }
                        else if(d1Old==Direction.DOWN && d1==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "CTopToRight");
                        }else if(d1==Direction.UP && d1Old==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "CTopToRight");
                        }
                        else if(d1==Direction.UP && d1Old==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "CTopToLeft");
                        }
                        else if(d1==Direction.DOWN && d1Old==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "CDownToRight");
                        }
                        else if(d1==Direction.DOWN && d1Old==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "CDownToLeft");
                        }
                        else if(d1Old==Direction.UP && d1==Direction.UP || d1Old==Direction.DOWN && d1==Direction.DOWN){
                            game.ColorToField(p1.y, p1.x, "CVertical");
                        }
                        else if(d1Old==Direction.LEFT && d1==Direction.LEFT || d1Old==Direction.RIGHT && d1==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "CHorizontal");
                        }
                    }else if(Player1Color==Color.MAGENTA){
                        
                        if(d1Old==Direction.UP && d1==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "MDownToLeft");
                        }
                        else if(d1Old==Direction.UP && d1==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "MDownToRight");
                        }
                        else if(d1Old==Direction.DOWN && d1==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "MTopToLeft");
                        }
                        else if(d1Old==Direction.DOWN && d1==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "MTopToRight");
                        }else if(d1==Direction.UP && d1Old==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "MTopToRight");
                        }
                        else if(d1==Direction.UP && d1Old==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "MTopToLeft");
                        }
                        else if(d1==Direction.DOWN && d1Old==Direction.LEFT){
                            game.ColorToField(p1.y, p1.x, "MDownToRight");
                        }
                        else if(d1==Direction.DOWN && d1Old==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "MDownToLeft");
                        }
                        else if(d1Old==Direction.UP && d1==Direction.UP || d1Old==Direction.DOWN && d1==Direction.DOWN){
                            game.ColorToField(p1.y, p1.x, "MVertical");
                        }
                        else if(d1Old==Direction.LEFT && d1==Direction.LEFT || d1Old==Direction.RIGHT && d1==Direction.RIGHT){
                            game.ColorToField(p1.y, p1.x, "MHorizontal");
                        }
                    }
                }
                if(d2Old!=null){
                    if(Player2Color==Color.RED){
                        
                        if(d2Old==Direction.UP && d2==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "RDownToLeft");
                        }
                        else if(d2Old==Direction.UP && d2==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "RDownToRight");
                        }
                        else if(d2Old==Direction.DOWN && d2==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "RTopToLeft");
                        }
                        else if(d2Old==Direction.DOWN && d2==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "RTopToRight");
                        }else if(d2==Direction.UP && d2Old==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "RTopToRight");
                        }
                        else if(d2==Direction.UP && d2Old==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "RTopToLeft");
                        }
                        else if(d2==Direction.DOWN && d2Old==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "RDownToRight");
                        }
                        else if(d2==Direction.DOWN && d2Old==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "RDownToLeft");
                        }
                        else if(d2Old==Direction.UP && d2==Direction.UP || d2Old==Direction.DOWN && d2==Direction.DOWN){
                            game.ColorToField(p2.y, p2.x, "RVertical");
                        }
                        else if(d2Old==Direction.LEFT && d2==Direction.LEFT || d2Old==Direction.RIGHT && d2==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "RHorizontal");
                        }
                    }else if(Player2Color==Color.GREEN){
                        
                        if(d2Old==Direction.UP && d2==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "GDownToLeft");
                        }
                        else if(d2Old==Direction.UP && d2==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "GDownToRight");
                        }
                        else if(d2Old==Direction.DOWN && d2==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "GTopToLeft");
                        }
                        else if(d2Old==Direction.DOWN && d2==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "GTopToRight");
                        }else if(d2==Direction.UP && d2Old==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "GTopToRight");
                        }
                        else if(d2==Direction.UP && d2Old==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "GTopToLeft");
                        }
                        else if(d2==Direction.DOWN && d2Old==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "GDownToRight");
                        }
                        else if(d2==Direction.DOWN && d2Old==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "GDownToLeft");
                        }
                        else if(d2Old==Direction.UP && d2==Direction.UP || d2Old==Direction.DOWN && d2==Direction.DOWN){
                            game.ColorToField(p2.y, p2.x, "GVertical");
                        }
                        else if(d2Old==Direction.LEFT && d2==Direction.LEFT || d2Old==Direction.RIGHT && d2==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "GHorizontal");
                        }
                    }else if(Player2Color==Color.CYAN){
                        
                        if(d2Old==Direction.UP && d2==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "CDownToLeft");
                        }
                        else if(d2Old==Direction.UP && d2==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "CDownToRight");
                        }
                        else if(d2Old==Direction.DOWN && d2==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "CTopToLeft");
                        }
                        else if(d2Old==Direction.DOWN && d2==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "CTopToRight");
                        }else if(d2==Direction.UP && d2Old==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "CTopToRight");
                        }
                        else if(d2==Direction.UP && d2Old==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "CTopToLeft");
                        }
                        else if(d2==Direction.DOWN && d2Old==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "CDownToRight");
                        }
                        else if(d2==Direction.DOWN && d2Old==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "CDownToLeft");
                        }
                        else if(d2Old==Direction.UP && d2==Direction.UP || d2Old==Direction.DOWN && d2==Direction.DOWN){
                            game.ColorToField(p2.y, p2.x, "CVertical");
                        }
                        else if(d2Old==Direction.LEFT && d2==Direction.LEFT || d2Old==Direction.RIGHT && d2==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "CHorizontal");
                        }
                    }else if(Player2Color==Color.MAGENTA){
                        
                        if(d2Old==Direction.UP && d2==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "MDownToLeft");
                        }
                        else if(d2Old==Direction.UP && d2==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "MDownToRight");
                        }
                        else if(d2Old==Direction.DOWN && d2==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "MTopToLeft");
                        }
                        else if(d2Old==Direction.DOWN && d2==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "MTopToRight");
                        }else if(d2==Direction.UP && d2Old==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "MTopToRight");
                        }
                        else if(d2==Direction.UP && d2Old==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "MTopToLeft");
                        }
                        else if(d2==Direction.DOWN && d2Old==Direction.LEFT){
                            game.ColorToField(p2.y, p2.x, "MDownToRight");
                        }
                        else if(d2==Direction.DOWN && d2Old==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "MDownToLeft");
                        }
                        else if(d2Old==Direction.UP && d2==Direction.UP || d2Old==Direction.DOWN && d2==Direction.DOWN){
                            game.ColorToField(p2.y, p2.x, "MVertical");
                        }
                        else if(d2Old==Direction.LEFT && d2==Direction.LEFT || d2Old==Direction.RIGHT && d2==Direction.RIGHT){
                            game.ColorToField(p2.y, p2.x, "MHorizontal");
                        }
                    }
                }
                d1Old=d1;
                d2Old=d2;
                
                game.step1(d1);
                game.step2(d2);
                board.refresh();
                 

            }catch(Exception e){
                System.out.println("Something went wrong!");
            }
            
            
        }
     }; 
}
