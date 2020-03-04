package in.cocomo.firestoredsc;

import java.util.ArrayList;

public class Contact {

    String name;
    ArrayList<String> email;
    ArrayList<Integer> number;

    public Contact() {
    }

    public Contact(String name, ArrayList<String> email, ArrayList<Integer> phoneNumber) {
        this.name = name;
        this.email = email;
        this.number = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    public void setEmail(ArrayList<String> email) {
        this.email = email;
    }

    public ArrayList<Integer> getNumber() {
        return number;
    }

    public void setNumber(ArrayList<Integer> number) {
        this.number = number;
    }
}
