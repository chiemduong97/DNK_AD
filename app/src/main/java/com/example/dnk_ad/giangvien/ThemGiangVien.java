package com.example.dnk_ad.giangvien;

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
import com.example.dnk_ad.dao.GiangVienDAO;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThemGiangVien extends AppCompatActivity implements GiangVienDAO {
    Toolbar toolbar;
    Button btnLuu;
    ImageView back;
    EditText tengv;
    TextInputLayout inputLayoutTenGV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_giang_vien);
        toolbar=findViewById(R.id.toolbarThemGV);
        btnLuu=findViewById(R.id.btnLuuGV);
        back=findViewById(R.id.ivBack_AddGV);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tengv=findViewById(R.id.etThemTenGV);
        inputLayoutTenGV=findViewById(R.id.textInputThemTenGV);
        inputLayoutTenGV.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    inputLayoutTenGV.setError("Không được để trống");
                    inputLayoutTenGV.setErrorEnabled(true);
                }
                else
                    inputLayoutTenGV.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(tengv.getText().toString().equals("")){
                        Toast.makeText(ThemGiangVien.this,"Không được để trống",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        add();
                    }
                }catch (Exception ex){
                    Toast.makeText(ThemGiangVien.this,"Thêm thất bại!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getAll() {

    }

    public void add(){
        FragmentGiangVien.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_GV_Add, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonAddGV",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThemGiangVien.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(ThemGiangVien.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                    }
                    finish();
                    FragmentGiangVien.pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FragmentGiangVien.pDialog.dismiss();
                Toast.makeText(ThemGiangVien.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ten_GV,tengv.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(ThemGiangVien.this).add(stringRequest);
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}