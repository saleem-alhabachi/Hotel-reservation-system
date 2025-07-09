package Hotel;

import java.sql.*;
import java.util.ArrayList;

public class CustomerServices {

    // Establish a connection to the database
    public Connection getConnected() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/Hotel", "root", "saleem2002");
    }

    // Fetch all customers with full details (Join Register and Customer tables)
    public ArrayList<Customer> getCustomers() throws SQLException {
        String query = """
                SELECT r.RegisterId, r.firstName, r.lastName, r.email, r.phone, r.username, r.gender, r.age, r.birthdate,
                       c.passport, c.nationality
                FROM Register r
                INNER JOIN Customers c ON r.RegisterId = c.CustomerId
                """;

        Statement st = getConnected().createStatement();
        ResultSet rs = st.executeQuery(query);

        ArrayList<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            Customer customer = new Customer();
            customer.setId(rs.getInt("RegisterId"));
            customer.setFirstName(rs.getString("firstName"));
            customer.setLastName(rs.getString("lastName"));
            customer.setEmail(rs.getString("email"));
            customer.setPhone(rs.getString("phone"));
            customer.setUsername(rs.getString("username"));
            customer.setGender(rs.getString("gender"));
            customer.setAge(rs.getInt("age"));
            customer.setDate(rs.getDate("birthdate"));
            customer.setPassport(rs.getString("passport"));
            customer.setNationalities(rs.getString("nationality"));

            customers.add(customer);
        }
        return customers;
    }

    // Add a new customer
    public void addCustomer(Customer customer) throws SQLException {
        
               
        Connection connection = getConnected();
       
        // Insert into Customer table
        String customerQuery = "INSERT INTO Customers (Customerid, passport, nationality) VALUES (?, ?, ?)";
        PreparedStatement customerPst = connection.prepareStatement(customerQuery);
        customerPst.setInt(1, customer.getId());
        customerPst.setString(2, customer.getPassport());
        customerPst.setString(3, customer.getNationalities());
        customerPst.executeUpdate();
    }
    
    public void addtoCustomer(Customer customer) throws SQLException {
        
        
        Connection connection = getConnected();
       
        // Insert into Customer table
        String customerQuery = "UPDATE customer set nationality = ? , passport = ? where customerID = ?";
        PreparedStatement customerPst = connection.prepareStatement(customerQuery);
       
        
        customerPst.setString(1, customer.getNationalities());
        customerPst.setString(2, customer.getPassport());
        customerPst.setInt(3, customer.getId());
        customerPst.executeUpdate();
    }

    // Delete a customer
    public void deleteCustomer(int id) throws SQLException {
        String query = "DELETE FROM Register WHERE registerid = ?";
        PreparedStatement pst = getConnected().prepareStatement(query);
        pst.setInt(1, id);
        pst.executeUpdate();
    }
    //check if a customer exists
    public boolean isCustomerExists(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM Customers WHERE customerId = ?";
        PreparedStatement pst = getConnected().prepareStatement(query);
        pst.setInt(1, id);
        
        ResultSet rs = pst.executeQuery();
        
        // Check if the result set contains a row
        if (rs.next()) {
            // If a row is found, get the count and check if it's greater than 0
            int count = rs.getInt(1);
            return count > 0;
        } else {
            // If no rows are found, the customer doesn't exist
            return false;
        }
    }

}
