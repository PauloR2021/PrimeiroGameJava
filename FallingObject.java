import java.awt.*;
import java.util.Random;

//Classe criada para criar objetos e queda dos objetos na tela

public class FallingObject {
    //Definindo os parametros do Objeto que vai cair na Tela
    int x,y;
    int speed = 3;
    int size = 30;
   
    GamePanel gp;

    public FallingObject(GamePanel gp){
        this.gp = gp;
        Random rand = new Random();
        x = rand.nextInt(gp.screenWidth - size); /// Posição horizontal aleatória
        y = 0; //Começa no Topo da Tela
    }

    public void update(){
        y += speed;
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.decode("#FFD700")); //Colocando cor no Objeto que vai cair na tela/ Cor: Dourado
        g2.fillOval(x,y,size,size); //Definindo o formatado do Objeto em Circulo
    }

    public boolean isOutOfScreen(){
        return y > gp.screenHeight; //Método para quando o Objeto estiver fora da tela
    }
}
