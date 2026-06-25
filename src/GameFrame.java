import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private Player currentPlayer;
    private PlayerService playerService;
    private GameLogic gameLogic;
    private JButton[] buttons;
    private JLabel lblStatus;
    private boolean gameOver;

    public GameFrame(Player player) {
        this.currentPlayer = player;
        this.playerService = new PlayerService();
        this.gameLogic     = new GameLogic();
        this.gameOver      = false;

        setTitle("Tic-Tac-Toe - " + player.getUsername());
        setSize(400, 480);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Status label
        lblStatus = new JLabel("Giliran kamu (X)", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblStatus, BorderLayout.NORTH);

        // Board panel 3x3
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 36));
            final int index = i;
            buttons[i].addActionListener(e -> handlePlayerMove(index));
            boardPanel.add(buttons[i]);
        }
        add(boardPanel, BorderLayout.CENTER);

        // Tombol kembali ke menu
        JButton btnBack = new JButton("Kembali ke Menu");
        btnBack.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Yakin mau keluar dari game?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
                menuFrame.setVisible(true);
                this.dispose();
            }
        });
        add(btnBack, BorderLayout.SOUTH);
    }

    private void handlePlayerMove(int index) {
        if (gameOver) return;

        // Coba buat gerakan player
        boolean moved = gameLogic.makeMove(index, 'X');
        if (!moved) {
            JOptionPane.showMessageDialog(this, "Cell sudah terisi, pilih yang lain!");
            return;
        }

        // Update tampilan button
        buttons[index].setText("X");
        buttons[index].setForeground(Color.BLUE);

        // Cek player menang
        if (gameLogic.checkWinner('X')) {
            finishGame("WIN");
            return;
        }

        // Cek draw setelah gerakan player
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        // Giliran komputer
        lblStatus.setText("Komputer sedang berpikir...");
        int compIndex = gameLogic.computerMove();
        if (compIndex != -1) {
            buttons[compIndex].setText("O");
            buttons[compIndex].setForeground(Color.RED);
        }

        // Cek komputer menang
        if (gameLogic.checkWinner('O')) {
            finishGame("LOSE");
            return;
        }

        // Cek draw setelah gerakan komputer
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        lblStatus.setText("Giliran kamu (X)");
    }

    private void finishGame(String result) {
        gameOver = true;

        // Nonaktifkan semua tombol
        for (JButton btn : buttons) btn.setEnabled(false);

        // Update statistik di database
        playerService.updateStatistics(currentPlayer, result);

        String pesan;
        if (result.equals("WIN"))       pesan = "Selamat, kamu MENANG! +10 poin";
        else if (result.equals("LOSE")) pesan = "Kamu KALAH. Tetap semangat!";
        else                            pesan = "SERI! +3 poin";

        lblStatus.setText(pesan);
        JOptionPane.showMessageDialog(this, pesan, "Hasil Game", JOptionPane.INFORMATION_MESSAGE);

        // Refresh data player dari DB sebelum balik ke menu
        Player refreshed = playerService.getPlayerById(currentPlayer.getId());
        if (refreshed != null) currentPlayer = refreshed;

        MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
        menuFrame.setVisible(true);
        this.dispose();
    }
}
