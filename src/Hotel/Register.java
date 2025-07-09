package Hotel;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class Register {
	String firstName, lastName, email, phone, username, password,gender;
	int age,id;
	Date date;
	
	
	public Register() {
		
	}
	public Register(String firstName, String lastName, String email, String phone, String username, String password,
			String gender, int age, int id, Date date) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.age = age;
		this.id = id;
		this.date = date;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
		this.age = calculateAge(date);
	}
	
	
	

    public int calculateAge(Date birthDate) {
        try {
            // Get current date
            Calendar currentDate = Calendar.getInstance();
            Calendar birthDateCalendar = Calendar.getInstance();
            birthDateCalendar.setTime(birthDate);

            // Calculate age by comparing years, months, and days
            int age = currentDate.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR);

            // Check if the birthday has passed this year
            if (currentDate.get(Calendar.MONTH) < birthDateCalendar.get(Calendar.MONTH) ||
                (currentDate.get(Calendar.MONTH) == birthDateCalendar.get(Calendar.MONTH) &&
                 currentDate.get(Calendar.DAY_OF_MONTH) < birthDateCalendar.get(Calendar.DAY_OF_MONTH))) {
                age--;
            }

            return age;
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return -1 in case of error
        }
    }
	

}
