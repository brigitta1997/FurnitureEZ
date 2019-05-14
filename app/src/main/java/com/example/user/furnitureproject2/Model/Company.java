package com.example.user.furnitureproject2.Model;

public class Company {
    private int id;
    private String name;
    private String logo;
    private String contactNum;
    private String address;

    public Company(){

    }

    public Company(int id, String name, String logo, String contactNum, String address) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.contactNum = contactNum;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
