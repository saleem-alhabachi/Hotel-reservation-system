package Hotel;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminControlPage extends JFrame {

    private JPanel contentPane;
    Admin admin = new Admin();

    public void hi(Admin a) {
        admin = a; 
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AdminControlPage frame = new AdminControlPage();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public AdminControlPage() {
        setTitle("Admin Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(44, 62, 80)); // Soft dark blue background

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Admin Control Panel", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitle.setForeground(new Color(241, 196, 15)); // Gold color
        lblTitle.setBounds(50, 20, 400, 30);
        contentPane.add(lblTitle);

        JButton btnManageRooms = createButton("Manage Rooms", 50, 80);
        btnManageRooms.addActionListener(e -> {
            if (admin.role.equals("General")) {
                ManageRoomsPage manageRoomPage = new ManageRoomsPage();
                manageRoomPage.setVisible(true);
                try {
                    manageRoomPage.fillTheTable();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "You don't have permission to manage rooms.", "Permission Denied", JOptionPane.WARNING_MESSAGE);
            }
        });
        contentPane.add(btnManageRooms);

        JButton btnManageCustomers = createButton("Manage Customers", 50, 130);
        btnManageCustomers.addActionListener(e -> {
            ManageCustomersPage customerPage = new ManageCustomersPage();
            customerPage.setVisible(true);
        });
        contentPane.add(btnManageCustomers);

        JButton btnManageRegisters = createButton("Manage Registers", 50, 180);
        btnManageRegisters.addActionListener(e -> {
            ManageRegistersPage manageRegistersPage = new ManageRegistersPage();
            manageRegistersPage.setVisible(true);
        });
        contentPane.add(btnManageRegisters);

        JButton btnManagePayments = createButton("Manage Payments", 50, 230);
        btnManagePayments.addActionListener(e -> {
            ManagePaymentPage paymentPage = new ManagePaymentPage();
            paymentPage.setVisible(true);
        });
        contentPane.add(btnManagePayments);

        JButton btnManageBookings = createButton("Manage Bookings", 50, 280);
        btnManageBookings.addActionListener(e -> {
            ManageBookingsPage bookingPage = new ManageBookingsPage();
            bookingPage.setVisible(true);
        });
        contentPane.add(btnManageBookings);
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 400, 40);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(39, 174, 96)); // Green button
        button.setFocusPainted(false);
        return button;
    }
}
