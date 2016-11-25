package com.lms.cmpe.model;

import javax.persistence.*;

@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int phoneId;
    @Column(unique = true)
    private String number;  // Note, phone numbers must be unique
    private String description;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    public Phone(){}
    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public Phone(String number, String description, Address address) {
        this.number = number;
        this.description = description;
        this.address = address;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
