package Hotel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ReservationPage extends JFrame {

    private JPanel contentPane;
    private JTextField checkInField;
    private JTextField checkOutField;
    private JTable availableRoomsTable;
    
    RoomServices roomSer = new RoomServices();
    BookingServices bookingSer = new BookingServices();
    CustomerServices customerSer = new CustomerServices();
    
    Register register = new Register();
    
    public void hi(Register r) {
    	register = r; 
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ReservationPage frame = new ReservationPage();
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
    public ReservationPage() {
        setTitle("Reservation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Make a Reservation");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(280, 20, 300, 30);
        contentPane.add(lblTitle);

        JLabel lblRoomType = new JLabel("Room Type:");
        lblRoomType.setFont(new Font("Arial", Font.PLAIN, 16));
        lblRoomType.setBounds(50, 80, 100, 25);
        contentPane.add(lblRoomType);

        String[] roomTypes = {"Single", "Double", "Suite"};
        JComboBox<String> roomTypeComboBox = new JComboBox<>(roomTypes);
        roomTypeComboBox.setBounds(160, 80, 150, 25);
        contentPane.add(roomTypeComboBox);

        JLabel lblCheckIn = new JLabel("Check-In:");
        lblCheckIn.setFont(new Font("Arial", Font.PLAIN, 16));
        lblCheckIn.setBounds(50, 120, 100, 25);
        contentPane.add(lblCheckIn);

        checkInField = new JTextField();
        checkInField.setBounds(160, 120, 150, 25);
        contentPane.add(checkInField);

        JLabel lblCheckOut = new JLabel("Check-Out:");
        lblCheckOut.setFont(new Font("Arial", Font.PLAIN, 16));
        lblCheckOut.setBounds(50, 160, 100, 25);
        contentPane.add(lblCheckOut);

        checkOutField = new JTextField();
        checkOutField.setBounds(160, 160, 150, 25);
        contentPane.add(checkOutField);

        JButton btnCheckAvailability = new JButton("Check Availability");
        btnCheckAvailability.setBackground(new Color(0, 123, 255));
        btnCheckAvailability.setForeground(Color.WHITE);
        btnCheckAvailability.setFont(new Font("Arial", Font.PLAIN, 16));
        btnCheckAvailability.setBounds(160, 200, 180, 40);
        contentPane.add(btnCheckAvailability);

        JLabel lblAvailableRooms = new JLabel("Available Rooms:");
        lblAvailableRooms.setFont(new Font("Arial", Font.PLAIN, 16));
        lblAvailableRooms.setBounds(50, 260, 150, 25);
        contentPane.add(lblAvailableRooms);

        availableRoomsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(availableRoomsTable);
        scrollPane.setBounds(50, 300, 700, 120);
        contentPane.add(scrollPane);

        JButton btnConfirm = new JButton("Confirm Reservation");
        btnConfirm.setBackground(new Color(40, 167, 69));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFont(new Font("Arial", Font.PLAIN, 16));
        btnConfirm.setBounds(320, 430, 200, 40);
        contentPane.add(btnConfirm);

        // Action for "Check Availability"
        btnCheckAvailability.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 try {
            	        String roomType = roomTypeComboBox.getSelectedItem().toString();
            	        LocalDate checkIn = LocalDate.parse(checkInField.getText());
            	        LocalDate checkOut = LocalDate.parse(checkOutField.getText());
            	        ArrayList<Room> availableRooms = roomSer.getAvailableRooms(roomType, checkIn, checkOut);

            	        String[][] data = new String[availableRooms.size()][3];
            	        for (int i = 0; i < availableRooms.size(); i++) {
            	            Room room = availableRooms.get(i);
            	            data[i][0] = String.valueOf(room.getRoomNumber());
            	            data[i][1] = room.getRoomType();
            	            data[i][2] = String.valueOf(room.getPrice());
            	        }
            	        String[] columns = {"Room Number", "Room Type", "Price"};
            	        availableRoomsTable.setModel(new DefaultTableModel(data, columns));
            	    } catch (SQLException ex) {
            	        JOptionPane.showMessageDialog(null, "Error fetching available rooms: " + ex.getMessage());
            	    }
            }
        });

        // Action for "Confirm Reservation"
        btnConfirm.addActionListener(e -> {
        	Customer c = new Customer();
            c.fillCustomer(register);
            try {
                int selectedRow = availableRoomsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a room to proceed.");
                    return;
                }

                int roomNumber = Integer.parseInt(availableRoomsTable.getValueAt(selectedRow, 0).toString());
                Room room = roomSer.getRooms().stream()
                                      .filter(r -> r.getRoomNumber() == roomNumber)
                                      .findFirst().orElse(null);

                if (room == null) {
                    JOptionPane.showMessageDialog(null, "Selected room not found.");
                    return;
                }
                
                

                Booking booking = new Booking();
                booking.setRoomID(room.getRoomId());
                booking.setCustomerID(c.id); // Retrieve this dynamically
                booking.setCheckInDate(LocalDate.parse(checkInField.getText()));
                booking.setCheckOutDate(LocalDate.parse(checkOutField.getText()));
                booking.setPaidAmount(0); // Initialize with 0, update after payment
                PaymentPage1 payment = new PaymentPage1();
                payment.setVisible(true);
                payment.hii(c);

                bookingSer.addBooking(booking);

                JOptionPane.showMessageDialog(null, "Booking Confirmed!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error confirming booking: " + ex.getMessage());
            }
            
            
        });

    }
}
