package com.example.dnk_ad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnk_ad.doanhthu.FragmentDoanhThu;
import com.example.dnk_ad.giangvien.FragmentGiangVien;
import com.example.dnk_ad.hocvien.FragmentHocVien;
import com.example.dnk_ad.loaikhoahoc.FragmentLoaiKhoaHoc;
import com.example.dnk_ad.phieudangky.FragmentPhieuDangKy;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static Toolbar toolbar;
    public static ImageView refresh;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        refresh=findViewById(R.id.ivRefresh);
        bottomNavigationView=findViewById(R.id.naviga);
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,new FragmentLoaiKhoaHoc()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_home){
                    fragmentManager=getSupportFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,new FragmentLoaiKhoaHoc()).commit();
                }
                else if(item.getItemId()==R.id.menu_phieu){
                    fragmentManager=getSupportFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,new FragmentPhieuDangKy()).commit();
                }
                else if(item.getItemId()==R.id.menu_hocvien){
                    fragmentManager=getSupportFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,new FragmentHocVien()).commit();
                }
                else if(item.getItemId()==R.id.menu_giangvien){
                    fragmentManager=getSupportFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,new FragmentGiangVien()).commit();
                }
                else if(item.getItemId()==R.id.menu_thongke){
                    fragmentManager=getSupportFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,new FragmentDoanhThu()).commit();
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_xac_nhan,null);
        TextView xacnhan=view.findViewById(R.id.tvXacNhan);
        xacnhan.setText("Bạn có chắc chắn muốn thoát không?");
        Button yes=view.findViewById(R.id.yes);
        Button no=view.findViewById(R.id.no);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog=builder.create();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finishAffinity();
                System.exit(0);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}