//Classe criada para criar inimigos caindo da Tela

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class EnemyFalling {
    
    int x, y;
    int speed = 6;
    int size = 40;

    GamePanel gp;

    public EnemyFalling(GamePanel gp){
        this.gp = gp;
        Random rand = new Random();
        x = rand.nextInt(gp.screenWidth - size);//Posicão Horizontal aletória
        y = 0; //Começa no Topo
    }

    public void update(){
        y += speed;
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.decode("#FF0000")); //Definindo a Cor do Inimigo
        g2.fillOval(x, y, size, size); //Defininfo o formato do Objetoi em Circulo
    }

    public boolean isOutOfScreen(){
        return y > gp.screenHeight; //Método para Quando o objeto estiver fora da tela
    }
}
