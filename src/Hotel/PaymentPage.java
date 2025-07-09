package Hotel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;

public class PaymentPage extends JFrame {

	public static CustomerServices customerSer = new CustomerServices();
	public static Customer customer = new Customer();
	public static Room room = new Room();
	public static Booking booking = new Booking();
	public static BookingServices bookingSer = new BookingServices();
	public static PaymentServices paymentSer = new PaymentServices();
	public  void hii(Room r) {
		room = r;
	}
	public void hii (Customer c) {
		customer = c;
	}
	public void hii (Booking b) {
        booking = b;
        System.out.println(booking.getBookingID());
    }
	
	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PaymentPage frame = new PaymentPage();
                   
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	

    public PaymentPage() {
        setTitle("Payment Page");
        setBounds(100, 100, 850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        
        JFrame frame = new JFrame("Payment Page");
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        
     // Title
        JLabel titleLabel = new JLabel("Payment Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.getContentPane().add(titleLabel, BorderLayout.NORTH);

        // Panel for form inputs
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Nationality
        JLabel nationalityLabel = new JLabel("Nationality:");
        JComboBox<String> nationalityComboBox = new JComboBox<>(new String[]{"Select", "USA", "Canada", "UK", "India","TUR", "Other"});
        formPanel.add(nationalityLabel);
        formPanel.add(nationalityComboBox);

        // Passport Number
        JLabel passportLabel = new JLabel("Passport Number:");
        JTextField passportField = new JTextField();
        formPanel.add(passportLabel);
        formPanel.add(passportField);

        // Amount
        JLabel amountLabel = new JLabel("Amount ($):");
        JTextField amountField = new JTextField("100.00");
        formPanel.add(amountLabel);
        formPanel.add(amountField);

        // Payment Method
        JLabel paymentMethodLabel = new JLabel("Payment Method:");
        JPanel paymentMethodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton creditCardButton = new JRadioButton("Credit Card");
        JRadioButton debitCardButton = new JRadioButton("Debit Card");
        JRadioButton paypalButton = new JRadioButton("PayPal");
        JRadioButton bankTransferButton = new JRadioButton("Bank Transfer");
        ButtonGroup paymentMethodGroup = new ButtonGroup();
        paymentMethodGroup.add(creditCardButton);
        paymentMethodGroup.add(debitCardButton);
        paymentMethodGroup.add(paypalButton);
        paymentMethodGroup.add(bankTransferButton);
        paymentMethodPanel.add(creditCardButton);
        paymentMethodPanel.add(debitCardButton);
        paymentMethodPanel.add(paypalButton);
        paymentMethodPanel.add(bankTransferButton);
        formPanel.add(paymentMethodLabel);
        formPanel.add(paymentMethodPanel);
        
        JLabel dateLabel = new JLabel("Payment Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField();
        formPanel.add(dateLabel);
        formPanel.add(dateField);

        // Add form panel to frame
        frame.getContentPane().add(formPanel, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton confirmButton = new JButton("Confirm Payment");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // Add button panel to frame
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
     // Event Listeners
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nationality = nationalityComboBox.getSelectedItem().toString();
                String passportNumber = passportField.getText();
                String paymentMethod = "";

                if (creditCardButton.isSelected()) {
                    paymentMethod = "Credit Card";
                } else if (debitCardButton.isSelected()) {
                    paymentMethod = "Debit Card";
                } else if (paypalButton.isSelected()) {
                    paymentMethod = "PayPal";
                } else if (bankTransferButton.isSelected()) {
                    paymentMethod = "Bank Transfer";
                }

                // Validate inputs
                if (nationality.equals("Select") || passportNumber.isEmpty() || paymentMethod.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all the required fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Payment confirmed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // You can add your backend code here to process the payment.
                    customer.setNationalities(nationality);
                    customer.setPassport(passportNumber);
                    try {
						
						
						double paymentAmount = Double.parseDouble(amountField.getText());
						String paymentDateString = dateField.getText();
						
						// Parse the payment date
			            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			            java.util.Date parsedDate = sdf.parse(paymentDateString);
			            java.sql.Date paymentDate = new java.sql.Date(parsedDate.getTime());
			            
			            
			            
			          if(!customerSer.isCustomerExists(customer.getId())) {
			        	   customerSer.addCustomer(customer);
			           }
			         bookingSer.addBooking(booking);
			          int bookingId = bookingSer.getBookingId(room.getRoomId(),customer.getId());
					  paymentSer.processPayment(bookingId, paymentAmount, paymentDate, paymentMethod);
					  dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
                }
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
        		@Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window on the screen
        
        
        

        
    } 
}
