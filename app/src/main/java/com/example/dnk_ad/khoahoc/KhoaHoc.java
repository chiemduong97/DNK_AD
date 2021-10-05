package com.example.dnk_ad.khoahoc;

public class KhoaHoc {
    private int makh;
    private String tenkh;
    private int maloai;

    public KhoaHoc() {
    }

    public KhoaHoc(int makh, String tenkh, int maloai) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.maloai = maloai;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }
}
