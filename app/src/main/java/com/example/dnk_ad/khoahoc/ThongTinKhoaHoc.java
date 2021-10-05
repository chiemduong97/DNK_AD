package com.example.dnk_ad.khoahoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dnk_ad.R;
import com.example.dnk_ad.config;
import com.example.dnk_ad.dao.KhoaHocDAO;
import com.example.dnk_ad.dao.LoaiKhoaHocDAO;
import com.example.dnk_ad.hocvien.FragmentHocVien;
import com.example.dnk_ad.loaikhoahoc.LoaiKhoaHoc;
import com.example.dnk_ad.loaikhoahoc.ThongTinLoaiKhoaHoc;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhoaHoc extends AppCompatActivity implements KhoaHocDAO {
    EditText makh,tenkh,tenloai;
    Toolbar toolbar;
    ImageView back;
    Button btnLuu,btnXoa;
    TextInputLayout inputLayoutTenKH;
    int maloai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khoa_hoc);
        makh=findViewById(R.id.etMaKH_TTKH);
        tenkh=findViewById(R.id.etTenKH_TTKH);
        tenloai=findViewById(R.id.etTenLoai_TTKH);
        toolbar=findViewById(R.id.toolbarTTKH);
        back=findViewById(R.id.ivBack_TTKH);
        btnLuu=findViewById(R.id.btnLuu_TTKH);
        btnXoa=findViewById(R.id.btnXoa_TTKH);
        inputLayoutTenKH=findViewById(R.id.textInputTenKH_TTKH);
        maloai=getIntent().getIntExtra(config.Ma_Loai,-1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        inputLayoutTenKH.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    inputLayoutTenKH.setError("Không được để trống");
                    inputLayoutTenKH.setErrorEnabled(true);
                }
                else
                    inputLayoutTenKH.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        makh.setText(getIntent().getIntExtra(config.Ma_KH,-1)+"");
        tenkh.setText(getIntent().getStringExtra(config.Ten_KH));
        tenloai.setText(getIntent().getStringExtra(config.Ten_Loai));
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ThongTinKhoaHoc.this);
                View view= LayoutInflater.from(ThongTinKhoaHoc.this).inflate(R.layout.dialog_xac_nhan,null);
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
    }

    @Override
    public void getAllTheoLoai() {

    }

    @Override
    public void getAll() {

    }

    @Override
    public void add() {

    }

    public void update(){
        KhoaHocActivity.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_KH_Update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonUpdateKhoaHoc",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThongTinKhoaHoc.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ThongTinKhoaHoc.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                    }
                    finish();
                    KhoaHocActivity.pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                KhoaHocActivity.pDialog.dismiss();
                Toast.makeText(ThongTinKhoaHoc.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_KH,makh.getText().toString());
                params.put(config.Ten_KH,tenkh.getText().toString());
                params.put(config.Ma_Loai,maloai+"");
                return params;
            }
        };
        Volley.newRequestQueue(ThongTinKhoaHoc.this).add(stringRequest);
    }
    public void delete(){
        KhoaHocActivity.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_KH_Delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonDeleteKhoaHoc",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThongTinKhoaHoc.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ThongTinKhoaHoc.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                    }
                    finish();
                    KhoaHocActivity.pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FragmentHocVien.pDialog.dismiss();
                Toast.makeText(ThongTinKhoaHoc.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_KH,makh.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(ThongTinKhoaHoc.this).add(stringRequest);
    }
}