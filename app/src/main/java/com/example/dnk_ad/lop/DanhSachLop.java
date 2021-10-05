package com.example.dnk_ad.lop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.dnk_ad.dao.LopDAO;
import com.example.dnk_ad.hocvien.HocVien;
import com.example.dnk_ad.loaikhoahoc.ThongTinLoaiKhoaHoc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class DanhSachLop extends AppCompatActivity implements LopDAO {
    RecyclerView recyclerView;
    ArrayList<HocVien> hocViens;
    public static ProgressDialog pDialog;
    TenHocVienAdapter adapter;
    int malop;
    ImageView back;
    TextView tenlop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_lop);
        handleSSLHandshake();
        recyclerView=findViewById(R.id.recyclerViewDanhSachLop);
        back=findViewById(R.id.ivBack_DSLop);
        tenlop=findViewById(R.id.tv_TenLop_DSLop);
        tenlop.setText(getIntent().getStringExtra(config.Ten_Lop));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        malop=getIntent().getIntExtra(config.Ma_Lop,-1);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pDialog.show();
        getDSLop();
    }

    @Override
    public void getAll() {

    }

    @Override
    public void getAllTheoKH() {

    }

    @Override
    public void getAllTheoKHActive() {

    }

    @Override
    public void add() {

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
    public void getGV() {

    }

    @Override
    public void getAllGV() {

    }

    public void getDSLop(){
        hocViens=new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_Lop_GetDSLOP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonGetDSLop",response.toString());
                try {
                    JSONObject jsonOb=new JSONObject(response.trim());
                    if(jsonOb.getInt(config.ThanhCong)==1){
                        JSONArray list = (JSONArray) jsonOb.getJSONArray(config.Table_HV);
                        for(int i=0;i<list.length();i++){
                            HocVien x=new HocVien();
                            JSONObject jsonObject=(JSONObject)list.get(i);
                            x.setMahv(jsonObject.getInt(config.Ma_HV));
                            x.setTenhv(jsonObject.getString(config.Ten_HV));
                            x.setTaikhoan(jsonObject.getString(config.TaiKhoan_HV));
                            x.setMatkhau(jsonObject.getString(config.MatKhau_HV));
                            x.setEmail(jsonObject.getString(config.Email_HV));
                            x.setSdt(jsonObject.getString(config.SDT_HV));
                            x.setDiachi(jsonObject.getString(config.DiaChi_HV));
                            x.setAvatar(jsonObject.getString(config.Avatar_HV));
                            x.setTrangthai(jsonObject.getInt(config.TrangThai_HV));
                            hocViens.add(x);
                        }

                    }
                    else {
                        Toast.makeText(DanhSachLop.this,jsonOb.getString(config.ThongBao),Toast.LENGTH_SHORT).show();
                    }
                    adapter=new TenHocVienAdapter(DanhSachLop.this,hocViens,recyclerView);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DanhSachLop.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);
                    pDialog.dismiss();

                } catch (JSONException ex) {
                    pDialog.dismiss();
                    Log.d("Thất bại","");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(DanhSachLop.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_Lop,malop+"");
                return params;
            }
        };
        Volley.newRequestQueue(DanhSachLop.this).add(stringRequest);
    }



    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}