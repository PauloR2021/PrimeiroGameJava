import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    ArrayList<FallingObject> objects = new ArrayList<>(); // Criando um Array para definir as posições que os Objetos vão cair na tela
    int spawnTimer = 0;

    ArrayList<EnemyFalling> enemy = new ArrayList<>(); // Criando um Array para Definir as posições que os Inimigos vão cair na tela 
    int spawnEnemyTimer = 0;
    
    
    final int tileSize = 48; //48x48 pixels por Tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    Thread gameThread;

    Player player = new Player(this);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(this);
        this.setFocusable(true);
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run(){
        double drawInterval = 1000000000 / 60; // 60 FPS
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){
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

        // Gera novos Objetos a cada X frames
        spawnTimer++;
        if(spawnTimer > 60){//A cada 1 Segundo 
            objects.add(new FallingObject(this));
            spawnTimer = 0;
        }

        // Gera novos Inimigos
        spawnEnemyTimer++;
        if(spawnEnemyTimer  > 80){
            enemy.add(new EnemyFalling(this));
            spawnEnemyTimer  = 0;
        }


        // Ataualiza e remove objetos fora da Tela
        Iterator<FallingObject> it = objects.iterator();
        while(it.hasNext()){
            FallingObject obj = it.next();
            obj.update();
            if(obj.isOutOfScreen()){
                it.remove();
            }
        }

        // Atualiza e  remove os Inimigos Fora da Tela

        Iterator<EnemyFalling> ite = enemy.iterator();
        while (ite.hasNext()) {
            EnemyFalling ene = ite.next();
            ene.update();
            if(ene.isOutOfScreen()){
                ite.remove();
            }   
        }


        player.update();
    }

    public void paintComponent(Graphics g){

        // Desenha o jogador
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);


        // Desenha objeto Caindo
        for (FallingObject obj : objects){
            obj.draw(g2);
        }

        // Desenha Inimigo
        for( EnemyFalling ene : enemy ){
            ene.draw(g2);
        }
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
