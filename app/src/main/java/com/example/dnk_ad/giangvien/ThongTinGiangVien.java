package com.example.dnk_ad.giangvien;

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
import com.example.dnk_ad.dao.GiangVienDAO;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinGiangVien extends AppCompatActivity implements GiangVienDAO {
    EditText magv,tengv;
    Toolbar toolbar;
    ImageView back;
    Button btnLuu,btnXoa;
    TextInputLayout inputLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_giang_vien);
        magv=findViewById(R.id.etMaGV_TTGV);
        tengv=findViewById(R.id.etTenGV_TTGV);
        toolbar=findViewById(R.id.toolbarTTGV);
        back=findViewById(R.id.ivBack_TTGV);
        btnLuu=findViewById(R.id.btnLuu_TTGV);
        btnXoa=findViewById(R.id.btnXoa_TTGV);
        inputLayout=findViewById(R.id.textInputTenGV_TTGV);
        inputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    inputLayout.setError("Không được để trống");
                    inputLayout.setErrorEnabled(true);
                }
                else
                    inputLayout.setErrorEnabled(false);
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
        magv.setText(getIntent().getIntExtra(config.Ma_GV,-1)+"");
        tengv.setText(getIntent().getStringExtra(config.Ten_GV));
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ThongTinGiangVien.this);
                View view= LayoutInflater.from(ThongTinGiangVien.this).inflate(R.layout.dialog_xac_nhan,null);
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
    public void delete(){
        FragmentGiangVien.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_GV_Delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonDeleteGV",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThongTinGiangVien.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ThongTinGiangVien.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
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
                Toast.makeText(ThongTinGiangVien.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_GV,magv.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(ThongTinGiangVien.this).add(stringRequest);
    }

    @Override
    public void getAll() {

    }

    @Override
    public void add() {

    }

    public void update(){
        FragmentGiangVien.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_GV_Update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonUpdateGV",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThongTinGiangVien.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ThongTinGiangVien.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
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
                Toast.makeText(ThongTinGiangVien.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_GV,magv.getText().toString());
                params.put(config.Ten_GV,tengv.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(ThongTinGiangVien.this).add(stringRequest);
    }
}