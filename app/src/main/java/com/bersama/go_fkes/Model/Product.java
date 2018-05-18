package com.bersama.go_fkes.Model;

public class Product {

    private int id;
    private String nama;
    private double lat;
    private double lng;
    private String alamat;
    private String notelp;
    private String image;



    public Product(int id, String nama,  String alamat, String notelp, String image) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.notelp = notelp;
        this.image = image;
    }


    public String getImage() { return image; }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNotelp() {
        return notelp;
    }
}
