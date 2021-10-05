package com.example.dnk_ad.hocvien;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dnk_ad.R;
import com.example.dnk_ad.config;
import com.example.dnk_ad.dao.HocVienDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinHocVien extends AppCompatActivity implements HocVienDAO {
    EditText taikhoan,tenhv,email,sdt,diachi;
    ToggleButton trangthai;
    Toolbar toolbar;
    ImageView back;
    Button btnLuu,btnXoa;
    TextView resetMatKhau;
    int tt;
    int mahv;
    String matkhau;
    String avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_hoc_vien);
        taikhoan=findViewById(R.id.etTaiKhoan_TThv);
        tenhv=findViewById(R.id.etHoTen_TThv);
        email=findViewById(R.id.etEmail_TThv);
        sdt=findViewById(R.id.etSDT_TThv);
        diachi=findViewById(R.id.etDiaChi_TThv);
        trangthai=findViewById(R.id.toggle_TThv);
        toolbar=findViewById(R.id.toolbarTThv);
        back=findViewById(R.id.ivBack_TThv);
        btnLuu=findViewById(R.id.btnLuu_TThv);
        btnXoa=findViewById(R.id.btnXoa_TThv);
        resetMatKhau=findViewById(R.id.tvResetMatKhau);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        taikhoan.setText(getIntent().getStringExtra(config.TaiKhoan_HV));
        tenhv.setText(getIntent().getStringExtra(config.Ten_HV));
        email.setText(getIntent().getStringExtra(config.Email_HV));
        sdt.setText(getIntent().getStringExtra(config.SDT_HV));
        diachi.setText(getIntent().getStringExtra(config.DiaChi_HV));
        tt=getIntent().getIntExtra(config.TrangThai_HV,-1);
        mahv=getIntent().getIntExtra(config.Ma_HV,-1);
        matkhau=getIntent().getStringExtra(config.MatKhau_HV);
        avatar=getIntent().getStringExtra(config.Avatar_HV);
        if(tt==1)
            trangthai.setChecked(true);
        else
            trangthai.setChecked(false);
        trangthai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                    tt=1;
                else
                    tt=0;
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ThongTinHocVien.this);
                View view= LayoutInflater.from(ThongTinHocVien.this).inflate(R.layout.dialog_xac_nhan,null);
                TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                xacnhan.setText("Bạn có thực sự muốn xóa");
                Button yes=view.findViewById(R.id.yes);
                Button no=view.findViewById(R.id.no);
                builder.setView(view);
                builder.setCancelable(false);
                AlertDialog dialog=builder.create();
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete();
                        dialog.dismiss();
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
        });
        resetMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ThongTinHocVien.this);
                View view= LayoutInflater.from(ThongTinHocVien.this).inflate(R.layout.dialog_xac_nhan,null);
                TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                xacnhan.setText("Bạn có muốn reset mật khẩu");
                Button yes=view.findViewById(R.id.yes);
                Button no=view.findViewById(R.id.no);
                builder.setView(view);
                builder.setCancelable(false);
                AlertDialog dialog=builder.create();
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reset();
                        dialog.dismiss();
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
        });
    }

    @Override
    public void getHocVien() {

    }

    @Override
    public void getAll() {

    }

    @Override
    public void add() {

    }

    public void update(){
        FragmentHocVien.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_HV_Update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonUpdateHV",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThongTinHocVien.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ThongTinHocVien.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                    }
                    finish();
                    FragmentHocVien.pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FragmentHocVien.pDialog.dismiss();
                Toast.makeText(ThongTinHocVien.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_HV,mahv+"");
                params.put(config.Ten_HV,tenhv.getText().toString());
                params.put(config.TaiKhoan_HV,taikhoan.getText().toString());
                params.put(config.MatKhau_HV,matkhau);
                params.put(config.Email_HV,email.getText().toString());
                params.put(config.SDT_HV,sdt.getText().toString());
                params.put(config.DiaChi_HV,diachi.getText().toString());
                params.put(config.TrangThai_HV,tt+"");
                return params;
            }
        };
        Volley.newRequestQueue(ThongTinHocVien.this).add(stringRequest);
    }

    @Override
    public void updateAnh() {

    }

    public void delete(){
        FragmentHocVien.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_HV_Delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonDeleteHV",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThongTinHocVien.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ThongTinHocVien.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                    }
                    finish();
                    FragmentHocVien.pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FragmentHocVien.pDialog.dismiss();
                Toast.makeText(ThongTinHocVien.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_HV,mahv+"");

                return params;
            }
        };
        Volley.newRequestQueue(ThongTinHocVien.this).add(stringRequest);
    }
    public void reset(){
        FragmentHocVien.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_HV_ReSet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonResetMatKhau",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThongTinHocVien.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ThongTinHocVien.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                    }
                    finish();
                    FragmentHocVien.pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FragmentHocVien.pDialog.dismiss();
                Toast.makeText(ThongTinHocVien.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_HV,mahv+"");

                return params;
            }
        };
        Volley.newRequestQueue(ThongTinHocVien.this).add(stringRequest);
    }

    @Override
    public void login() {

    }
}