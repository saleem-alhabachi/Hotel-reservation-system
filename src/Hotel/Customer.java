package Hotel;

import java.sql.Date;

public class Customer extends Register {
    private String passport="";
    private String nationalities="";

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getNationalities() {
        return nationalities;
    }

    public void setNationalities(String nationalities) {
        this.nationalities = nationalities;
    }
    public void fillCustomer(Register register) {
    
    	this.setId(register.getId());
        this.setFirstName(register.getFirstName());
        this.setLastName(register.getLastName());
        this.setEmail(register.getEmail());
        this.setPhone(register.getPhone());
        this.setUsername(register.getUsername());
        this.setPassword(register.getPassword());
        this.setGender(register.getGender());
        this.setAge(register.getAge());
        this.setDate(register.getDate());
   }
}
