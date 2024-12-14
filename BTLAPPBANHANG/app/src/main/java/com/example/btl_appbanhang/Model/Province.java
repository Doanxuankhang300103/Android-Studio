package com.example.btl_appbanhang.Model;

public class Province {
//    id: "89",
//    name: "An Giang",
//    name_en: "An Giang",
//    full_name: "Tỉnh An Giang",
//    full_name_en: "An Giang Province",
//    latitude: "10.5392057",
//    longitude: "105.2312822"
    private String id;
    private String name;
    private String name_en;
    private String full_name;
    private String full_name_en;
    private String latitude;
    private String longitude;

    public Province() {
    }

    public Province(String name, String name_en, String full_name, String full_name_en, String latitude, String longitude) {
        this.name = name;
        this.name_en = name_en;
        this.full_name = full_name;
        this.full_name_en = full_name_en;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getFull_name_en() {
        return full_name_en;
    }

    public void setFull_name_en(String full_name_en) {
        this.full_name_en = full_name_en;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
