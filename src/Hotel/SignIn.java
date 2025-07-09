package Hotel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.util.ArrayList;

public class SignIn extends JFrame {

    private JTextField txtFirstName, txtLastName, txtBirthDate, txtEmail, txtPhoneNumber, txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbxGender;
    private JButton btnRegister;

    private RegisterServices registerServices = new RegisterServices();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                SignIn frame = new SignIn();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SignIn() {
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 500);

        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 245, 245));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(600, 60));
        JLabel lblTitle = new JLabel("Create Your Account", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        contentPane.add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        contentPane.add(formPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Make the text fields wider

        // First Name
        addFormRow(formPanel, gbc, "First Name", txtFirstName = new JTextField(20), 0);

        // Last Name
        addFormRow(formPanel, gbc, "Last Name", txtLastName = new JTextField(20), 1);

        // Birth Date
        addFormRow(formPanel, gbc, "Birth Date (yyyy-MM-dd)", txtBirthDate = new JTextField(20), 2);

        // Email
        addFormRow(formPanel, gbc, "Email", txtEmail = new JTextField(20), 3);

        // Phone Number
        addFormRow(formPanel, gbc, "Phone Number", txtPhoneNumber = new JTextField(20), 4);

        // Gender
        JLabel lblGender = new JLabel("Gender");
        lblGender.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(lblGender, gbc);

        cmbxGender = new JComboBox<>(new String[]{"Male", "Female"});
        cmbxGender.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        formPanel.add(cmbxGender, gbc);

        // Username
        addFormRow(formPanel, gbc, "Username", txtUsername = new JTextField(20), 6);

        // Password
        addFormRow(formPanel, gbc, "Password", txtPassword = new JPasswordField(20), 7);

        // Register Button
        btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegister.setBackground(new Color(46, 204, 113));
        btnRegister.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        formPanel.add(btnRegister, gbc);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);

        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(textField, gbc);
    }

    private void handleRegister() {
        try {
            String firstName = txtFirstName.getText().trim();
            String lastName = txtLastName.getText().trim();
            String birthDateStr = txtBirthDate.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhoneNumber.getText().trim();
            String gender = (String) cmbxGender.getSelectedItem();
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            // Validation checks
            if (firstName.isEmpty() || lastName.isEmpty() || birthDateStr.isEmpty() ||
                email.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (username.length() < 6) {
                JOptionPane.showMessageDialog(this, "Username must be at least 6 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length() < 8) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate and parse birth date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(birthDateStr);
            java.sql.Date birthDate = new java.sql.Date(parsedDate.getTime());

            // Check for existing username
            ArrayList<Register> registers = registerServices.getRegisters();
            for (Register reg : registers) {
                if (reg.getUsername().equals(username)) {
                    JOptionPane.showMessageDialog(this, "Username already exists. Please choose another one.");
                    return;
                }
            }

            // Create and save the new register object
            Register register = new Register();
            register.firstName = firstName;
            register.lastName = lastName;
            register.date = birthDate;
            register.email = email;
            register.phone = phone;
            register.gender = gender;
            register.username = username;
            register.password = password;
            register.age = register.calculateAge(birthDate);

            registerServices.addRegister(register);
            JOptionPane.showMessageDialog(this, "Registration successful!");
            dispose();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid birth date format. Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while saving your information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
