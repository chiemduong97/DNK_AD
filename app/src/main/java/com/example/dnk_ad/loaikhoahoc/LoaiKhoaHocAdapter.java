package com.example.dnk_ad.loaikhoahoc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnk_ad.R;
import com.example.dnk_ad.config;
import com.example.dnk_ad.khoahoc.KhoaHocActivity;

import java.util.ArrayList;

public class LoaiKhoaHocAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    Context context;
    ArrayList<LoaiKhoaHoc> loaiKhoaHocs;
    ArrayList<LoaiKhoaHoc> loaiKhoaHocsOld;
    RecyclerView recyclerView;

    public LoaiKhoaHocAdapter(Context context, ArrayList<LoaiKhoaHoc> loaiKhoaHocs, RecyclerView recyclerView){
        this.context=context;
        this.loaiKhoaHocs=loaiKhoaHocs;
        this.recyclerView=recyclerView;
        this.loaiKhoaHocsOld=loaiKhoaHocs;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch=constraint.toString();
                if(strSearch.isEmpty())
                    loaiKhoaHocs=loaiKhoaHocsOld;
                else {
                    ArrayList<LoaiKhoaHoc> arrayList=new ArrayList<>();
                    for(int i=0;i<loaiKhoaHocsOld.size();i++){
                        if(loaiKhoaHocsOld.get(i).getTenloai().toLowerCase().contains(strSearch.toLowerCase()))
                            arrayList.add(loaiKhoaHocsOld.get(i));
                    }
                    loaiKhoaHocs=arrayList;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=loaiKhoaHocs;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                loaiKhoaHocs=(ArrayList<LoaiKhoaHoc>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tenloai;
        public MyViewHolder(@NonNull View LoaiKhoaHocView) {
            super(LoaiKhoaHocView);
            tenloai=LoaiKhoaHocView.findViewById(R.id.ten_loai);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.loai_khoa_hoc,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LoaiKhoaHoc loaiKhoaHoc=loaiKhoaHocs.get(position);
        ((MyViewHolder)holder).tenloai.setText(loaiKhoaHoc.getTenloai());
        ((MyViewHolder)holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent(context,ThongTinLoaiKhoaHoc.class);
                intent.putExtra(config.Ma_Loai,loaiKhoaHoc.getMaloai());
                intent.putExtra(config.Ten_Loai,loaiKhoaHoc.getTenloai());
                context.startActivity(intent);
                return false;
            }
        });
        ((MyViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, KhoaHocActivity.class);
                intent.putExtra(config.Ma_Loai,loaiKhoaHoc.getMaloai());
                intent.putExtra(config.Ten_Loai,loaiKhoaHoc.getTenloai());
                context.startActivity(intent);
            }
        });

    }


    
    @Override
    public int getItemCount() {
        return loaiKhoaHocs.size();
    }


}
