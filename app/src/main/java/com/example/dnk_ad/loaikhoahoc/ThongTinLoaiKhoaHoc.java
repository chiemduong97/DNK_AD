package com.example.dnk_ad.loaikhoahoc;

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
import com.example.dnk_ad.dao.LoaiKhoaHocDAO;
import com.example.dnk_ad.khoahoc.ThemKhoaHoc;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinLoaiKhoaHoc extends AppCompatActivity implements LoaiKhoaHocDAO{
    EditText maloai,tenloai;
    Toolbar toolbar;
    ImageView back;
    Button btnLuu,btnXoa;
    TextInputLayout inputLayoutTenLoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_loai_khoa_hoc);
        maloai=findViewById(R.id.etMaLoai_TTLoai);
        tenloai=findViewById(R.id.etTenLoai_TTLoai);
        toolbar=findViewById(R.id.toolbarTTLoai);
        back=findViewById(R.id.ivBack_TTLoai);
        btnLuu=findViewById(R.id.btnLuu_TTLoai);
        btnXoa=findViewById(R.id.btnXoa_TTLoai);
        inputLayoutTenLoai=findViewById(R.id.textInputTenLoai_TTLoai);
        inputLayoutTenLoai.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    inputLayoutTenLoai.setError("Không được để trống");
                    inputLayoutTenLoai.setErrorEnabled(true);
                }
                else
                    inputLayoutTenLoai.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        maloai.setText(getIntent().getIntExtra(config.Ma_Loai,-1)+"");
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
                AlertDialog.Builder builder=new AlertDialog.Builder(ThongTinLoaiKhoaHoc.this);
                View view= LayoutInflater.from(ThongTinLoaiKhoaHoc.this).inflate(R.layout.dialog_xac_nhan,null);
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
    public void getAll() {

    }

    @Override
    public void add() {

    }

    public void update(){
        FragmentLoaiKhoaHoc.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_Loai_Update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonUpdateLoai",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThongTinLoaiKhoaHoc.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ThongTinLoaiKhoaHoc.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                    }
                    finish();
                    FragmentLoaiKhoaHoc.pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FragmentLoaiKhoaHoc.pDialog.dismiss();
                Toast.makeText(ThongTinLoaiKhoaHoc.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_Loai,maloai.getText().toString());
                params.put(config.Ten_Loai,tenloai.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(ThongTinLoaiKhoaHoc.this).add(stringRequest);
    }
    public void delete(){
        FragmentLoaiKhoaHoc.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_Loai_Delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonDeleteLoai",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThongTinLoaiKhoaHoc.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ThongTinLoaiKhoaHoc.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                    }
                    finish();
                    FragmentLoaiKhoaHoc.pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FragmentLoaiKhoaHoc.pDialog.dismiss();
                Toast.makeText(ThongTinLoaiKhoaHoc.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_Loai,maloai.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(ThongTinLoaiKhoaHoc.this).add(stringRequest);
    }
}