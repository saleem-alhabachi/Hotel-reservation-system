package Hotel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

    private JPanel contentPane;
    private JTextField txtUsername;
    private JPasswordField passwordField;
    private JButton btnLogin;

    RegisterServices registerSer = new RegisterServices();
    AdminServices adminSer = new AdminServices();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Login frame = new Login();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Login() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(245, 245, 245)); // Light gray background
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title
        JLabel lblTitle = new JLabel("Login to Al-Tal Hotel");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(100, 20, 250, 30);
        contentPane.add(lblTitle);

        // Subtitle
        JLabel lblSubtitle = new JLabel("Enter your credentials to continue");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitle.setBounds(100, 50, 250, 20);
        contentPane.add(lblSubtitle);

        // Username Label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsername.setBounds(100, 100, 80, 20);
        contentPane.add(lblUsername);

        // Username Field
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsername.setBounds(190, 100, 160, 25);
        contentPane.add(txtUsername);

        // Password Label
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPassword.setBounds(100, 150, 80, 20);
        contentPane.add(lblPassword);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(190, 150, 160, 25);
        contentPane.add(passwordField);

        // Login Button
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(30, 144, 255)); // DodgerBlue color
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBounds(170, 220, 100, 30);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        contentPane.add(btnLogin);
    }

    private void handleLogin() {
        ArrayList<Register> registers = new ArrayList<>();
        ArrayList<Admin> admins = new ArrayList<>();

        try {
            admins = adminSer.getAdmins();
            registers = registerSer.getRegisters();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        String username = txtUsername.getText();
        char[] password_ = passwordField.getPassword();
        String password = new String(password_);

        boolean customer = false;
        boolean admin = false;

        for (Register r : registers) {
            if (r.username.equals(username) && r.password.equals(password)) customer = true;
        }
        for (Admin a : admins) {
            if (a.username.equals(username) && a.password.equals(password)) admin = true;
        }

        if (customer) {
            Register reg = registers.stream()
                    .filter(r -> username.equals(username) && password.equals(r.password))
                    .findFirst().get();
            CustomerPage resPage = new CustomerPage();
            resPage.setVisible(true);
            resPage.hi(reg);
            dispose(); 
        } else if (admin) {
            Admin adm = admins.stream()
                    .filter(a -> username.equals(username) && password.equals(a.password))
                    .findFirst().get();
            AdminControlPage adminPage = new AdminControlPage();
            adminPage.setVisible(true);
            adminPage.hi(adm);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username or password is incorrect", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
