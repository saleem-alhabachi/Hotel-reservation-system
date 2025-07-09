package Hotel;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ManageRegistersPage extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private RegisterServices registerServices;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ManageRegistersPage frame = new ManageRegistersPage();
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
    public ManageRegistersPage() {
        setTitle("Manage Registers");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        registerServices = new RegisterServices();

        // Table to display register data
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "ID", "First Name", "Last Name", "Email", "Phone", "Username", "Gender", "Age", "Birthdate" });
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 760, 400);
        contentPane.add(scrollPane);

        // Button to load registers
        JButton btnLoad = new JButton("Load Registers");
        btnLoad.setBounds(10, 420, 150, 30);
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadRegisters();
            }
        });
        contentPane.add(btnLoad);

        // Button to delete a selected register
        JButton btnDelete = new JButton("Delete Register");
        btnDelete.setBounds(170, 420, 150, 30);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedRegister();
            }
        });
        contentPane.add(btnDelete);

        // Button to add a new register
        JButton btnAdd = new JButton("Add Register");
        btnAdd.setBounds(330, 420, 150, 30);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddRegisterDialog();
            }
        });
        contentPane.add(btnAdd);
    }

    // Method to load registers into the table
    private void loadRegisters() {
        try {
            ArrayList<Register> registers = registerServices.getRegisters();
            tableModel.setRowCount(0); // Clear existing rows
            for (Register register : registers) {
                tableModel.addRow(new Object[] {
                        register.getId(),
                        register.getFirstName(),
                        register.getLastName(),
                        register.getEmail(),
                        register.getPhone(),
                        register.getUsername(),
                        register.getGender(),
                        register.getAge(),
                        register.getDate()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading registers: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to delete the selected register
    private void deleteSelectedRegister() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                registerServices.deleteRegister(id);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Register deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting register: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a register to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Method to open a dialog to add a new register
    private void openAddRegisterDialog() {
        JDialog addDialog = new JDialog(this, "Add Register", true);
        addDialog.setSize(400, 500);
        addDialog.setLayout(null);

        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setBounds(10, 10, 100, 25);
        addDialog.add(lblFirstName);
        JTextField txtFirstName = new JTextField();
        txtFirstName.setBounds(120, 10, 200, 25);
        addDialog.add(txtFirstName);

        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setBounds(10, 50, 100, 25);
        addDialog.add(lblLastName);
        JTextField txtLastName = new JTextField();
        txtLastName.setBounds(120, 50, 200, 25);
        addDialog.add(txtLastName);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(10, 90, 100, 25);
        addDialog.add(lblEmail);
        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(120, 90, 200, 25);
        addDialog.add(txtEmail);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(10, 130, 100, 25);
        addDialog.add(lblPhone);
        JTextField txtPhone = new JTextField();
        txtPhone.setBounds(120, 130, 200, 25);
        addDialog.add(txtPhone);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(10, 170, 100, 25);
        addDialog.add(lblUsername);
        JTextField txtUsername = new JTextField();
        txtUsername.setBounds(120, 170, 200, 25);
        addDialog.add(txtUsername);
        
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(10, 210, 100, 25);
        addDialog.add(lblPassword);
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 210, 200, 25);
        addDialog.add(txtPassword);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(10, 250, 100, 25);
        addDialog.add(lblGender);
        JComboBox txtGender = new JComboBox();
        txtGender.addItem("Male");
        txtGender.addItem("Female");
        txtGender.setBounds(120, 250, 200, 25);
        addDialog.add(txtGender);
        
        
       

        JLabel lblBirthdate = new JLabel("Birthdate (YYYY-MM-DD):");
        lblBirthdate.setBounds(10, 290, 150, 25);
        addDialog.add(lblBirthdate);
        JTextField txtBirthdate = new JTextField();
        txtBirthdate.setBounds(170, 290, 150, 25);
        addDialog.add(txtBirthdate);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(150, 400, 100, 30);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Register newRegister = new Register();
                newRegister.setFirstName(txtFirstName.getText());
                newRegister.setLastName(txtLastName.getText());
                newRegister.setEmail(txtEmail.getText());
                newRegister.setPhone(txtPhone.getText());
                newRegister.setPassword(new String(txtPassword.getPassword()));
                newRegister.setUsername(txtUsername.getText());
                newRegister.setGender(txtGender.getSelectedItem().toString());
                newRegister.setDate(java.sql.Date.valueOf(txtBirthdate.getText()));

                try {
                    registerServices.addRegister(newRegister);
                    JOptionPane.showMessageDialog(addDialog, "Register added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    addDialog.dispose();
                    loadRegisters();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(addDialog, "Error adding register: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addDialog.add(btnSave);

        addDialog.setVisible(true);
    }
}
