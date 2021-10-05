package com.example.dnk_ad.doanhthu;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dnk_ad.MainActivity;
import com.example.dnk_ad.R;
import com.example.dnk_ad.config;
import com.example.dnk_ad.dao.DoanhThuDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentDoanhThu extends Fragment implements DoanhThuDAO {
    ViewPager viewPager;
    ArrayList<DoanhThuNam> doanhThuNams;
    public static ProgressDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_doanh_thu,null);
        viewPager = (ViewPager) view.findViewById(R.id.vpDoanhThu);
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
        getDoanhThuNam();
    }

    public void getDoanhThuNam(){
        doanhThuNams=new ArrayList<>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(config.URL_DoanhThu_GetNam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("jsonGetDoanhThuNam",response.toString());
                try {
                    if(response.getInt(config.ThanhCong)==1){
                        JSONArray list = (JSONArray) response.getJSONArray(config.Table_DoanhThu);
                        for(int i=0;i<list.length();i++){
                            DoanhThuNam x=new DoanhThuNam();
                            JSONObject jsonObject=(JSONObject)list.get(i);
                            x.setNam(jsonObject.getInt(config.Nam_DoanhThu));
                            x.setTong(jsonObject.getDouble(config.Tong_DoanhThu));
                            doanhThuNams.add(x);
                        }
                    }
                    else {
                        Toast.makeText(getContext(),response.getString(config.ThongBao),Toast.LENGTH_SHORT).show();
                    }
                    Log.d("size",doanhThuNams.size()+"");
                    sortYear();
                    viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
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
                Toast.makeText(getContext(),"Kết nối thất bại",Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }

    @Override
    public void getDoanhThuThang() {

    }

    public void sortYear() {
        Collections.sort(doanhThuNams,new Comparator<DoanhThuNam>() {
            @Override
            public int compare(DoanhThuNam o1, DoanhThuNam o2) {
                return o1.getNam()-o2.getNam();
            }
        });
    };

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position)
        {
            for(int i=0;i<doanhThuNams.size();i++){
                return new FragmentDoanhThuTheoNam(doanhThuNams.get(position).getNam(),doanhThuNams.get(position).getTong());
            }
            return null;
        }
        @Override
        public int getCount() {
            return doanhThuNams.size();
        }
    }
}
