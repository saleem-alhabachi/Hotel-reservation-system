package Hotel;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class CustomerPage extends JFrame {

    private JPanel contentPane;
    private JTable tableReservations;
    private JTable tableAvailableRooms;
    private JTextField txtCheckIn;
    private JTextField txtCheckOut;
    private JComboBox<String> cmbRoomType;
    private RoomServices roomSer = new RoomServices();
    Register register = new Register();
    private BookingServices bookingSer = new BookingServices();
    private CustomerServices customerSer = new CustomerServices();
    
    
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
                    CustomerPage frame = new CustomerPage();
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
    public CustomerPage() {
        setTitle("Customer Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 850, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 11, 814, 539);
        contentPane.add(tabbedPane);

        // My Reservations Tab
        JPanel panelReservations = new JPanel();
        tabbedPane.addTab("My Reservations", null, panelReservations, "click to show");
        panelReservations.setLayout(null);

        JScrollPane scrollPaneReservations = new JScrollPane();
        scrollPaneReservations.setBounds(10, 11, 789, 400);
        panelReservations.add(scrollPaneReservations);

        tableReservations = new JTable();
        tableReservations.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Reservation ID", "Room Number", "Check-in", "Check-out", "Total Amount", "Paid Amount", "Remaining Amount"
            }
        ));
        scrollPaneReservations.setViewportView(tableReservations);

        JButton btnPayRemaining = new JButton("Pay Remaining Amount");
        btnPayRemaining.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableReservations.getSelectedRow();
                if (selectedRow != -1) {
                    // Logic to pay the remaining amoun
                	
                	int roomId = Integer.parseInt(tableReservations.getValueAt(selectedRow, 1).toString());
                	Room room = new Room();
                	
                    try {
						room = roomSer.getRooms().stream()
						                      .filter(r -> r.getRoomId() == roomId)
						                      .findFirst().get();
					} catch (SQLException t) {
						// TODO Auto-generated catch block
						t.printStackTrace();
					}
                    
                	Customer c = new Customer();
                    c.fillCustomer(register);
                	int bookingId = Integer.parseInt(tableReservations.getValueAt(selectedRow, 0).toString());
                	
                    Booking booking = new Booking();
					try {
						booking = bookingSer.getAllBookings().stream()
						                      .filter(b -> b.getBookingID() == bookingId)
						                      .findFirst().orElse(null);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

                    if (booking == null) {
                        JOptionPane.showMessageDialog(null, "Selected room not found.");
                        return;
                    }
                    
                    PamentPage2 payment = new PamentPage2();
                    payment.setVisible(true);
                    payment.hii(c);
                    payment.hii(booking);
                    payment.hii(room);
                    
                    JOptionPane.showMessageDialog(contentPane, "Payment functionality to be implemented.");
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Please select a reservation.");
                }
                
            }
        });
        btnPayRemaining.setBounds(10, 422, 200, 23);
        panelReservations.add(btnPayRemaining);
        
        
        JButton btnCancel = new JButton("Cancel Reservation");
        
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableReservations.getSelectedRow();
                if (selectedRow != -1) {
                	int bookingID = (int) tableReservations.getValueAt(selectedRow, 0); // Assuming BookingID is in the first column
                    int roomNumber= (int) tableReservations.getValueAt(selectedRow, 1);
                    int roomId=10000;
					try {
						roomId = roomSer.getRoomId(roomNumber);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    try {
						bookingSer.deleteBooking(bookingID, roomId);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    try {
						loadReservations();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    JOptionPane.showMessageDialog(contentPane, "reservation deleted successfully!");
             
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Please select a reservation.");
                }
                
            }
        });
        
        btnCancel.setBounds(10,450,200,23);
        panelReservations.add(btnCancel);
        

        JButton btnRefreshReservations = new JButton("Refresh");
        btnRefreshReservations.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic to refresh reservations table
            	try {
                    loadReservations();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(contentPane, "Refreshing reservations.");
            }
        });
        btnRefreshReservations.setBounds(220, 422, 100, 23);
        panelReservations.add(btnRefreshReservations);

        // New Reservation Tab
        JPanel panelNewReservation = new JPanel();
        tabbedPane.addTab("New Reservation", null, panelNewReservation, null);
        panelNewReservation.setLayout(null);

        JLabel lblCheckIn = new JLabel("Check-in Date:");
        lblCheckIn.setBounds(10, 14, 100, 14);
        panelNewReservation.add(lblCheckIn);

        txtCheckIn = new JTextField();
        txtCheckIn.setBounds(120, 11, 100, 20);
        panelNewReservation.add(txtCheckIn);
        txtCheckIn.setColumns(10);

        JLabel lblCheckOut = new JLabel("Check-out Date:");
        lblCheckOut.setBounds(230, 14, 100, 14);
        panelNewReservation.add(lblCheckOut);

        txtCheckOut = new JTextField();
        txtCheckOut.setBounds(340, 11, 100, 20);
        panelNewReservation.add(txtCheckOut);
        txtCheckOut.setColumns(10);

        JLabel lblRoomType = new JLabel("Room Type:");
        lblRoomType.setBounds(450, 14, 80, 14);
        panelNewReservation.add(lblRoomType);

        cmbRoomType = new JComboBox<>();
        cmbRoomType.setModel(new DefaultComboBoxModel<>(new String[] {"Single", "Double", "Suite"}));
        cmbRoomType.setBounds(540, 11, 100, 20);
        panelNewReservation.add(cmbRoomType);

        JButton btnCheckAvailability = new JButton("Check Availability");
        btnCheckAvailability.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic to check available rooms
            	try {
                    loadAvailableRooms();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                JOptionPane.showMessageDialog(contentPane, "Refreshing available rooms.");
            }
        });
        btnCheckAvailability.setBounds(650, 10, 150, 23);
        panelNewReservation.add(btnCheckAvailability);

        JScrollPane scrollPaneAvailableRooms = new JScrollPane();
        scrollPaneAvailableRooms.setBounds(10, 50, 789, 400);
        panelNewReservation.add(scrollPaneAvailableRooms);

        tableAvailableRooms = new JTable();
        tableAvailableRooms.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Room Number", "Room Type", "Price per Night"//, "Status"
            }
        ));
        tableAvailableRooms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tableAvailableRooms.getSelectedRow();
                if (selectedRow != -1) {
                    // Logic to display room details
                	int roomNumber = Integer.parseInt(tableAvailableRooms.getValueAt(selectedRow, 0).toString());
                	Room room = new Room();
                    try {
						room = roomSer.getRooms().stream()
						                      .filter(r -> r.getRoomNumber() == roomNumber)
						                      .findFirst().get();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    RoomDetails page = new RoomDetails(room);
                    page.setVisible(true);
                    
                   
                    
                   
                }
            }
        });
        scrollPaneAvailableRooms.setViewportView(tableAvailableRooms);

        JButton btnConfirmReservation = new JButton("Confirm Reservation");
        btnConfirmReservation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableAvailableRooms.getSelectedRow();
                if (selectedRow != -1) {
                    // Logic to confirm reservation and proceed to payment
                	Customer c = new Customer();
                    c.fillCustomer(register);
                    try {
                        int roomNumber = Integer.parseInt(tableAvailableRooms.getValueAt(selectedRow, 0).toString());
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
                        booking.setCheckInDate(LocalDate.parse(txtCheckIn.getText()));
                        booking.setCheckOutDate(LocalDate.parse(txtCheckOut.getText()));
                        booking.setPaidAmount(0); // Initialize with 0, update after payment
                        PaymentPage payment = new PaymentPage();
                        payment.setVisible(true);
                        payment.hii(c);
                        payment.hii(room);
                        payment.hii(booking);

                        
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error confirming booking: " + ex.getMessage());
                    }
                    JOptionPane.showMessageDialog(contentPane, "Reservation confirmed. Proceed to payment.");
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Please select a room.");
                }
            }
        });
        btnConfirmReservation.setBounds(10, 460, 200, 23);
        panelNewReservation.add(btnConfirmReservation);
    }
    
    private void loadReservations() throws SQLException {
    		DefaultTableModel model = (DefaultTableModel) tableReservations.getModel();
        model.setRowCount(0);
        
        ArrayList<Booking> reservations = bookingSer.getBookings(register.id);
        for (Booking r : reservations) {
            Object[] rowData = {
                r.getBookingID(),
                bookingSer.getRoomId(r.getBookingID()),
                r.getCheckInDate(),
                r.getCheckOutDate(),
                r.getTotalPrice(),
                r.getPaidAmount(),
                r.getTotalPrice()-r.getPaidAmount()
            };
            model.addRow(rowData);}
    }
    
    private void loadAvailableRooms() throws SQLException {
    	DefaultTableModel model = (DefaultTableModel) tableAvailableRooms.getModel();
        model.setRowCount(0);
        
        String roomType = cmbRoomType.getSelectedItem().toString();
        LocalDate checkIn = LocalDate.parse(txtCheckIn.getText());
        LocalDate checkOut = LocalDate.parse(txtCheckOut.getText());
        
        ArrayList<Room> availableRooms = roomSer.getAvailableRooms(roomType, checkIn, checkOut);
        for(Room r : availableRooms) {
            Object[] rowData = {
                r.getRoomNumber(),
                r.getRoomType(),
                r.getPrice(),
                //r.isStatus()
            };
            model.addRow(rowData);
        }
    }
}
