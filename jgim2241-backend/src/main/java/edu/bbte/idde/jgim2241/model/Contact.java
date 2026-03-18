package edu.bbte.idde.jgim2241.model;

import java.util.Date;

public class Contact extends BaseEntity {
    private String name;
    private String email;
    private String phoneNumber;
    private Date birthdate;
    private String address;

    public Contact() {
        super();
    }

    public Contact(Long id, String name, String email, String phoneNumber, Date birthdate, String address) {
        super(id);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.address = address;
    }

    public Contact(String name, String email, String phoneNumber, Date birthdate, String address) {
        super();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Contact{"
                + "name='" + name + '\''
                + ", email='" + email + '\''
                + ", phoneNumber='" + phoneNumber + '\''
                + ", birthdate=" + birthdate
                + ", address='" + address + '\''
                + ", id=" + id + '}';
    }
}
