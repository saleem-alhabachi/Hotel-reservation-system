package Hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdminServices {
	public Connection getConnected() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/Hotel","root","saleem2002");
	}
	public ArrayList<Admin> getAdmins() throws SQLException{
		Statement st = getConnected().createStatement();
		ResultSet rs = st.executeQuery("select * from Admins");
		ArrayList<Admin> admins= new ArrayList<Admin>();
		while(rs.next()) {
			Admin admin = new Admin();
			admin.setAdminId(rs.getInt(1));
			admin.setFirstName(rs.getString(2));
			admin.setLastName(rs.getString(3));
			admin.setUsername(rs.getString(4));
			admin.setPassword(rs.getString(5));
			admin.setRole(rs.getString(6));
			admins.add(admin);
			
		}
		return admins;
	}
	public void addAdmin(Admin admin) throws SQLException {
		 PreparedStatement pst = getConnected().prepareStatement("insert into Admin(FirstName,LastName,Username,Passwordhash,role values(?, ?, ?, ?, ?)"); 
		 pst.setString(1, admin.getFirstName());
		 pst.setString(2, admin.getLastName());
		 pst.setString(3, admin.getUsername());
		 pst.setString(4, admin.getPassword());
		 pst.setString(5, admin.getRole());
		 pst.executeUpdate();
		 
    }

}
