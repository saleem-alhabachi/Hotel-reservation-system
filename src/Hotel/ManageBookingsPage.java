package Hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ManageBookingsPage extends JFrame {

    private JTable bookingsTable;
    private JTextField customerIDField, roomIDField, checkInDateField, checkOutDateField, paidAmountField;
    private BookingServices bookingServices;

    public ManageBookingsPage() {
        setTitle("Manage Bookings");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bookingServices = new BookingServices();
        initializeUI();
        loadBookingsTable();
    }

    private void initializeUI() {
        // Labels
        JLabel lblCustomerID = new JLabel("Customer ID:");
        JLabel lblRoomID = new JLabel("Room ID:");
        JLabel lblCheckInDate = new JLabel("Check-In Date (YYYY-MM-DD):");
        JLabel lblCheckOutDate = new JLabel("Check-Out Date (YYYY-MM-DD):");
        JLabel lblPaidAmount = new JLabel("Paid Amount:");

        // Input Fields
        customerIDField = new JTextField();
        roomIDField = new JTextField();
        checkInDateField = new JTextField();
        checkOutDateField = new JTextField();
        paidAmountField = new JTextField();

        // Buttons
        JButton btnAdd = new JButton("Add Booking");
        JButton btnDelete = new JButton("Delete Booking");

        // Table
        bookingsTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(bookingsTable);

        // Layout setup
        setLayout(null);
        lblCustomerID.setBounds(20, 20, 200, 30);
        customerIDField.setBounds(220, 20, 150, 30);
        lblRoomID.setBounds(20, 60, 200, 30);
        roomIDField.setBounds(220, 60, 150, 30);
        lblCheckInDate.setBounds(20, 100, 200, 30);
        checkInDateField.setBounds(220, 100, 150, 30);
        lblCheckOutDate.setBounds(20, 140, 200, 30);
        checkOutDateField.setBounds(220, 140, 150, 30);
        lblPaidAmount.setBounds(20, 180, 200, 30);
        paidAmountField.setBounds(220, 180, 150, 30);

        btnAdd.setBounds(400, 20, 150, 30);
        btnDelete.setBounds(400, 60, 150, 30);
        tableScrollPane.setBounds(20, 250, 740, 300);

        // Add components to the frame
        add(lblCustomerID);
        add(customerIDField);
        add(lblRoomID);
        add(roomIDField);
        add(lblCheckInDate);
        add(checkInDateField);
        add(lblCheckOutDate);
        add(checkOutDateField);
        add(lblPaidAmount);
        add(paidAmountField);
        add(btnAdd);
        add(btnDelete);
        add(tableScrollPane);

        // Button Actions
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addBooking();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error adding booking: " + ex.getMessage());
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteBooking();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting booking: " + ex.getMessage());
                }
            }
        });
    }

    private void addBooking() throws SQLException {
        try {
            Booking booking = new Booking();
            booking.setRoomID(Integer.parseInt(roomIDField.getText()));
            booking.setCustomerID(Integer.parseInt(customerIDField.getText()));
            booking.setCheckInDate(LocalDate.parse(checkInDateField.getText())); // Ensure format: YYYY-MM-DD
            booking.setCheckOutDate(LocalDate.parse(checkOutDateField.getText())); // Ensure format: YYYY-MM-DD
            booking.setPaidAmount(Double.parseDouble(paidAmountField.getText()));

            bookingServices.addBooking(booking);

            // Refresh the table after adding
            loadBookingsTable();
            JOptionPane.showMessageDialog(this, "Booking added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format. Please check your data.");
        }
    }

    private void deleteBooking() throws SQLException {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow != -1) {
            int bookingID = (int) bookingsTable.getValueAt(selectedRow, 0); // Assuming BookingID is in the first column
            int roomId = (int) bookingsTable.getValueAt(selectedRow, 1);
            bookingServices.deleteBooking(bookingID, roomId);
            loadBookingsTable();
            JOptionPane.showMessageDialog(this, "Booking deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a booking to delete.");
        }
    }

    private void loadBookingsTable() {
        try {
            ArrayList<Booking> bookings = bookingServices.getAllBookings();
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.setColumnIdentifiers(new String[] { "Booking ID", "Room ID", "Customer ID", "Check-In Date", "Check-Out Date", "Paid Amount", "Total Price", "Number of Days", "Status" });
            for (Booking booking : bookings) {
                tableModel.addRow(new Object[] {
                    booking.getBookingID(),
                    booking.getRoomID(),
                    booking.getCustomerID(),
                    booking.getCheckInDate(),
                    booking.getCheckOutDate(),
                    booking.getPaidAmount(),
                    booking.getTotalPrice(),
                    booking.getNumberOfDays(),
                    booking.getStatus()
                });
            }
            bookingsTable.setModel(tableModel);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading bookings: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ManageBookingsPage().setVisible(true);
    }
}
