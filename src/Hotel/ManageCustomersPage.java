package Hotel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.SQLException;

public class ManageCustomersPage extends JFrame {
    private JPanel contentPane;
    private JTable table;
    private CustomerServices customerServices = new CustomerServices();
    private RegisterServices registerServices = new RegisterServices();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ManageCustomersPage frame = new ManageCustomersPage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ManageCustomersPage() {
        setTitle("Manage Customers");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "ID", "First Name", "Last Name", "Email", "Phone", "Username", "Gender", "Age", "Birthdate", "Passport", "Nationalities"
                }
        ));
        scrollPane.setViewportView(table);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);

        JButton btnAdd = new JButton("Add Customer");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });
        panel.add(btnAdd);

        JButton btnDelete = new JButton("Delete Customer");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteCustomer();
            }
        });
        panel.add(btnDelete);

        loadCustomers();
    }

    private void loadCustomers() {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (Customer customer : customerServices.getCustomers()) {
                model.addRow(new Object[] {
                        customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                        customer.getPhone(), customer.getUsername(), customer.getGender(), customer.getAge(),
                        customer.getDate(), customer.getPassport(), customer.getNationalities()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading customers: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCustomer() {
    	openAddRegisterDialog();
    }

    private void deleteCustomer() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a customer to delete!");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            customerServices.deleteCustomer(id);
            loadCustomers();
            JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting customer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 // Method to open a dialog to add a new register
    private void openAddRegisterDialog() {
        JDialog addDialog = new JDialog(this, "Add Customer", true);
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
        
        JLabel lblPassport = new JLabel("Passport:");
        lblPassport.setBounds(10, 330, 100, 25);
        addDialog.add(lblPassport);
        JTextField txtPassport = new JTextField();
        txtPassport.setBounds(120, 330, 200, 25);
        addDialog.add(txtPassport);
        
        JLabel lblNationalities = new JLabel("Nationalities:");
        lblNationalities.setBounds(10, 370, 100, 25);
        addDialog.add(lblNationalities);
        JTextField txtNationalities = new JTextField();
        txtNationalities.setBounds(120, 370, 200, 25);
        addDialog.add(txtNationalities);
        
        
        

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(150, 420, 100, 30);
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
                    JOptionPane.showMessageDialog(addDialog, "Customer added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    addDialog.dispose();
                    //loadRegisters();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(addDialog, "Error adding customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                Customer newCustomer = new Customer();
                newCustomer.fillCustomer(newRegister);
                newCustomer.setNationalities(txtNationalities.getText());
                newCustomer.setPassport(txtPassport.getText());
                try {
					customerServices.addCustomer(newCustomer);
					loadCustomers();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        addDialog.add(btnSave);

        addDialog.setVisible(true);
    }
}
