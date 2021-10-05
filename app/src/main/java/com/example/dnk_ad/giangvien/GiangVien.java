package com.example.dnk_ad.giangvien;

public class GiangVien {
    private int magv;
    private String tengv;
    public GiangVien(){}
    public GiangVien(int magv,String tengv){
        this.magv=magv;
        this.tengv=tengv;
    }

    public int getMagv() {
        return magv;
    }

    public void setMagv(int magv) {
        this.magv = magv;
    }

    public String getTengv() {
        return tengv;
    }

    public void setTengv(String tengv) {
        this.tengv = tengv;
    }
}
