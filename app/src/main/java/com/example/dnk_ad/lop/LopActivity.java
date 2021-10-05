package com.example.dnk_ad.lop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.dnk_ad.loaikhoahoc.ThongTinLoaiKhoaHoc;
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

public class LopActivity extends AppCompatActivity implements LopDAO{
    RecyclerView recyclerView;
    ArrayList<Lop> lops;
    public static ProgressDialog pDialog;
    LopAdapter adapter;
    SearchView searchView;
    int makh;
    Toolbar toolbar;
    ImageView back;
    TextView tenkh;
    TextView tvAll,tvActive;
    int flag=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lop);
        handleSSLHandshake();
        recyclerView=findViewById(R.id.recyclerViewLop);
        searchView=findViewById(R.id.TimKiemLop);
        toolbar=findViewById(R.id.toolbarLop);
        back=findViewById(R.id.ivBack_Lop);
        tenkh=findViewById(R.id.tv_TenKH_Lop);
        tvAll=findViewById(R.id.tvAllLop);
        tvActive=findViewById(R.id.tvActiveLop);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tenkh.setText(getIntent().getStringExtra(config.Ten_KH));
        makh=getIntent().getIntExtra(config.Ma_KH,-1);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();


        FloatingActionButton floatingActionButton=findViewById(R.id.addLop);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LopActivity.this, ThemLop.class);
                intent.putExtra(config.Ma_KH,makh);
                startActivity(intent);
            }
        });

        tvActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag!=1){
                    getAllTheoKHActive();
                    tvActive.setBackgroundColor(Color.RED);
                    tvAll.setBackgroundColor(Color.GRAY);
                    flag=1;
                }
            }
        });

        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag!=0){
                    getAllTheoKH();
                    tvAll.setBackgroundColor(Color.RED);
                    tvActive.setBackgroundColor(Color.GRAY);

                    flag=0;
                }
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        pDialog.show();
        getAllTheoKHActive();
        tvActive.setBackgroundColor(Color.RED);
        tvAll.setBackgroundColor(Color.GRAY);
        flag=1;
    }

    @Override
    public void getAll() {

    }

    public void getAllTheoKH(){
        lops=new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_Lop_GetAllTheoKH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonGetLopTheoKH",response.toString());
                try {
                    JSONObject jsonOb=new JSONObject(response.trim());
                    if(jsonOb.getInt(config.ThanhCong)==1){
                        JSONArray list = (JSONArray) jsonOb.getJSONArray(config.Table_Lop);
                        for(int i=0;i<list.length();i++){
                            Lop x=new Lop();
                            JSONObject jsonObject=(JSONObject)list.get(i);
                            x.setMalop(jsonObject.getInt(config.Ma_Lop));
                            x.setTenlop(jsonObject.getString(config.Ten_Lop));
                            x.setMota(jsonObject.getString(config.Mota_Lop));
                            x.setMakh(jsonObject.getInt(config.Ma_KH));
                            x.setMagv(jsonObject.getInt(config.Ma_GV));
                            x.setBatdau(jsonObject.getString(config.BD_Lop));
                            x.setKetthuc(jsonObject.getString(config.KT_Lop));
                            x.setCahoc(jsonObject.getString(config.CaHoc_Lop));
                            x.setAnhminhhoa(jsonObject.getString(config.Anh_Lop));
                            x.setDanhgia(jsonObject.getDouble(config.DanhGia_Lop));
                            x.setHocphi(jsonObject.getDouble(config.HocPhi_Lop));
                            lops.add(x);
                        }

                    }
                    else {
                        Toast.makeText(LopActivity.this,jsonOb.getString(config.ThongBao),Toast.LENGTH_SHORT).show();
                    }
                    adapter=new LopAdapter(LopActivity.this,lops,recyclerView);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(LopActivity.this);
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
                    Log.d("Thất bại","");
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(LopActivity.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_KH,makh+"");
                return params;
            }
        };
        Volley.newRequestQueue(LopActivity.this).add(stringRequest);
    }

    @Override
    public void getAllTheoKHActive() {
        lops=new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_Lop_GetAllTheoKHActive, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonGetLopTheoKH",response.toString());
                try {
                    JSONObject jsonOb=new JSONObject(response.trim());
                    if(jsonOb.getInt(config.ThanhCong)==1){
                        JSONArray list = (JSONArray) jsonOb.getJSONArray(config.Table_Lop);
                        for(int i=0;i<list.length();i++){
                            Lop x=new Lop();
                            JSONObject jsonObject=(JSONObject)list.get(i);
                            x.setMalop(jsonObject.getInt(config.Ma_Lop));
                            x.setTenlop(jsonObject.getString(config.Ten_Lop));
                            x.setMota(jsonObject.getString(config.Mota_Lop));
                            x.setMakh(jsonObject.getInt(config.Ma_KH));
                            x.setMagv(jsonObject.getInt(config.Ma_GV));
                            x.setBatdau(jsonObject.getString(config.BD_Lop));
                            x.setKetthuc(jsonObject.getString(config.KT_Lop));
                            x.setCahoc(jsonObject.getString(config.CaHoc_Lop));
                            x.setAnhminhhoa(jsonObject.getString(config.Anh_Lop));
                            x.setDanhgia(jsonObject.getDouble(config.DanhGia_Lop));
                            x.setHocphi(jsonObject.getDouble(config.HocPhi_Lop));
                            lops.add(x);
                        }

                    }
                    else {
                        Toast.makeText(LopActivity.this,jsonOb.getString(config.ThongBao),Toast.LENGTH_SHORT).show();
                    }
                    adapter=new LopAdapter(LopActivity.this,lops,recyclerView);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(LopActivity.this);
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
                    Log.d("Thất bại","");
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(LopActivity.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_KH,makh+"");
                return params;
            }
        };
        Volley.newRequestQueue(LopActivity.this).add(stringRequest);
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

    @Override
    public void getDSLop() {

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