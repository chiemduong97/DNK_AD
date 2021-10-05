package com.example.dnk_ad.khoahoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dnk_ad.R;
import com.example.dnk_ad.config;
import com.example.dnk_ad.dao.KhoaHocDAO;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThemKhoaHoc extends AppCompatActivity implements KhoaHocDAO{
    Toolbar toolbar;
    Button btnLuu;
    ImageView back;
    EditText tenkh;
    TextInputLayout inputLayoutTenKH;
    int maloai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khoa_hoc);
        toolbar=findViewById(R.id.toolbarThemKH);
        btnLuu=findViewById(R.id.btnLuuKH);
        back=findViewById(R.id.ivBack_AddKH);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tenkh=findViewById(R.id.etThemTenKH);
        inputLayoutTenKH=findViewById(R.id.textInputThemTenKH);
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

        maloai=getIntent().getIntExtra(config.Ma_Loai,-1);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(tenkh.getText().toString().equals("")){
                        Toast.makeText(ThemKhoaHoc.this,"Không được để trống",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        add();
                    }
                }catch (Exception ex){
                    Toast.makeText(ThemKhoaHoc.this,"Thêm thất bại!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getAllTheoLoai() {

    }

    @Override
    public void getAll() {

    }

    public void add(){
        KhoaHocActivity.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_KH_Add, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonAddKhoaHoc",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThemKhoaHoc.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(ThemKhoaHoc.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
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
                Toast.makeText(ThemKhoaHoc.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ten_KH,tenkh.getText().toString());
                params.put(config.Ma_Loai,maloai+"");
                return params;
            }
        };
        Volley.newRequestQueue(ThemKhoaHoc.this).add(stringRequest);
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}