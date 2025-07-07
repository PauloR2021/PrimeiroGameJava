import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

    int x,y, speed;
    boolean up, down, left,right;
    GamePanel gp;

    public Player(GamePanel gp){
        this.gp = gp;
        x = 100;
        y = 100;
        speed =4;
    }

    public void update(){
        System.out.println("Atualizando posição...");
        if(up) y -= speed;
        if(down) y += speed;
        if(left) x -= speed;
        if(right) x += speed;
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.white);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }

    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP) up = true;
        if(code == KeyEvent.VK_DOWN) down = true;
        if(code == KeyEvent.VK_LEFT) left = true;
        if(code == KeyEvent.VK_RIGHT) right = true;
    }

    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP) up = false;
        if(code == KeyEvent.VK_DOWN) down = false;
        if(code == KeyEvent.VK_LEFT) left = false;
        if(code == KeyEvent.VK_RIGHT) right = false;
    }
    
}
