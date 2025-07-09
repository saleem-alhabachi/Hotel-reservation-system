package Hotel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class PaymentPage1 extends JFrame {
	public PaymentPage1() {
	}
	
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
    }

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Payment Page");
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                String nationality = (String) nationalityComboBox.getSelectedItem();
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
						
						int bookingId = paymentSer.getBookingId(customer.getId());
						int paymetnAmount = Integer.parseInt(amountField.getText());
						String paymentDateString = dateField.getText();
						
						// Parse the payment date
			            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			            java.util.Date parsedDate = sdf.parse(paymentDateString);
			            java.sql.Date paymentDate = new java.sql.Date(parsedDate.getTime());
			            
			            customerSer.addCustomer(customer);
			            bookingSer.addBooking(booking);
			            
						paymentSer.processPayment(bookingId, paymetnAmount, paymentDate, paymentMethod);
						
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

        cancelButton.addActionListener(e -> frame.dispose());

        // Make the frame visible
        frame.setVisible(true);
    }
    
}
