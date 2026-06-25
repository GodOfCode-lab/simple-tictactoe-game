import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private PlayerService playerService;

    public LoginFrame() {
        playerService = new PlayerService();

        setTitle("Login - Tic-Tac-Toe");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnLogin = new JButton("Login");
        add(new JLabel());
        add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Username dan password tidak boleh kosong!",
                            "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Player player = playerService.login(username, password);

                if (player != null) {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Login berhasil! Selamat datang, " + player.getUsername(),
                            "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    MainMenuFrame menuFrame = new MainMenuFrame(player);
                    menuFrame.setVisible(true);
                    LoginFrame.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Username atau password salah!",
                            "Login Gagal", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(false);
    }
}
