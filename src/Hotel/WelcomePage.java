package Hotel;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class WelcomePage extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    WelcomePage frame = new WelcomePage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public WelcomePage() {
        setTitle("Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 248, 255)); // Light blue background
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title label
        JLabel lblTitle = new JLabel("Welcome to Al-Tal Hotel!");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 28));
        lblTitle.setForeground(new Color(25, 25, 112)); // Dark blue text
        lblTitle.setBounds(10, 20, 564, 40);
        contentPane.add(lblTitle);

        // Subtitle label
        JLabel lblSubtitle = new JLabel("Experience luxury and comfort like never before.");
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("SansSerif", Font.ITALIC, 16));
        lblSubtitle.setForeground(new Color(70, 130, 180)); // Steel blue text
        lblSubtitle.setBounds(10, 70, 564, 30);
        contentPane.add(lblSubtitle);

        // Sign Up Button
        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.setFont(new Font("Arial", Font.PLAIN, 16));
        btnSignUp.setBackground(new Color(30, 144, 255)); // Dodger blue button
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setBounds(440, 280, 120, 40);
        btnSignUp.setBorderPainted(false);
        btnSignUp.setFocusPainted(false);
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SignIn newSignIn = new SignIn();
                newSignIn.setVisible(true);
            }
        });
        contentPane.add(btnSignUp);

        // Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.PLAIN, 16));
        btnLogin.setBackground(new Color(60, 179, 113)); // Medium sea green button
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBounds(40, 280, 120, 40);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login newLogin = new Login();
                newLogin.setVisible(true);
            }
        });
        contentPane.add(btnLogin);

        // Additional text for user engagement
        JLabel lblTagline = new JLabel("Your comfort is our priority.");
        lblTagline.setHorizontalAlignment(SwingConstants.CENTER);
        lblTagline.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblTagline.setForeground(new Color(25, 25, 112)); // Dark blue text
        lblTagline.setBounds(10, 120, 564, 20);
        contentPane.add(lblTagline);

        JLabel lblPrompt = new JLabel("Already a member? Login now or sign up to join us!");
        lblPrompt.setHorizontalAlignment(SwingConstants.CENTER);
        lblPrompt.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblPrompt.setForeground(new Color(70, 130, 180)); // Steel blue text
        lblPrompt.setBounds(10, 200, 564, 20);
        contentPane.add(lblPrompt);
    }
}
