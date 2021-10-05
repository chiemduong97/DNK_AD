package com.example.dnk_ad.hocvien;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dnk_ad.MainActivity;
import com.example.dnk_ad.R;
import com.example.dnk_ad.config;
import com.example.dnk_ad.dao.HocVienDAO;
import com.example.dnk_ad.giangvien.ThongTinGiangVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class FragmentHocVien extends Fragment implements HocVienDAO{
    RecyclerView recyclerView;
    ArrayList<HocVien> hocViens;
    public static ProgressDialog pDialog;
    HocVienAdapter adapter;
    SearchView searchView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_hoc_vien,null);
        handleSSLHandshake();
        recyclerView=view.findViewById(R.id.recyclerViewHV);
        searchView=view.findViewById(R.id.TimKiemHV);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        FloatingActionButton floatingActionButton=view.findViewById(R.id.addHV);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ThemHocVien.class);
                startActivity(intent);
            }
        });
        Animation animation = AnimationUtils.loadAnimation(getContext().getApplicationContext(), R.anim.rorate);

        MainActivity.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.refresh.startAnimation(animation);
                onResume();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        pDialog.show();
        getAll();
    }

    @Override
    public void getHocVien() {

    }

    public void getAll(){
        hocViens=new ArrayList<>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(config.URL_HV_GetAll, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("getAllHV",response.toString());
                try {
                    if(response.getInt(config.ThanhCong)==1){
                        JSONArray list = (JSONArray) response.getJSONArray(config.Table_HV);
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
                        Toast.makeText(getContext(),response.getString(config.ThongBao),Toast.LENGTH_SHORT).show();
                    }
                    adapter=new HocVienAdapter(getContext(),hocViens,recyclerView);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
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
                Log.d("thất bại",error.toString());
                Toast.makeText(getContext(), "Kết nối thất bại", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();

            }
        });
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
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
    public void reset() {

    }

    @Override
    public void login() {

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
