package Hotel;

import java.sql.*;

public class PaymentServices {

    private Connection connection;

    // Constructor to initialize the database connection
    public PaymentServices() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Hotel", "root", "saleem2002");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to process payment and update booking status
    public void processPayment(int bookingID, double paymentAmount, Date paymentDate, String paymentMethod) throws SQLException {
        // Fetch the current paid amount and total price for the booking
        String selectQuery = "SELECT paidAmount, totalPrice FROM bookings WHERE BookingID = ?";
        PreparedStatement pst = connection.prepareStatement(selectQuery);
        pst.setInt(1, bookingID);
        ResultSet rs = pst.executeQuery();
        System.out.println(bookingID);

        if (rs.next()) {
            double paidAmount = rs.getDouble("paidAmount");
            double totalPrice = rs.getDouble("totalPrice");

            // Check if the payment exceeds the total price
            if (paidAmount + paymentAmount > totalPrice) {
                throw new SQLException("Payment amount exceeds the total price of the booking.");
            }

            // Update the paid amount and status in the bookings table
            String updateQuery = "UPDATE bookings SET paidAmount = ?, status = ? WHERE BookingID = ?";
            String status = (paidAmount + paymentAmount == totalPrice) ? "Completed" : "Incomplete";
            PreparedStatement updatePst = connection.prepareStatement(updateQuery);
            updatePst.setDouble(1, paidAmount + paymentAmount);
            updatePst.setString(2, status);
            updatePst.setInt(3, bookingID);
            updatePst.executeUpdate();
        }

        // Add the payment to the payment table
        String insertQuery = "INSERT INTO payment (CustomerID, BookingID, Amount, PaymentDate, PaymentMethod) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement insertPst = connection.prepareStatement(insertQuery);
        insertPst.setInt(1, getCustomerIdByBookingId(bookingID));
        insertPst.setInt(2, bookingID);
        insertPst.setDouble(3, paymentAmount);
        insertPst.setDate(4, paymentDate);  // Payment Date
        insertPst.setString(5, paymentMethod); // Payment Method
        insertPst.executeUpdate();
    }

    // Method to get the list of payments for a specific booking
    public ResultSet getPaymentsForBooking(int bookingID) throws SQLException {
        String query = "SELECT * FROM payment WHERE BookingID = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, bookingID);
        return pst.executeQuery();
    }
    public int getCustomerIdByBookingId(int bookingID) throws SQLException {
        // SQL query to get the customerId from the bookings table using the BookingID
        String query = "SELECT customerId FROM bookings WHERE BookingID = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, bookingID);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getInt(1);  // Return the customerId
        } else {
        	System.out.println(bookingID);
            throw new SQLException("Booking ID not found in the database.");
        }
    }
    public String viewPayments(int bookingID) throws SQLException {
        StringBuilder paymentRecords = new StringBuilder();
        String query = "SELECT * FROM payment WHERE BookingID = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, bookingID);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int paymentId = rs.getInt("PaymentID");
            double amount = rs.getDouble("Amount");
            Date paymentDate = rs.getDate("PaymentDate");
            String paymentMethod = rs.getString("PaymentMethod");
            paymentRecords.append("Payment ID: " + paymentId + "\n")
                          .append("Amount: " + amount + "\n")
                          .append("Payment Date: " + paymentDate + "\n")
                          .append("Payment Method: " + paymentMethod + "\n")
                          .append("---------------------------------\n");
        }

        if (paymentRecords.length() == 0) {
            paymentRecords.append("No payments found for this Booking ID.");
        }

        return paymentRecords.toString();
    }
    public int getBookingId(int customerId) throws SQLException {
        String query = "SELECT bookingId FROM payment WHERE customerID = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, customerId);

        ResultSet rs = pst.executeQuery();
        
        // Check if the result set contains any rows
        if (rs.next()) {
            // If a row is found, get the bookingId
            int bookingId = rs.getInt(1);
            return bookingId;
        } else {
            // If no rows are found, handle the situation (e.g., return a default value or throw an exception)
            throw new SQLException("No booking found for customer ID: " + customerId);
        }
    }



}
