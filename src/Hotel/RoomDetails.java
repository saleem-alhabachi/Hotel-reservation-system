package Hotel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

public class RoomDetails extends JFrame {

	private JPanel contentPane;
	public static Room room = new Room();
	
	
	public String showStatus(boolean x) {
        return x ? "Available" : "Unavailable";
    }

    public boolean showStatus(String x) {
        return x.equals("Available");
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RoomDetails frame = new RoomDetails(room);
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
	public RoomDetails(Room room) {
		this.room = room;
		setTitle("Room Details");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 402, 259);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(43, 11, 317, 183);
		contentPane.add(textArea);
		
		String details =  
                "Room Type: " + room.getRoomType() + "\tRoom Capacity: " + room.getCapacity() +
                "\nPrice per Night: " + room.getPrice() + 
                "\n\nFacilities: \n" + room.getFeatures();
		textArea.setText(details);
	}
	
}
