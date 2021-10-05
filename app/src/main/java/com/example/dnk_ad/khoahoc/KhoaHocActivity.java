package com.example.dnk_ad.khoahoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.dnk_ad.dao.KhoaHocDAO;
import com.example.dnk_ad.giangvien.ThongTinGiangVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

public class KhoaHocActivity extends AppCompatActivity implements KhoaHocDAO{
    RecyclerView recyclerView;
    ArrayList<KhoaHoc> khoaHocs;
    public static ProgressDialog pDialog;
    KhoaHocAdapter adapter;
    SearchView searchView;
    int maloai;
    Toolbar toolbar;
    ImageView back;
    TextView tenloai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoa_hoc);
        handleSSLHandshake();
        recyclerView=findViewById(R.id.recyclerViewKH);
        searchView=findViewById(R.id.TimKiemKH);
        toolbar=findViewById(R.id.toolbarKhoaHoc);
        back=findViewById(R.id.ivBack_KH);
        tenloai=findViewById(R.id.tv_TenLoaiKH_KH);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tenloai.setText(getIntent().getStringExtra(config.Ten_Loai));
        maloai=getIntent().getIntExtra(config.Ma_Loai,-1);


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();


        FloatingActionButton floatingActionButton=findViewById(R.id.addKH);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(KhoaHocActivity.this, ThemKhoaHoc.class);
                intent.putExtra(config.Ma_Loai,maloai);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        pDialog.show();
        getAllTheoLoai();
    }

    public void getAllTheoLoai(){
        khoaHocs=new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_KH_GetAllTheoLoai, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonGetKHTheoLoai",response.toString());
                try {
                    JSONObject jsonOb=new JSONObject(response.trim());
                    if(jsonOb.getInt(config.ThanhCong)==1){
                        JSONArray list = (JSONArray) jsonOb.getJSONArray(config.Table_KH);
                        for(int i=0;i<list.length();i++){
                            KhoaHoc x=new KhoaHoc();
                            JSONObject jsonObject=(JSONObject)list.get(i);
                            x.setMakh(jsonObject.getInt(config.Ma_KH));
                            x.setTenkh(jsonObject.getString(config.Ten_KH));
                            x.setMaloai(jsonObject.getInt(config.Ma_Loai));
                            khoaHocs.add(x);
                        }

                    }
                    else {
                        Toast.makeText(KhoaHocActivity.this,jsonOb.getString(config.ThongBao),Toast.LENGTH_SHORT).show();
                    }
                    adapter=new KhoaHocAdapter(KhoaHocActivity.this,khoaHocs,recyclerView);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(KhoaHocActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);
                    pDialog.dismiss();
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            adapter.getFilter().filter(query);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            adapter.getFilter().filter(newText);
                            return false;
                        }
                    });

                } catch (JSONException ex) {
                    pDialog.dismiss();
                    Log.d("Thất bại","");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(KhoaHocActivity.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_Loai,maloai+"");
                return params;
            }
        };
        Volley.newRequestQueue(KhoaHocActivity.this).add(stringRequest);
    }

    @Override
    public void getAll() {

    }

    @Override
    public void add() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

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