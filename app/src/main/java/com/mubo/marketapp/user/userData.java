package com.mubo.marketapp.user;

public class userData {
    private String usename;
    private String id;
    private String address;

    public userData(String usename, String id, String address) {
        this.usename = usename;
        this.id = id;
        this.address = address;
    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
