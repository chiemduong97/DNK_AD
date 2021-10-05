package com.example.dnk_ad.doanhthu;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dnk_ad.R;
import com.example.dnk_ad.config;
import com.example.dnk_ad.dao.DoanhThuDAO;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

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

public class FragmentDoanhThuTheoNam extends Fragment implements DoanhThuDAO {
    int year;
    Double tongnam;
    BarChart barChart;
    TextView nam;
    TextView tong;
    ArrayList<DoanhThuThang> doanhThuThangs;
    ArrayList<DoanhThuThang> doanhThus;
    public FragmentDoanhThuTheoNam(int year,Double tongnam){
        this.year=year;
        this.tongnam=tongnam;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_doanh_thu_theo_nam,null);
        handleSSLHandshake();
        barChart=view.findViewById(R.id.barCharDoanhThu);
        nam=view.findViewById(R.id.tvNam);
        tong=view.findViewById(R.id.tvTong);
        getDoanhThuThang();

        return view;
    }


    @Override
    public void getDoanhThuNam() {

    }

    public void getDoanhThuThang(){
        doanhThuThangs=new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_DoanhThu_GetThang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonGetDoanhThuThang",response.toString());
                try {
                    JSONObject jsonOb=new JSONObject(response.trim());
                    if(jsonOb.getInt(config.ThanhCong)==1){
                        JSONArray list = (JSONArray) jsonOb.getJSONArray(config.Table_DoanhThu);
                        for(int i=0;i<list.length();i++){
                            DoanhThuThang x=new DoanhThuThang();
                            JSONObject jsonObject=(JSONObject)list.get(i);
                            x.setThang(jsonObject.getInt(config.Thang_DoanhThu));
                            x.setTong(jsonObject.getDouble(config.Tong_DoanhThu));
                            doanhThuThangs.add(x);
                        }
                    }
                    else {
                        Toast.makeText(getContext(),jsonOb.getString(config.ThongBao),Toast.LENGTH_SHORT).show();
                    }
                    doanhThus=new ArrayList<>();
                    for(int i=1;i<=12;i++){
                       doanhThus.add(new DoanhThuThang(i,0.0));
                    }
                    for(int i=0;i<doanhThus.size();i++){
                        for(int j=0;j<doanhThuThangs.size();j++){
                            if(doanhThuThangs.get(j).getThang()==doanhThus.get(i).getThang()){
                                doanhThus.set(i,doanhThuThangs.get(j));
                            }
                        }
                    }

                    nam.setText("#"+year);
                    tong.setText(tongnam+"");
                    ArrayList arrayList=new ArrayList();
                    for(int i=0;i<doanhThus.size();i++){
                        if(doanhThus.get(i).getTong()!=0.0){
                            int tong= (int) Math.round(doanhThus.get(i).getTong());
                            arrayList.add(new BarEntry(doanhThus.get(i).getThang(),tong));
                        }
                    }
                    BarDataSet barDataSet=new BarDataSet(arrayList,"Doanh thu tháng");
                    barDataSet.setColor(Color.parseColor("#FF0000"));

                    XAxis xAxis=barChart.getXAxis();
                    ArrayList<String> month=new ArrayList<>();
                    month.add("");
                    for(int i=0;i<doanhThus.size();i++){
                        if(doanhThus.get(i).getTong()==0.0){
                            month.add("");
                        }
                        else
                            month.add("Th "+doanhThus.get(i).getThang());
                    }
                    BarData barData=new BarData(barDataSet);
                    barChart.setData(barData);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(month));
                    barChart.getAxisLeft().setAxisMinimum(0);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1f);

                    xAxis.setCenterAxisLabels(false);
                    xAxis.setGranularityEnabled(true);
                    barChart.getXAxis().setAxisMinimum(0.3f);
                    barData.setBarWidth(0.3f);
                    barChart.getDescription().setEnabled(false);
                    barChart.animateY(1000);
                } catch (JSONException ex) {
                    Log.d("Thất bại","");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Nam_DoanhThu,year+"");
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
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
