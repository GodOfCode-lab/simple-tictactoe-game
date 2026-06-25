import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    private Player currentPlayer;
    private JButton btnStartGame;
    private JButton btnStatistics;
    private JButton btnTopScorers;
    private JButton btnExit;

    public MainMenuFrame(Player player) {
        this.currentPlayer = player;

        setTitle("Main Menu - " + player.getUsername());
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        add(new JLabel("Selamat datang, " + player.getUsername() + "!", SwingConstants.CENTER));

        btnStartGame   = new JButton("Mulai Game");
        btnStatistics  = new JButton("Statistik Saya");
        btnTopScorers  = new JButton("Top 5 Scorers");
        btnExit        = new JButton("Keluar");

        add(btnStartGame);
        add(btnStatistics);
        add(btnTopScorers);
        add(btnExit);

        btnStartGame.addActionListener(e -> {
            GameFrame gameFrame = new GameFrame(currentPlayer);
            gameFrame.setVisible(true);
            this.dispose();
        });

        btnStatistics.addActionListener(e -> {
            StatisticsFrame statsFrame = new StatisticsFrame(currentPlayer);
            statsFrame.setVisible(true);
        });

        btnTopScorers.addActionListener(e -> {
            TopScorersFrame topFrame = new TopScorersFrame();
            topFrame.setVisible(true);
        });

        btnExit.addActionListener(e -> System.exit(0));
    }
}
