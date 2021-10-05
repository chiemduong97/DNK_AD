package com.example.dnk_ad.lop;

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
import com.example.dnk_ad.khoahoc.KhoaHocActivity;
import com.example.dnk_ad.loaikhoahoc.ThongTinLoaiKhoaHoc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    Context context;
    ArrayList<Lop> lops;
    ArrayList<Lop> lopsOld;
    RecyclerView recyclerView;

    public LopAdapter(Context context, ArrayList<Lop> lops, RecyclerView recyclerView){
        this.context=context;
        this.lops=lops;
        this.recyclerView=recyclerView;
        this.lopsOld=lops;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch=constraint.toString();
                if(strSearch.isEmpty())
                    lops=lopsOld;
                else {
                    ArrayList<Lop> arrayList=new ArrayList<>();
                    for(int i=0;i<lopsOld.size();i++){
                        if(lopsOld.get(i).getTenlop().toLowerCase().contains(strSearch.toLowerCase()))
                            arrayList.add(lopsOld.get(i));
                    }
                    lops=arrayList;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=lops;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                lops=(ArrayList<Lop>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tenlop;
        TextView sohv;

        public MyViewHolder(@NonNull View LopView) {
            super(LopView);
            tenlop=LopView.findViewById(R.id.lop_ten);
            sohv=LopView.findViewById(R.id.lop_sohv);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.lop,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Lop lop=lops.get(position);
        LopActivity.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_Lop_GetSoHV, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonGetSoHV",response.toString());
                try {
                    JSONObject jsonOb = new JSONObject(response.trim());
                    if (jsonOb.getInt(config.ThanhCong) == 1) {
                        JSONArray list = (JSONArray) jsonOb.getJSONArray(config.Table_Lop);
                        JSONObject jsonObject=(JSONObject)list.get(0);
                        ((MyViewHolder)holder).sohv.setText(jsonObject.getInt(config.SoHV_Lop)+" học viên");
                        LopActivity.pDialog.dismiss();
                    }
                    else {
                        LopActivity.pDialog.dismiss();
                        Toast.makeText(context, jsonOb.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    LopActivity.pDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LopActivity.pDialog.dismiss();
                Toast.makeText(context, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_Lop,lop.getMalop()+"");
                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);
        ((MyViewHolder)holder).tenlop.setText(lop.getTenlop());
        ((MyViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenkh=((LopActivity)context).getIntent().getStringExtra(config.Ten_KH);
                Intent intent=new Intent(context, ThongTinLop.class);
                intent.putExtra(config.Ma_Lop,lop.getMalop());
                intent.putExtra(config.Ten_Lop,lop.getTenlop());
                intent.putExtra(config.Mota_Lop,lop.getMota());
                intent.putExtra(config.Ma_KH,lop.getMakh());
                intent.putExtra(config.Ma_GV,lop.getMagv());
                intent.putExtra(config.BD_Lop,lop.getBatdau());
                intent.putExtra(config.KT_Lop,lop.getKetthuc());
                intent.putExtra(config.CaHoc_Lop,lop.getCahoc());
                intent.putExtra(config.Anh_Lop,lop.getAnhminhhoa());
                intent.putExtra(config.DanhGia_Lop,lop.getDanhgia());
                intent.putExtra(config.HocPhi_Lop,lop.getHocphi());
                intent.putExtra(config.Ten_KH,tenkh);
                context.startActivity(intent);
            }
        });


    }


    
    @Override
    public int getItemCount() {
        return lops.size();
    }


}
