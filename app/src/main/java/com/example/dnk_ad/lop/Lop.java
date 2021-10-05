package com.example.dnk_ad.lop;

public class Lop {
    private int malop;
    private String tenlop;
    private String mota;
    private int makh;
    private int magv;
    private String batdau;
    private String ketthuc;
    private String cahoc;
    private String anhminhhoa;
    private Double danhgia;
    private Double hocphi;
    public Lop(){}

    public Lop(int malop, String tenlop, String mota, int makh, int magv, String batdau, String ketthuc, String cahoc, String anhminhhoa, Double danhgia, Double hocphi) {
        this.malop = malop;
        this.tenlop = tenlop;
        this.mota = mota;
        this.makh = makh;
        this.magv = magv;
        this.batdau = batdau;
        this.ketthuc = ketthuc;
        this.cahoc=cahoc;
        this.anhminhhoa = anhminhhoa;
        this.danhgia = danhgia;
        this.hocphi = hocphi;
    }

    public int getMalop() {
        return malop;
    }

    public void setMalop(int malop) {
        this.malop = malop;
    }

    public String getTenlop() {
        return tenlop;
    }

    public void setTenlop(String tenlop) {
        this.tenlop = tenlop;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public int getMagv() {
        return magv;
    }

    public void setMagv(int magv) {
        this.magv = magv;
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

    public String getAnhminhhoa() {
        return anhminhhoa;
    }

    public void setAnhminhhoa(String anhminhhoa) {
        this.anhminhhoa = anhminhhoa;
    }

    public Double getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(Double danhgia) {
        this.danhgia = danhgia;
    }

    public Double getHocphi() {
        return hocphi;
    }

    public void setHocphi(Double hocphi) {
        this.hocphi = hocphi;
    }
}
