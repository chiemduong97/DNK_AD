package com.example.dnk_ad.loaikhoahoc;

public class LoaiKhoaHoc {
    private int maloai;
    private String tenloai;
    public LoaiKhoaHoc(){}
    public LoaiKhoaHoc(int maloai,String tenloai){
        this.maloai=maloai;
        this.tenloai=tenloai;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }
}
