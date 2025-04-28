package snakegame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener{
    private Image apple;
    private Image dot;
    private Image head;
    
    private final int All_Dots = 900;
    private final int Dot_Size = 10;
    private final int Random_Position =29;
    
    private int apple_x;
    private int apple_y;
    
    private final int x[] = new int[All_Dots];
    private final int y[] = new int[All_Dots];
    
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private boolean ingame = true;
    
    private int dots;
    private  Timer timer;
    
    Board(){
        
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(500, 600));
        setFocusable(true);
        
        loadImages();
        initialgame();
    }
    
    public void loadImages(){
        ImageIcon i1 =new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
        apple =i1.getImage();
        
        ImageIcon i2 =new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
        dot =i2.getImage();
        
        ImageIcon i3 =new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
        head =i3.getImage();
    }
        
    public void initialgame(){
        dots = 3;
        
        for(int i=0; i<dots;i++){
            y[i] = 50;
            x[i] = 50 - i * Dot_Size;
        }
        
        locateApple();
        
        Timer timer = new Timer(140,this);
        timer.start();
        
    }
    
    public void locateApple(){
        int r = (int)(Math.random() * Random_Position);
        apple_x = r * Dot_Size;
        
        r = (int)(Math.random() * Random_Position);
        apple_y = r * Dot_Size;
    }
        
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
            
    }
    
    public void draw(Graphics g){
        
        if(ingame){
        g.drawImage(apple, apple_x, apple_y, this);
       
        for(int i =0; i < dots; i++){
            if(i == 0){
                g.drawImage(head, x[i], y[i], this);
            }else{
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        
        Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameover(g);
        }
    }
    public void gameover(Graphics g){
        String msg = "Game Over!";
        Font font = new Font("SAN_SErif", Font.BOLD, 15);
        FontMetrics metrices = getFontMetrics(font);
        g.setColor(Color.white);
        g.drawString(msg, ((500 - metrices.stringWidth(msg)) / 2), 600 / 2);
    }
    
    public void move(){
        for(int i = dots; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        
        if(leftDirection){
            x[0] = x[0] - Dot_Size;
        }
        
        if(rightDirection){
            x[0] = x[0] + Dot_Size;
            
        }
        
        if(upDirection){
            y[0] = y[0] - Dot_Size; 
        }
        
        if(downDirection){
            y[0] = y[0] + Dot_Size;
        }

    }
    
    public void checkApple(){
        if((x[0] == apple_x) && (y[0] == apple_y)){
            dots++;
            locateApple();
        }
    }
    
    public void checkCollision(){
        for(int i =dots; i > 0; i--){
            if((i > 4) && (x[0] == x[i]) && (y[0] == y[i])){
                ingame = false;
            }
            
            if(y[0] >= 600){
                ingame = false;
            }
            if(x[0] >= 500){
                ingame = false;
            }
            if(y[0] < 0){
                ingame = false;
            }
            if(x[0] < 0){
                ingame = false;
            }
            if(!ingame){
                timer.stop();
            }
        }
    }
    public void actionPerformed(ActionEvent ae){
        if(ingame){
            checkApple();
            move();repaint();
            checkCollision();
        }
        
    }
    
    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            
            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;          
            }
            
            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;          
            }
            
            if(key == KeyEvent.VK_UP && (!downDirection)){
                upDirection = true;
                leftDirection = false;
                rightDirection = false;          
            }
            
            if(key == KeyEvent.VK_DOWN && (!upDirection)){
                downDirection = true;
                leftDirection = false;
                rightDirection = false;          
            }
        }
    }
    
    public static void main(String[] args) {
        
    }
}