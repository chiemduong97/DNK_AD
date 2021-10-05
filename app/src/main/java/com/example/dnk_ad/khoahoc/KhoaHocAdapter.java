package com.example.dnk_ad.khoahoc;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dnk_ad.R;
import com.example.dnk_ad.config;
import com.example.dnk_ad.lop.LopActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KhoaHocAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    Context context;
    ArrayList<KhoaHoc> khoaHocs;
    ArrayList<KhoaHoc> khoaHocsOld;
    RecyclerView recyclerView;

    public KhoaHocAdapter(Context context, ArrayList<KhoaHoc> khoaHocs, RecyclerView recyclerView){
        this.context=context;
        this.khoaHocs=khoaHocs;
        this.recyclerView=recyclerView;
        this.khoaHocsOld=khoaHocs;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch=constraint.toString();
                if(strSearch.isEmpty())
                    khoaHocs=khoaHocsOld;
                else {
                    ArrayList<KhoaHoc> arrayList=new ArrayList<>();
                    for(int i=0;i<khoaHocsOld.size();i++){
                        if(khoaHocsOld.get(i).getTenkh().toLowerCase().contains(strSearch.toLowerCase()))
                            arrayList.add(khoaHocsOld.get(i));
                    }
                    khoaHocs=arrayList;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=khoaHocs;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                khoaHocs=(ArrayList<KhoaHoc>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tenkh;
        TextView solop;
        public MyViewHolder(@NonNull View KhoaHocView) {
            super(KhoaHocView);
            tenkh=KhoaHocView.findViewById(R.id.kh_ten);
            solop=KhoaHocView.findViewById(R.id.kh_solop);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.khoa_hoc,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        KhoaHoc khoaHoc=khoaHocs.get(position);
        KhoaHocActivity.pDialog.show();
        ((MyViewHolder)holder).tenkh.setText(khoaHoc.getTenkh());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_KH_GetSoLop, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonGetSoLop",response.toString());
                try {
                    JSONObject jsonOb = new JSONObject(response.trim());
                    if (jsonOb.getInt(config.ThanhCong) == 1) {
                        JSONArray list = (JSONArray) jsonOb.getJSONArray(config.Table_KH);
                        JSONObject jsonObject=(JSONObject)list.get(0);
                        ((MyViewHolder)holder).solop.setText(jsonObject.getInt(config.SoLop_KH)+" lớp");
                        KhoaHocActivity.pDialog.dismiss();
                    }
                    else {
                        KhoaHocActivity.pDialog.dismiss();
                        Toast.makeText(context, jsonOb.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    KhoaHocActivity.pDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                KhoaHocActivity.pDialog.show();
                Toast.makeText(context, "Kết nối thất bại", Toast.LENGTH_LONG).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_KH,khoaHoc.getMakh()+"");
                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);

        ((MyViewHolder)holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String tenloai=((KhoaHocActivity)context).getIntent().getStringExtra(config.Ten_Loai);
                Intent intent=new Intent(context,ThongTinKhoaHoc.class);
                intent.putExtra(config.Ma_KH,khoaHoc.getMakh());
                intent.putExtra(config.Ten_KH,khoaHoc.getTenkh());
                intent.putExtra(config.Ma_Loai,khoaHoc.getMaloai());
                intent.putExtra(config.Ten_Loai,tenloai);
                context.startActivity(intent);
                return false;
            }
        });
        ((MyViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, LopActivity.class);
                intent.putExtra(config.Ma_KH,khoaHoc.getMakh());
                intent.putExtra(config.Ten_KH,khoaHoc.getTenkh());
                context.startActivity(intent);
            }
        });


    }


    
    @Override
    public int getItemCount() {
        return khoaHocs.size();
    }


}
