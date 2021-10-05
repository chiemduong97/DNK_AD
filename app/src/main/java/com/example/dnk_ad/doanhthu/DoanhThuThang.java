package com.example.dnk_ad.doanhthu;

public class DoanhThuThang {
    private int thang;
    private Double tong;

    public DoanhThuThang() {
    }

    public DoanhThuThang(int thang, Double tong) {
        this.thang = thang;
        this.tong = tong;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public Double getTong() {
        return tong;
    }

    public void setTong(Double tong) {
        this.tong = tong;
    }
}
