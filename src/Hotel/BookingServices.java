package Hotel;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class BookingServices {
	
	RoomServices roomSer = new RoomServices();

    private Connection getConnected() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/Hotel", "root", "saleem2002");
    }

    // Add a booking
    public void addBooking(Booking booking) throws SQLException {
        // Calculate the number of days between check-in and check-out
        long daysBetween = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        booking.setNumberOfDays((int) daysBetween);

        // Assume room price per day is fetched from the database or a fixed value
        double roomPrice = roomSer.getRoomPrice(booking.getRoomID()); // Fetch room price
        booking.setTotalPrice(roomPrice * booking.getNumberOfDays());
        

        // Set status based on paidAmount
        if (booking.getPaidAmount() == booking.getTotalPrice()) {
            booking.setStatus("Completed");
        } else if (booking.getPaidAmount() > booking.getTotalPrice()) {
            throw new SQLException("Paid amount cannot exceed total price.");
        } else {
            booking.setStatus("Incomplete");
        }

        // Insert booking into the database
        String query = "INSERT INTO bookings (RoomID, CustomerID, CheckInDate, CheckOutDate, PaidAmount, TotalPrice, NumberOfDays, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = getConnected().prepareStatement(query)) {
            pst.setInt(1, booking.getRoomID());
            pst.setInt(2, booking.getCustomerID());
            pst.setDate(3, Date.valueOf(booking.getCheckInDate()));
            pst.setDate(4, Date.valueOf(booking.getCheckOutDate()));
            pst.setDouble(5, booking.getPaidAmount());
            pst.setDouble(6, booking.getTotalPrice());
            pst.setInt(7, booking.getNumberOfDays());
            pst.setString(8, booking.getStatus());
            pst.executeUpdate();
        }
        //edit room status
        String qry = "UPDATE Rooms SET Status = 0 WHERE RoomID = ?";
        try (PreparedStatement pst = getConnected().prepareStatement(qry)) {
            pst.setInt(1, booking.getRoomID());
            pst.executeUpdate();
        }
    }

    // Get all bookings
    public ArrayList<Booking> getAllBookings() throws SQLException {
        String query = "SELECT * FROM bookings";
        try (Statement st = getConnected().createStatement(); ResultSet rs = st.executeQuery(query)) {
            ArrayList<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingID(rs.getInt("BookingID"));
                booking.setRoomID(rs.getInt("RoomID"));
                booking.setCustomerID(rs.getInt("CustomerID"));
                booking.setCheckInDate(rs.getDate("CheckInDate").toLocalDate());
                booking.setCheckOutDate(rs.getDate("CheckOutDate").toLocalDate());
                booking.setPaidAmount(rs.getDouble("PaidAmount"));
                booking.setTotalPrice(rs.getDouble("TotalPrice"));
                booking.setNumberOfDays(rs.getInt("NumberOfDays"));
                booking.setStatus(rs.getString("Status"));
                bookings.add(booking);
            }
            return bookings;
        }
    }

    // Delete a booking
    public void deleteBooking(int bookingId , int roomId) throws SQLException {
        String query = "DELETE FROM bookings WHERE BookingID = ?";
        try (PreparedStatement pst = getConnected().prepareStatement(query)) {
            pst.setInt(1, bookingId);
            pst.executeUpdate();
            //update room status
            roomSer.updateRoomStatus(roomId, true);
            
        }
    }
    public ArrayList<Booking> getBookings(int customerId) throws SQLException {
    	String query = "SELECT BookingID,CheckInDate,CheckOutDate,TotalPrice,PaidAmount FROM Bookings\r\n"
    			+ "                            JOIN Rooms room ON Bookings.RoomID = room.RoomID\r\n"
    			+ "                                                                               WHERE CustomerID = ?";
    	try (PreparedStatement pst = getConnected().prepareStatement(query)) {
    		pst.setInt(1, customerId);
    		ResultSet rs = pst.executeQuery();
    		ArrayList<Booking> bookings = new ArrayList<>();
    		while (rs.next()) {
    				Booking booking = new Booking();
                
                booking.setBookingID(rs.getInt("BookingID"));
                booking.setCheckInDate(rs.getDate("CheckInDate").toLocalDate());
                booking.setCheckOutDate(rs.getDate("CheckOutDate").toLocalDate());
                booking.setTotalPrice(rs.getDouble("TotalPrice"));
                booking.setPaidAmount(rs.getDouble("PaidAmount"));
                
                bookings.add(booking);
    		}
    		return bookings;
    	}
    }
    public int getRoomId(int bookingId) throws SQLException {
    	String query = "SELECT RoomID FROM Bookings WHERE BookingID =?";
    	PreparedStatement pst = getConnected().prepareStatement(query);
        pst.setInt(1, bookingId);
        ResultSet rs = pst.executeQuery();
        if(rs.next()) 
            return rs.getInt("RoomID");
        return 0;
        
    }
    //check if booking exists
    public boolean bookingExists(int customerId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Bookings WHERE CustomerId = ?";
        PreparedStatement pst = getConnected().prepareStatement(query);
        pst.setInt(1,customerId );
        
        ResultSet rs = pst.executeQuery();
        
        // Check if the result set contains a row
        if (rs.next()) {
            // If a row is found, get the count and check if it's greater than 0
            return rs.getInt(1) > 0;
        } else {
            // If no rows are found, the booking doesn't exist
            return false;
        }
    }
    // get booking id from room and customer id 
    public int getBookingId(int roomId, int customerId) throws SQLException {
        String query = "SELECT BookingID FROM Bookings WHERE RoomID =? AND CustomerID =?";
        PreparedStatement pst = getConnected().prepareStatement(query);
        pst.setInt(1, roomId);
        pst.setInt(2, customerId);
        
        ResultSet rs = pst.executeQuery();
        if(rs.next()) 
            return rs.getInt("BookingID");
        return 0;
        
    }
    
    // update booking status
    public void updateBookingStatus(int bookingId, String status) throws SQLException {
        String query = "UPDATE Bookings SET Status =? WHERE BookingID =?";
        PreparedStatement pst = getConnected().prepareStatement(query);
        pst.setString(1, status);
        pst.setInt(2, bookingId);
        
        pst.executeUpdate();
        
    }
    
    // update paid amount
    
    

    

   
}
