package com.example.dnk_ad;

import java.util.ArrayList;
import java.util.Arrays;

public class config {
    // https://192.168.1.4/dnk/
    // https://dcd-da1.000webhostapp.com/id17333467_dnk/
    public static final String Localhost="https://dcd-da1.000webhostapp.com/id17333467_dnk/";


    public static final String ThanhCong="thanhcong";
    public static final String ThongBao="thongbao";

    //hoc vien
    public static final String Table_HV="hocvien";
    public static final String Ma_HV="mahv";
    public static final String Ten_HV="tenhv";
    public static final String TaiKhoan_HV="taikhoan";
    public static final String MatKhau_HV="matkhau";
    public static final String Email_HV="email";
    public static final String SDT_HV="sdt";
    public static final String DiaChi_HV="diachi";
    public static final String Avatar_HV="avatar";
    public static final String TrangThai_HV="trangthai";
    public static final String URL_HV_GetAll=Localhost + "HocVienDAO/getAllHocVien.php";
    public static final String URL_HV_Get=Localhost + "HocVienDAO/getHocVien.php";
    public static final String URL_HV_Add=Localhost + "HocVienDAO/addHocVien.php";
    public static final String URL_HV_Update=Localhost + "HocVienDAO/updateHocVien.php";
    public static final String URL_HV_UpdateAvatar=Localhost + "HocVienDAO/updateAvatar.php";
    public static final String URL_HV_Delete=Localhost + "HocVienDAO/deleteHocVien.php";
    public static final String URL_HV_ReSet=Localhost + "HocVienDAO/resetMatKhau.php";

    //giang vien
    public static final String Table_GV="giangvien";
    public static final String Ma_GV="magv";
    public static final String Ten_GV="tengv";
    public static final String URL_GV_Get=Localhost + "GiangVienDAO/getGiangVien.php";
    public static final String URL_GV_GetAll=Localhost + "GiangVienDAO/getAllGiangVien.php";
    public static final String URL_GV_Add=Localhost + "GiangVienDAO/addGiangVien.php";
    public static final String URL_GV_Update=Localhost + "GiangVienDAO/updateGiangVien.php";
    public static final String URL_GV_Delete=Localhost + "GiangVienDAO/deleteGiangVien.php";

    //loai khoa hoc
    public static final String Table_Loai="loaikhoahoc";
    public static final String Ma_Loai="maloai";
    public static final String Ten_Loai="tenloai";
    public static final String URL_Loai_GetAll=Localhost + "LoaiKhoaHocDAO/getAllLoai.php";
    public static final String URL_Loai_Add=Localhost + "LoaiKhoaHocDAO/addLoai.php";
    public static final String URL_Loai_Update=Localhost + "LoaiKhoaHocDAO/updateLoai.php";
    public static final String URL_Loai_Delete=Localhost + "LoaiKhoaHocDAO/deleteLoai.php";

    //khoa hoc
    public static final String Table_KH="khoahoc";
    public static final String Ma_KH="makh";
    public static final String Ten_KH="tenkh";
    public static final String SoLop_KH="solop";
    public static final String URL_KH_GetAll=Localhost + "KhoaHocDAO/getAllKhoaHoc.php";
    public static final String URL_KH_GetAllTheoLoai=Localhost + "KhoaHocDAO/getAllKhoaHocTheoLoai.php";
    public static final String URL_KH_Add=Localhost + "KhoaHocDAO/addKhoaHoc.php";
    public static final String URL_KH_Update=Localhost + "KhoaHocDAO/updateKhoaHoc.php";
    public static final String URL_KH_Delete=Localhost + "KhoaHocDAO/deleteKhoaHoc.php";
    public static final String URL_KH_GetSoLop=Localhost + "KhoaHocDAO/getSoLop.php";

    //lop
    public static final String Table_Lop="lop";
    public static final String Ma_Lop="malop";
    public static final String Ten_Lop="tenlop";
    public static final String Mota_Lop="mota";
    public static final String BD_Lop="batdau";
    public static final String KT_Lop="ketthuc";
    public static final String CaHoc_Lop="cahoc";
    public static final String Anh_Lop="anhminhhoa";
    public static final String DanhGia_Lop="danhgia";
    public static final String HocPhi_Lop="hocphi";
    public static final String SoHV_Lop="sohv";
    public static final String URL_Lop_GetAll=Localhost + "LopDAO/getAllLop.php";
    public static final String URL_Lop_Get=Localhost + "LopDAO/getLop.php";
    public static final String URL_Lop_GetAllTheoKH=Localhost + "LopDAO/getAllLopTheoKH.php";
    public static final String URL_Lop_GetAllTheoKHActive=Localhost + "LopDAO/getAllLopTheoKHActive.php";
    public static final String URL_Lop_Add=Localhost + "LopDAO/addLop.php";
    public static final String URL_Lop_Update=Localhost + "LopDAO/updateLop.php";
    public static final String URL_Lop_UpdateAnh=Localhost + "LopDAO/updateAnh.php";
    public static final String URL_Lop_Delete=Localhost + "LopDAO/deleteLop.php";
    public static final String URL_Lop_GetSoHV=Localhost + "LopDAO/getSoHV.php";
    public static final String URL_Lop_GetDSLOP=Localhost + "LopDAO/getDSLop.php";
    public static final String URL_Lop_AnhMinhHoa=Localhost + "LopDAO/anhminhhoa/";


    //phieu dang ky
    public static final String Table_PhieuDK="phieudangky";
    public static final String Ma_PhieuDK="mapdk";
    public static final String TrangThai_PhieuDK="trangthai";
    public static final String NgayDongHP_PhieuDK="ngaydonghocphi";
    public static final String TienDong_PhieuDK="tiendong";
    public static final String URL_PhieuDK_Add=Localhost + "PhieuDangKyDAO/addPhieuDK.php";
    public static final String URL_PhieuDK_GetAllWait=Localhost + "PhieuDangKyDAO/getAllPhieuChoXuLy.php";
    public static final String URL_PhieuDK_GetAllDaXacNhanTheoHV=Localhost + "PhieuDangKyDAO/getAllPhieuDaXacNhanTheoHV.php";
    public static final String URL_PhieuDK_GetAllDangChoTheoHV=Localhost + "PhieuDangKyDAO/getAllPhieuDangChoTheoHV.php";
    public static final String URL_PhieuDK_Update=Localhost + "PhieuDangKyDAO/updatePhieuDK.php";
    public static final String URL_PhieuDK_Delete=Localhost + "PhieuDangKyDAO/deletePhieuDK.php";

    //doanh thu
    public static final String Table_DoanhThu="doanhthu";
    public static final String Nam_DoanhThu="nam";
    public static final String Thang_DoanhThu="thang";
    public static final String Tong_DoanhThu="tong";
    public static final String URL_DoanhThu_GetNam=Localhost + "DoanhThuDAO/getDoanhThuNam.php";
    public static final String URL_DoanhThu_GetThang=Localhost + "DoanhThuDAO/getDoanhThuThang.php";


    //ca hoc
    public static final ArrayList<String> CaHoc= new ArrayList<>(Arrays.asList("7h30 - 9h30","9h45 - 11h45","13h30 - 15h30","15h45 - 17h45"));

    //phieu danh gia
    public static final String Table_PhieuDG="phieudanhgia";
    public static final String Ma_PhieuDG="mapdg";
    public static final String Diem_PhieuDG="diem";
    public static final String NgayDG_PhieuDG="ngaydg";
    public static final String LuotThich_PhieuDG="luotthich";
    public static final String BinhLuan_PhieuDG="binhluan";
    public static final String URL_PhieuDG_GetAll=Localhost + "PhieuDanhGiaDAO/getAllPhieuDanhGia.php";
    public static final String URL_PhieuDG_GetAllTheoLop=Localhost + "PhieuDanhGiaDAO/getAllPhieuDanhGiaTheoLop.php";
    public static final String URL_PhieuDG_Add=Localhost + "PhieuDanhGiaDAO/addPhieuDanhGia.php";
    public static final String URL_PhieuDG_UpdateDG=Localhost + "PhieuDanhGiaDAO/updateDanhGia.php";
    public static final String URL_PhieuDG_UpdateLuotThich=Localhost + "PhieuDanhGiaDAO/updateLuotThich.php";

    //thich
    public static final String Table_Thich="thich";
    public static final String TrangThai_Thich="trangthai";
    public static final String URL_Thich_GetAll=Localhost + "ThichDAO/getAllThich.php";
}
