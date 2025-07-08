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
    final int maxScreenCol = 16; //Max de colunas
    final int maxScreenRow = 12; //Max de linhas 
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    boolean gameOver = false; //Para Definir gamer Over
    int score = 0; //Para Definir Pontuação

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


        // Atualiza e remove objetos fora da Tela
        Iterator<FallingObject> it = objects.iterator();
        while(it.hasNext()){
            FallingObject obj = it.next();
            obj.update();

            Rectangle objRect = new Rectangle(obj.x, obj.y, obj.size, obj.size);
            Rectangle playerRect = new Rectangle(player.x, player.y, tileSize, tileSize);

            if(objRect.intersects(playerRect)){
                score++;        // aumenta pontuação
                it.remove();    // remove o objeto da tela
                continue;
            }

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

        // Vida do Personagem e esquema para perder vida
        for (EnemyFalling ene : enemy){
            Rectangle enemyReact = new Rectangle(ene.x, ene.y, ene.size, ene.size);
            Rectangle playerReact = new Rectangle(player.x, player.y, tileSize,tileSize);


            // Quando o Inimigo Toca no Player
            if(enemyReact.intersects(playerReact)){
                player.loseLife(); // Perde a Vida
                ene.y = screenHeight + 100 ; // Remove o inimigo da tela
                break; //Evita perder várias vidas por multiplas colisões no mesmo frame
            }
        }

        // Verificando se o Personagem ainda tem Vida
        if(player.getLives() <= 0){
            gameOver = true;
            gameThread = null; //Para o Jogo
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

        // Desenha as Vidas do Personagem
        for(int i = 0; i < player.getLives(); i++){
            g2.setColor(Color.green);
            g2.fillRect(20 + (i *30), 20, 20 ,20 ); // posição X aumenta conforme a vida
        }

        // Mostrando Tela  de Gamer Over Quando o Personagem perde a Vida
        if(gameOver){

            String line1 = "GAME OVER";
            String line2 = "Pressione ENTER";
            String line3 = "Pontos: "+score;

            // Criando as Fontes para os textos
            Font font = new Font("Arial", Font.BOLD, 48);
            Font font2 = new Font("Arial",Font.PLAIN, 24);
            Font font3 = new Font("Arial",Font.PLAIN, 20);


            // Colocando a Cor para o texto de Game Over
            g2.setColor(Color.white);
            g2.setFont(font);
            
            // Centralizando o Texto Game Over
            FontMetrics fm1 = g2.getFontMetrics();
            int line1Width = fm1.stringWidth(line1);
            int x1 = (screenWidth - line1Width) / 2;
            int y1 = screenHeight / 2;
            g2.drawString(line1, x1, y1);

            // Sombra para linha 1
            g2.setColor(Color.blue);
            g2.drawString(line1, x1 + 2, y1 + 2);

            // Texto principal
            g2.setColor(Color.white);
            g2.drawString(line1, x1, y1);

            // Segunda linha (fonte menor, abaixo da primeira)
            g2.setFont(font2);
            FontMetrics fm2 = g2.getFontMetrics();
            int line2Width = fm2.stringWidth(line2);
            int x2 = (screenWidth - line2Width) / 2;
            int y2 = y1 + fm2.getHeight() + 10; // espaço entre linhas
            g2.drawString(line2, x2, y2);

            // Terceira Linha
            g2.setFont(font3);
            g2.setColor(Color.yellow);
            FontMetrics fm3 = g2.getFontMetrics();
            int line3Width = fm3.stringWidth(line3);
            int x3 = (screenWidth - line3Width) / 2;
            int y3 = y2 + fm2.getHeight() + 10; // 
            g2.drawString(line3, x3, y3);

            
        }


        // Mostra a Pontuação do Player
        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        String scoreText = "Pontuação: " + score;
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(scoreText);
        g2.drawString(scoreText, screenWidth - textWidth - 20, 60);


        g2.dispose();

    }

    public void restartGame(){
        // Limpa listas
        objects.clear();
        enemy.clear();

        // Reinicia Jogador
        player = new Player(this);

        score = 0; //Reseta a Pontuação

        // Reseta Game Over e Reinicia Thrend
        gameOver = false;
        startGameThread();
    }



    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){
        player.keyPressed(e);

        
        // Reinicia o jogo se estiver em Game Over e ENTER for pressionado
        if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
            restartGame();
        }
    }

    public void keyReleased(KeyEvent e){
        player.keyReleased(e);
    }
}
