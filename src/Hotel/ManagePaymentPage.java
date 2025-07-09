package Hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class ManagePaymentPage extends JFrame {

    private JTextField bookingIDField;
    private JTextField paymentAmountField;
    private JTextField paymentDateField;
    private JComboBox<String> paymentMethodComboBox;
    private JButton processPaymentButton;
    private JButton viewPaymentsButton;
    private JLabel statusLabel;
    private JTable paymentTable;
    private DefaultTableModel tableModel;

    private PaymentServices paymentServices;

    public ManagePaymentPage() {
        // Initialize the payment services object
        paymentServices = new PaymentServices();

        // Frame settings
        setTitle("Manage Payments");
        setLayout(new BorderLayout(15, 15));
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // UI components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 2), "Payment Information", 0, 0, new Font("Arial", Font.BOLD, 16), Color.BLUE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        bookingIDField = new JTextField(15);
        paymentAmountField = new JTextField(15);
        paymentDateField = new JTextField(15); // Input for payment date in yyyy-MM-dd format
        paymentMethodComboBox = new JComboBox<>(new String[]{"Credit Card", "Cash"}); // Combobox for Payment Method
        processPaymentButton = new JButton("Process Payment");
        viewPaymentsButton = new JButton("View Payments");
        statusLabel = new JLabel("", SwingConstants.CENTER);

        statusLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        
        // Input panel layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Booking ID:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(bookingIDField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Payment Amount:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(paymentAmountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Payment Date (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(paymentDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Payment Method:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(paymentMethodComboBox, gbc);

        // Payment table settings
        tableModel = new DefaultTableModel(new String[]{"Payment ID", "Booking ID", "Amount", "Payment Date", "Payment Method"}, 0);
        paymentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(paymentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Payment Records", 0, 0, new Font("Arial", Font.BOLD, 14)));

        // Main panel with buttons and status label
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(2, 1, 5, 5));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.add(processPaymentButton);
        buttonPanel.add(viewPaymentsButton);

        actionPanel.add(buttonPanel);
        actionPanel.add(statusLabel);

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Set up button action listeners
        processPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayment();
            }
        });

        viewPaymentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPayments();
            }
        });
    }

    // Method to process payment
    private void processPayment() {
        try {
            // Validate inputs
            if (bookingIDField.getText().isEmpty() || paymentAmountField.getText().isEmpty() || paymentDateField.getText().isEmpty()) {
                statusLabel.setText("Please fill in all fields.");
                statusLabel.setForeground(Color.RED);
                return;
            }

            int bookingID = Integer.parseInt(bookingIDField.getText());
            double paymentAmount = Double.parseDouble(paymentAmountField.getText());

            if (paymentAmount <= 0) {
                statusLabel.setText("Amount must be greater than zero.");
                statusLabel.setForeground(Color.RED);
                return;
            }

            String paymentDateString = paymentDateField.getText();
            String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();

            // Parse the payment date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(paymentDateString);
            java.sql.Date paymentDate = new java.sql.Date(parsedDate.getTime());

            // Process payment and update status
            paymentServices.processPayment(bookingID, paymentAmount, paymentDate, paymentMethod);

            // Success message
            statusLabel.setText("Payment processed successfully!");
            statusLabel.setForeground(Color.GREEN);

            // Clear input fields after processing
            bookingIDField.setText("");
            paymentAmountField.setText("");
            paymentDateField.setText("");
            paymentMethodComboBox.setSelectedIndex(0);

            // Refresh the payment list
            refreshPaymentList(bookingID);

        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid input! Please enter valid numbers.");
            statusLabel.setForeground(Color.RED);
        } catch (SQLException ex) {
            statusLabel.setText("Error: " + ex.getMessage());
            statusLabel.setForeground(Color.RED);
        } catch (java.text.ParseException ex) {
            statusLabel.setText("Invalid date format! Please use yyyy-MM-dd.");
            statusLabel.setForeground(Color.RED);
        }
    }

    // Method to view payments for a specific booking
    private void viewPayments() {
        try {
            int bookingID = Integer.parseInt(bookingIDField.getText());
            refreshPaymentList(bookingID);
        } catch (NumberFormatException ex) {
            statusLabel.setText("Please enter a valid booking ID.");
            statusLabel.setForeground(Color.RED);
        }
    }

    // Method to refresh the payment list for a specific booking
    private void refreshPaymentList(int bookingID) {
        try {
            ResultSet rs = paymentServices.getPaymentsForBooking(bookingID);
            // Clear previous data
            tableModel.setRowCount(0);

            // Populate table with payment data
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("PaymentID"),
                        rs.getInt("BookingID"),
                        rs.getDouble("Amount"),
                        rs.getDate("PaymentDate"),
                        rs.getString("PaymentMethod")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error: Unable to fetch payment data.");
            statusLabel.setForeground(Color.RED);
        }
    }

    public static void main(String[] args) {
        // Display the Manage Payment page
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ManagePaymentPage().setVisible(true);
            }
        });
    }
}
