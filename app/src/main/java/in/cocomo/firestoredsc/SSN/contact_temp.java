package in.cocomo.firestoredsc.SSN;

import com.google.type.LatLng;

public class contact_temp {

    String name;
    int number;
    String emailId;


    public contact_temp(String name, int number, String emailId) {
        this.name = name;
        this.number = number;
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
