package Hotel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AddRoomPage extends JFrame {

	private JPanel contentPane;
	private JTextField txtRoomNumber;
	private JTextField txtRoomPrice;
	private JTextField txtRoomCapacity;
	RoomServices roomSer = new RoomServices();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddRoomPage frame = new AddRoomPage();
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
	public AddRoomPage() {
		setTitle("New Room");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 587, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRoomNumber = new JLabel("Room Number");
		lblRoomNumber.setBounds(33, 44, 88, 14);
		contentPane.add(lblRoomNumber);
		
		JLabel lblRoomType = new JLabel("Room Type");
		lblRoomType.setBounds(33, 84, 88, 14);
		contentPane.add(lblRoomType);
		
		JLabel lblNewLabel = new JLabel("Room Price");
		lblNewLabel.setBounds(33, 127, 88, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblRoomCapacity = new JLabel("Room Capacity");
		lblRoomCapacity.setBounds(33, 169, 99, 14);
		contentPane.add(lblRoomCapacity);
		
		JLabel lblRoomFeatures = new JLabel("Room Features");
		lblRoomFeatures.setBounds(33, 260, 88, 14);
		contentPane.add(lblRoomFeatures);
		
		txtRoomNumber = new JTextField();
		txtRoomNumber.setBounds(164, 41, 96, 20);
		contentPane.add(txtRoomNumber);
		txtRoomNumber.setColumns(10);
		
		txtRoomPrice = new JTextField();
		txtRoomPrice.setColumns(10);
		txtRoomPrice.setBounds(164, 124, 96, 20);
		contentPane.add(txtRoomPrice);
		
		txtRoomCapacity = new JTextField();
		txtRoomCapacity.setColumns(10);
		txtRoomCapacity.setBounds(164, 166, 96, 20);
		contentPane.add(txtRoomCapacity);
		
		JTextArea txtFeatures = new JTextArea();
		txtFeatures.setBounds(142, 227, 282, 97);
		contentPane.add(txtFeatures);
		
		JComboBox cmbxRoomType = new JComboBox();
		cmbxRoomType.setModel(new DefaultComboBoxModel(new String[] {"Single", "Double", "Suite"}));
		cmbxRoomType.setBounds(164, 80, 86, 22);
		contentPane.add(cmbxRoomType);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Room room = new Room();
				
				room.setRoomNumber(Integer.parseInt(txtRoomNumber.getText()));
				room.setPrice(Integer.parseInt(txtRoomPrice.getText()));
				room.setRoomType(cmbxRoomType.getSelectedItem().toString());
				room.setCapacity(Integer.parseInt(txtRoomCapacity.getText()));
				room.setFeatures(txtFeatures.getText().toString());
				
				try {
					roomSer.saveRoom(room);
					JOptionPane.showMessageDialog(contentPane, "room saved!");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				txtRoomNumber.setText("");
				txtRoomPrice.setText("");
				cmbxRoomType.setSelectedIndex(0);
				txtRoomCapacity.setText("");
				txtFeatures.setText("");
			}
		});
		btnSave.setBounds(474, 327, 89, 23);
		contentPane.add(btnSave);
	}
}
