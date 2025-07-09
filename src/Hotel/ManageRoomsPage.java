package Hotel;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class ManageRoomsPage extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextArea txtDetails;
    private RoomServices roomSer = new RoomServices();
    private boolean view = false;
    private JPanel panel = new JPanel();

    public String showStatus(boolean x) {
        return x ? "Available" : "Unavailable";
    }

    public boolean showStatus(String x) {
        return x.equals("Available");
    }

    public void fillTheTable() throws SQLException {
        tableModel.setRowCount(0); // Clear the table
        roomSer.getRooms().forEach(room -> {
            tableModel.addRow(new Object[] {
                room.getRoomNumber(),
                room.getRoomType(),
                room.getPrice(),
                showStatus(room.isStatus())
            });
        });
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ManageRoomsPage frame = new ManageRoomsPage();
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
    public ManageRoomsPage() {
        setTitle("Managing Rooms");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 50, 400, 400);
        contentPane.add(scrollPane);

        tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
            "Room Number", "Room Type", "Price", "Availability"
        });
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        txtDetails = new JTextArea();
        txtDetails.setBounds(450, 50, 300, 200);
        contentPane.add(txtDetails);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(450, 270, 100, 30);
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    fillTheTable();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(btnRefresh);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(570, 270, 100, 30);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(contentPane, "Please select a room to delete.");
                        return;
                    }

                    int selectedNumber = (int) tableModel.getValueAt(selectedRow, 0);
                    int selectedId = roomSer.getRooms().stream()
                        .filter(r -> r.getRoomNumber() == selectedNumber)
                        .findFirst().get().getRoomId();

                    roomSer.deleteRoom(selectedId);
                    JOptionPane.showMessageDialog(contentPane, "Room deleted!");
                    fillTheTable();
                    txtDetails.setText("");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(btnDelete);

        JButton btnEdit = new JButton("Edit Room");
        btnEdit.setBounds(450, 320, 100, 30);
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!view) {
                    view = true;
                    panel.setVisible(true);
                } else {
                    view = false;
                    panel.setVisible(false);
                }
            }
        });
        contentPane.add(btnEdit);

        JButton btnAddRoom = new JButton("Add New Room");
        btnAddRoom.setBounds(570, 320, 140, 30);
        btnAddRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddRoomPage newRoom = new AddRoomPage();
                newRoom.setVisible(true);
            }
        });
        contentPane.add(btnAddRoom);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        int selectedNumber = (int) tableModel.getValueAt(selectedRow, 0);
                        Room selectedRoom = roomSer.getRooms().stream()
                            .filter(r -> r.getRoomNumber() == selectedNumber)
                            .findFirst().get();

                        String features = "Room ID: " + selectedRoom.getRoomId() + "\tRoom Number: " + selectedRoom.getRoomNumber() +
                                         "\nRoom Type: " + selectedRoom.getRoomType() + "\tRoom Capacity: " + selectedRoom.getCapacity() +
                                         "\nPrice per Night: " + selectedRoom.getPrice() + "\tAvailability: " + showStatus(selectedRoom.isStatus()) +
                                         "\n\nFeatures: \n" + selectedRoom.getFeatures();

                        txtDetails.setText(features);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        panel.setBounds(450, 370, 300, 150);
        panel.setLayout(null);
        panel.setVisible(false);
        contentPane.add(panel);

        JLabel lblNewPrice = new JLabel("New Price:");
        lblNewPrice.setBounds(10, 10, 80, 20);
        panel.add(lblNewPrice);

        JTextField txtNewPrice = new JTextField();
        txtNewPrice.setBounds(100, 10, 150, 20);
        panel.add(txtNewPrice);

        JLabel lblAvailability = new JLabel("Availability:");
        lblAvailability.setBounds(10, 40, 80, 20);
        panel.add(lblAvailability);

        JComboBox<String> cmbxAvailability = new JComboBox<>(new String[] { "Available", "Unavailable" });
        cmbxAvailability.setBounds(100, 40, 150, 20);
        panel.add(cmbxAvailability);

        JLabel lblFeatures = new JLabel("Features:");
        lblFeatures.setBounds(10, 70, 80, 20);
        panel.add(lblFeatures);

        JTextArea txtFeatures = new JTextArea();
        txtFeatures.setBounds(100, 70, 150, 50);
        panel.add(txtFeatures);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(100, 130, 80, 20);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(contentPane, "Please select a room to edit.");
                        return;
                    }

                    int selectedNumber = (int) tableModel.getValueAt(selectedRow, 0);
                    int selectedId = roomSer.getRooms().stream()
                        .filter(r -> r.getRoomNumber() == selectedNumber)
                        .findFirst().get().getRoomId();

                    roomSer.updateRoom(selectedId, showStatus((String) cmbxAvailability.getSelectedItem()),
                        Integer.parseInt(txtNewPrice.getText()), txtFeatures.getText());

                    JOptionPane.showMessageDialog(contentPane, "Room updated successfully.");
                    fillTheTable();
                    txtDetails.setText("");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(contentPane, "Please enter a valid price.");
                }
            }
        });
        panel.add(btnSave);

        try {
            fillTheTable();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
