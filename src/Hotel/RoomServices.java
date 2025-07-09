package Hotel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;




public class RoomServices {
	public Connection getConnected() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/Hotel","root","saleem2002");
	}
	
	public ArrayList<Room> getRooms() throws SQLException{
		Statement st = getConnected().createStatement();
		ResultSet rs = st.executeQuery("select * from Rooms");
		ArrayList<Room> rooms = new ArrayList<Room>();
		while(rs.next()) {
			Room room = new Room();
			room.setRoomId(rs.getInt(1));
			room.setRoomNumber(rs.getInt(2));
			room.setRoomType(rs.getString(3));
			room.setPrice(rs.getInt(4));
			room.setStatus(rs.getBoolean(5));
			room.setCapacity(rs.getInt(6));
			room.setFeatures(rs.getString(7));
			rooms.add(room);
			
		}
		return rooms;
	}
	
	public void saveRoom(Room r) throws SQLException {
			
			String query = "insert into Rooms (RoomNumber, RoomType, Price, Capacity,Features) values(?,?,?,?,?)";
			PreparedStatement ps = getConnected().prepareStatement(query);
			ps.setInt(1, r.getRoomNumber());
			ps.setString(2, r.getRoomType());
			ps.setInt(3, r.getPrice());
			ps.setInt(4, r.getCapacity());
			ps.setString(5, r.getFeatures());
			ps.executeUpdate();
		}
	
	public void deleteRoom(int roomId) throws SQLException {
		String query = "delete from Rooms where roomId=?";
		PreparedStatement ps = getConnected().prepareStatement(query);
		ps.setInt(1, roomId);
		ps.executeUpdate();
	}
	
	public void updateRoom(int roomId, boolean status, int price,String features) throws SQLException {
		String query = "update rooms set price = ?, status = ?, features = ? where roomId = ?";
		PreparedStatement ps = getConnected().prepareStatement(query);
		ps.setInt(1, price);
		ps.setBoolean(2, status);
		ps.setString(3, features);
		ps.setInt(4,roomId);
		ps.executeUpdate();
	}
	public double getRoomPrice(int roomID) throws SQLException {
	    String query = "SELECT Price FROM rooms WHERE roomID = ?";
	    
	    try (PreparedStatement pst = getConnected().prepareStatement(query)) {
	        pst.setInt(1, roomID);
	        try (ResultSet rs = pst.executeQuery()) {
	            if (rs.next()) {
	                return rs.getDouble("Price");
	            } else {
	                throw new SQLException("Room not found with ID: " + roomID);
	            }
	        }
	    }
	}
	public ArrayList<Room> getAvailableRooms(String roomType, LocalDate checkIn, LocalDate checkOut) throws SQLException {
	    String query = "SELECT * FROM Rooms WHERE RoomType = ? AND Status = 1 AND RoomID NOT IN "
	                + "(SELECT RoomID FROM Bookings WHERE (? < CheckOutDate AND ? > CheckInDate))";
	    try (PreparedStatement pst = getConnected().prepareStatement(query)) {
	        pst.setString(1, roomType);
	        pst.setDate(2, Date.valueOf(checkIn));
	        pst.setDate(3, Date.valueOf(checkOut));
	        try (ResultSet rs = pst.executeQuery()) {
	            ArrayList<Room> availableRooms = new ArrayList<>();
	            while (rs.next()) {
	                Room room = new Room();
	                room.setRoomId(rs.getInt("RoomID"));
	                room.setRoomNumber(rs.getInt("RoomNumber"));
	                room.setRoomType(rs.getString("RoomType"));
	                room.setPrice(rs.getInt("Price"));
	                room.setStatus(rs.getBoolean("Status"));
	                room.setCapacity(rs.getInt("Capacity"));
	                room.setFeatures(rs.getString("Features"));
	                availableRooms.add(room);
	            }
	            return availableRooms;
	        }
	    }
	}
	// edit the status of the room
	public void updateRoomStatus(int roomId, boolean status) throws SQLException {
			String query = "UPDATE rooms SET Status =? WHERE RoomID =?";
        PreparedStatement ps = getConnected().prepareStatement(query);
        ps.setBoolean(1, status);
        ps.setInt(2, roomId);
        ps.executeUpdate();
	}
	
	// get room id from the room number
	public int getRoomId(int roomNumber) throws SQLException {
			String query = "SELECT RoomID FROM rooms WHERE RoomNumber =?";
        PreparedStatement ps = getConnected().prepareStatement(query);
        ps.setInt(1, roomNumber);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            return rs.getInt("RoomID");
        } else {
            throw new SQLException("Room not found with room number: " + roomNumber);
        }
	}

	

}
