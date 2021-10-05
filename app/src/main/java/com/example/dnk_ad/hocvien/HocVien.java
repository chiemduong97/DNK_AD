package com.example.dnk_ad.hocvien;

public class HocVien {
    private int mahv;
    private String tenhv;
    private String taikhoan;
    private String matkhau;
    private String email;
    private String sdt;
    private String diachi;
    private String avatar;
    private int trangthai;
    public HocVien(){}

    public HocVien(int mahv, String tenhv, String taikhoan, String matkhau, String email, String sdt, String diachi, String avatar, int trangthai) {
        this.mahv = mahv;
        this.tenhv = tenhv;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.email = email;
        this.sdt = sdt;
        this.diachi = diachi;
        this.avatar = avatar;
        this.trangthai = trangthai;
    }

    public int getMahv() {
        return mahv;
    }

    public void setMahv(int mahv) {
        this.mahv = mahv;
    }

    public String getTenhv() {
        return tenhv;
    }

    public void setTenhv(String tenhv) {
        this.tenhv = tenhv;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
