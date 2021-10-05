package com.example.dnk_ad.doanhthu;

public class DoanhThuNam {
    private int nam;
    private Double tong;
    public DoanhThuNam(){}

    public DoanhThuNam(int nam, Double tong) {
        this.nam = nam;
        this.tong = tong;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public Double getTong() {
        return tong;
    }

    public void setTong(Double tong) {
        this.tong = tong;
    }
}
