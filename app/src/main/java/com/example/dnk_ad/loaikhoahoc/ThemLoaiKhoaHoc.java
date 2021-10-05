package com.example.dnk_ad.loaikhoahoc;

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
import com.example.dnk_ad.dao.LoaiKhoaHocDAO;
import com.example.dnk_ad.khoahoc.ThemKhoaHoc;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThemLoaiKhoaHoc extends AppCompatActivity implements LoaiKhoaHocDAO{
    Toolbar toolbar;
    Button btnLuu;
    ImageView back;
    EditText tenloai;
    TextInputLayout inputLayoutTenLoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_khoa_hoc);
        toolbar=findViewById(R.id.toolbarThemLoai);
        btnLuu=findViewById(R.id.btnLuuLoai);
        back=findViewById(R.id.ivBack_AddLoai);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tenloai=findViewById(R.id.etThemTenLoai);
        inputLayoutTenLoai=findViewById(R.id.textInputThemTenLoai);
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
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(tenloai.getText().toString().equals("")){
                        Toast.makeText(ThemLoaiKhoaHoc.this,"Không được để trống",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        add();
                    }
                }catch (Exception ex){
                    Toast.makeText(ThemLoaiKhoaHoc.this,"Thêm thất bại!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getAll() {

    }

    public void add(){
        FragmentLoaiKhoaHoc.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_Loai_Add, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonAddLoai",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThemLoaiKhoaHoc.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(ThemLoaiKhoaHoc.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
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
                Toast.makeText(ThemLoaiKhoaHoc.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ten_Loai,tenloai.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(ThemLoaiKhoaHoc.this).add(stringRequest);
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}