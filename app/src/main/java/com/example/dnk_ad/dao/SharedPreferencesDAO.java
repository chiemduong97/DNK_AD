package com.example.dnk_ad.dao;

import com.example.dnk_ad.hocvien.HocVien;

public interface SharedPreferencesDAO {
    public void save();
    public HocVien restore();
    public void delete();
}
