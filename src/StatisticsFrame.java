import javax.swing.*;
import java.awt.*;

public class StatisticsFrame extends JFrame {
    private PlayerService playerService;

    public StatisticsFrame(Player player) {
        playerService = new PlayerService();

        setTitle("Statistik - " + player.getUsername());
        setSize(300, 280);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        Player fresh = playerService.getPlayerById(player.getId());
        if (fresh == null) fresh = player; // fallback

        add(new JLabel("Username:", SwingConstants.RIGHT));
        add(new JLabel(fresh.getUsername()));

        add(new JLabel("Menang:", SwingConstants.RIGHT));
        add(new JLabel(String.valueOf(fresh.getWins())));

        add(new JLabel("Kalah:", SwingConstants.RIGHT));
        add(new JLabel(String.valueOf(fresh.getLosses())));

        add(new JLabel("Seri:", SwingConstants.RIGHT));
        add(new JLabel(String.valueOf(fresh.getDraws())));

        add(new JLabel("Total Game:", SwingConstants.RIGHT));
        int total = fresh.getWins() + fresh.getLosses() + fresh.getDraws();
        add(new JLabel(String.valueOf(total)));

        add(new JLabel("Score:", SwingConstants.RIGHT));
        add(new JLabel(String.valueOf(fresh.getScore())));

        JButton btnClose = new JButton("Tutup");
        add(new JLabel());
        add(btnClose);
        btnClose.addActionListener(e -> this.dispose());
    }
}
