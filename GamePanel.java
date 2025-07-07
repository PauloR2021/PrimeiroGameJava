import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    
    final int tileSize = 48; //48x48 pixels por Tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    Thread gamThread;

    Player player = new Player(this);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(this);
        this.setFocusable(true);
    }
    public void startGameThread(){
        gamThread = new Thread();
        gamThread.start();
    }

    public void run(){
        double drawInterval = 1000000000 / 60; // 60 FPS
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gamThread != null){
            update();
            repaint();

            try{
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if(remainingTime <0) remainingTime =0;

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
        
    }

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }

    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e){
        player.keyReleased(e);
    }
}
