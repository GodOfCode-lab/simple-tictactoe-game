import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TopScorersFrame extends JFrame {
    private JTable table;
    private PlayerService playerService;

    public TopScorersFrame() {
        playerService = new PlayerService();

        setTitle("Top 5 Scorers");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = {"Rank", "Username", "Menang", "Kalah", "Seri", "Score"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        ArrayList<Player> topPlayers = playerService.getTopFiveScorers();
        for (int i = 0; i < topPlayers.size(); i++) {
            Player p = topPlayers.get(i);
            model.addRow(new Object[]{
                    i + 1,
                    p.getUsername(),
                    p.getWins(),
                    p.getLosses(),
                    p.getDraws(),
                    p.getScore()
            });
        }

        table = new JTable(model);
        table.setEnabled(false); // read-only
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnClose = new JButton("Tutup");
        btnClose.addActionListener(e -> this.dispose());
        add(btnClose, BorderLayout.SOUTH);
    }
}
