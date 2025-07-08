import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

    int x,y, speed;
    boolean up, down, left,right;
    boolean A,S,D,W;
    int lives = 3 ;
    GamePanel gp;

    public Player(GamePanel gp){
        this.gp = gp;
        x = 100;
        y = 500;
        speed = 10;
    }

    public void update(){
        System.out.println("Atualizando posição...");
        
        //Cima
        /*/
        if(up && y - speed >=0){
            y -= speed; 
        }else if(W && y - speed >= 0){
            y -= speed;
        }

        //Baixo
        if(down && y + speed + gp.tileSize <= gp.screenHeight){
            y += speed; 
        }else if( S && y + speed + gp.tileSize <= gp.screenHeight){
            y += speed;
        }*/

        // Esquerda
        if(left && x - speed >= 0){
            x -= speed;
        }else if(A && x - speed >= 0){
            x -= speed;
        }

        // Direita
        if(right && x + speed + gp.tileSize <= gp.screenWidth){
            x += speed;
        }else if(D && x + speed + gp.tileSize <= gp.screenWidth){
            x += speed;
        }
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.white);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }

    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();

        //if(code == KeyEvent.VK_UP) up = true;
        //if(code == KeyEvent.VK_DOWN) down = true;
        if(code == KeyEvent.VK_LEFT) left = true;
        if(code == KeyEvent.VK_RIGHT) right = true;

        //if(code == KeyEvent.VK_W) W = true; 
        //if(code == KeyEvent.VK_S) S = true;
        if(code == KeyEvent.VK_D) D = true;
        if(code == KeyEvent.VK_A) A = true;
    }

    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();

        //if(code == KeyEvent.VK_UP) up = false;
        //if(code == KeyEvent.VK_DOWN) down = false;
        if(code == KeyEvent.VK_LEFT) left = false;
        if(code == KeyEvent.VK_RIGHT) right = false;

        //if(code == KeyEvent.VK_W) W = false;
        //if(code == KeyEvent.VK_S) S = false;
        if(code == KeyEvent.VK_D) D = false;
        if(code == KeyEvent.VK_A) A = false;
    }

    // Criando as Vidas do Player
    public int getLives(){
        return lives;
    }

    public void loseLife(){ //Função para perder vida
    lives --;
    }
    
}
