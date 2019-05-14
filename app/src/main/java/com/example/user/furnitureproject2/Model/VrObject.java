package com.example.user.furnitureproject2.Model;


import java.math.BigDecimal;

public class VrObject {

    private int id;
    private String name;
    private Double price;
    private String obj3dl;
    private String obj2dl;
    private String descr;
    private String sfbLink;
    private String company;
    private String type;

    public VrObject(){

    }
    public VrObject(int id,String name,Double price, String obj3dl, String obj2dl, String descr, String sfbLink, String company, String type){
        this.id = id;
        this.name = name;
        this.price = price;
        this.obj3dl = obj3dl;
        this.obj2dl = obj2dl;
        this.descr = descr;
        this.sfbLink = sfbLink;
        this.company = company;
        this.type = type;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getId() {

        return id;
    }

    public String getSfbLink() {
        return sfbLink;
    }

    public void setSfbLink(String sfbLink) {
        this.sfbLink = sfbLink;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getObj3dl() {
        return obj3dl;
    }

    public void setObj3dl(String obj3dl) {
        this.obj3dl = obj3dl;
    }

    public String getObj2dl() {
        return obj2dl;
    }

    public void setObj2dl(String obj2dl) {
        this.obj2dl = obj2dl;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
