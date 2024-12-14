package com.example.btl_appbanhang.Model;

public class Address {
    private int id;
    private int user_id;
    private String name;
    private String phone;
    private String address;
    private String ward;
    private String district;
    private String city;
    private int is_active;

    public Address() {
    }

    public Address(int user_id, String name, String phone, String address, String ward, String district, String city) {
        this.user_id = user_id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.ward = ward;
        this.district = district;
        this.city = city;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
