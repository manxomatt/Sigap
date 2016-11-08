package com.app.sources;

/**
 * Created by blue on 08/11/16.
 */

public class PolisiLog {

    String id, kategori, nama, keterangan;

    public PolisiLog() {
    }

    public PolisiLog (String id, String kategori, String nama, String keterangan) {
        this.id = id;
        this.kategori = kategori;
        this.nama = nama;
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
