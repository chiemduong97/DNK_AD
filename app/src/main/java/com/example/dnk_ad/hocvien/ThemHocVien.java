package com.example.dnk_ad.hocvien;

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
import com.example.dnk_ad.dao.HocVienDAO;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThemHocVien extends AppCompatActivity implements HocVienDAO{
    Toolbar toolbar;
    Button btnLuu;
    ImageView back;
    EditText taikhoan,email;
    TextInputLayout inputLayoutTaiKhoan,inputLayoutEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoc_vien);
        toolbar=findViewById(R.id.toolbarThemHocVien);
        btnLuu=findViewById(R.id.btnLuuHV);
        back=findViewById(R.id.ivBack_AddHV);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        taikhoan=findViewById(R.id.etThemTaiKhoan);
        email=findViewById(R.id.etThemEmail);

        inputLayoutTaiKhoan=findViewById(R.id.textInputThemTaiKhoan);
        inputLayoutEmail=findViewById(R.id.textInputThemEmail);

        inputLayoutTaiKhoan.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    inputLayoutTaiKhoan.setError("Không được để trống");
                    inputLayoutTaiKhoan.setErrorEnabled(true);
                }
                else
                   inputLayoutTaiKhoan.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputLayoutEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    inputLayoutEmail.setError("Không được để trống");
                    inputLayoutEmail.setErrorEnabled(true);
                }
                else
                    inputLayoutEmail.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(taikhoan.getText().toString().equals("")||email.getText().toString().equals("")){
                        Toast.makeText(ThemHocVien.this,"Không được để trống",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        FragmentHocVien.pDialog.show();
                        add();
                    }
                }catch (Exception ex){
                    Toast.makeText(ThemHocVien.this,"Đăng ký thất bại!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getHocVien() {

    }

    @Override
    public void getAll() {

    }

    public void add(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_HV_Add, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonAddHV",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThemHocVien.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(ThemHocVien.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
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
                Toast.makeText(ThemHocVien.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.TaiKhoan_HV,taikhoan.getText().toString());
                params.put(config.Email_HV, email.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(ThemHocVien.this).add(stringRequest);
    }

    @Override
    public void update() {

    }

    @Override
    public void updateAnh() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void login() {

    }
}