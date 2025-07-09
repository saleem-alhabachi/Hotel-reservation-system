package Hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RegisterServices {
	
	public Connection getConnected() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/Hotel","root","saleem2002");
	}
	
	public ArrayList<Register> getRegisters() throws SQLException{
		Statement st = getConnected().createStatement();
		ResultSet rs = st.executeQuery("select * from Register");
		ArrayList<Register> registers = new ArrayList<Register>();
		while(rs.next()) {
			Register register = new Register();
			register.setId(rs.getInt(1));
			register.setFirstName(rs.getString(2));
			register.setLastName(rs.getString(3));
			register.setEmail(rs.getString(4));
			register.setPhone(rs.getString(5));
			register.setPassword(rs.getString(6));
			register.setUsername(rs.getString(7));
			register.setGender(rs.getString(8));
			register.setAge(rs.getInt(9));
			register.setDate(rs.getDate(10));
			registers.add(register);
		}
		return registers;
	}
	public void addRegister(Register register) throws SQLException {
        PreparedStatement pst = getConnected().prepareStatement("insert into Register(FirstName, LastName, Email, phone, passwordhash, username, gender, age, birthdate) values(?,?,?,?,?,?,?,?,?)");
        pst.setString(1, register.getFirstName());
        pst.setString(2, register.getLastName());
        pst.setString(3, register.getEmail());
        pst.setString(4, register.getPhone());
        pst.setString(5, register.getPassword());
        pst.setString(6, register.getUsername());
        pst.setString(7, register.getGender());
        pst.setInt(8, register.getAge());
        pst.setDate(9, register.getDate());
        pst.executeUpdate();
        
    }
	 public void deleteRegister(int id) throws SQLException {
	        PreparedStatement pst = getConnected().prepareStatement("delete from Register where id = ?");
	        pst.setInt(1, id);
	        pst.executeUpdate();
	    }
	 
	 

	
}
