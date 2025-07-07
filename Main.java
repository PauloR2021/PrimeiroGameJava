import javax.swing.JFrame; //Importando a Biblioteca JFrame

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("RPG 2D Simples");

        GamePanel gamePanel = new GamePanel();
        window.setContentPane(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.requestFocusInWindow();
        gamePanel.startGameThread();

    }
}