package com.example.dnk_ad.phieudangky;

public class PhieuDangKy {
    private int mapdk;
    private int mahv;
    private int malop;
    private int trangthai;
    private String ngaydonghocphi;
    private Double tiendong;
    private String batdau;
    private String ketthuc;
    private String cahoc;
    public PhieuDangKy(){}

    public PhieuDangKy(int mapdk, int mahv, int malop, int trangthai, String ngaydonghocphi, Double tiendong, String batdau, String ketthuc, String cahoc) {
        this.mapdk = mapdk;
        this.mahv = mahv;
        this.malop = malop;
        this.trangthai = trangthai;
        this.ngaydonghocphi = ngaydonghocphi;
        this.tiendong = tiendong;
        this.batdau=batdau;
        this.ketthuc=ketthuc;
        this.cahoc=cahoc;
    }

    public int getMapdk() {
        return mapdk;
    }

    public void setMapdk(int mapdk) {
        this.mapdk = mapdk;
    }

    public int getMahv() {
        return mahv;
    }

    public void setMahv(int mahv) {
        this.mahv = mahv;
    }

    public int getMalop() {
        return malop;
    }

    public void setMalop(int malop) {
        this.malop = malop;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getNgaydonghocphi() {
        return ngaydonghocphi;
    }

    public void setNgaydonghocphi(String ngaydonghocphi) {
        this.ngaydonghocphi = ngaydonghocphi;
    }

    public Double getTiendong() {
        return tiendong;
    }

    public void setTiendong(Double tiendong) {
        this.tiendong = tiendong;
    }

    public String getBatdau() {
        return batdau;
    }

    public void setBatdau(String batdau) {
        this.batdau = batdau;
    }

    public String getKetthuc() {
        return ketthuc;
    }

    public void setKetthuc(String ketthuc) {
        this.ketthuc = ketthuc;
    }

    public String getCahoc() {
        return cahoc;
    }

    public void setCahoc(String cahoc) {
        this.cahoc = cahoc;
    }
}
