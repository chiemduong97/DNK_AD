package com.example.dnk_ad.phieudangky;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.example.dnk_ad.dao.PhieuDangKyDAO;
import com.example.dnk_ad.lop.ThongTinLop;

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

public class FragmentPhieuDangKy extends Fragment implements PhieuDangKyDAO {
    RecyclerView recyclerView;
    ArrayList<PhieuDangKy> phieuDangKIES;
    public static ProgressDialog pDialog;
    PhieuDangKyAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_phieu_dang_ky,null);
        handleSSLHandshake();
        recyclerView=view.findViewById(R.id.recyclerViewPhieuDK);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
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
        getAllDangCho();
    }

    @Override
    public void getAll() {

    }

    public void getAllDangCho(){
        phieuDangKIES=new ArrayList<>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(config.URL_PhieuDK_GetAllWait, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("jsonGetAllPDK",response.toString());
                try {
                    if(response.getInt(config.ThanhCong)==1){
                        JSONArray list = (JSONArray) response.getJSONArray(config.Table_PhieuDK);
                        for(int i=0;i<list.length();i++){
                            PhieuDangKy x=new PhieuDangKy();
                            JSONObject jsonObject=(JSONObject)list.get(i);
                            x.setMapdk(jsonObject.getInt(config.Ma_PhieuDK));
                            x.setMahv(jsonObject.getInt(config.Ma_HV));
                            x.setMalop(jsonObject.getInt(config.Ma_Lop));
                            x.setCahoc(jsonObject.getString(config.CaHoc_Lop));
                            phieuDangKIES.add(x);
                        }

                    }
                    else {
                        Toast.makeText(getContext(),response.getString(config.ThongBao),Toast.LENGTH_SHORT).show();
                    }
                    adapter=new PhieuDangKyAdapter(getContext(),phieuDangKIES,recyclerView);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
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
                Log.d("thất bại",error.toString());
                pDialog.dismiss();
                Toast.makeText(getContext(), "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }

    @Override
    public void getAllDaXacNhanTheoHV() {

    }

    @Override
    public void getAllDangChoTheoHV() {

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
