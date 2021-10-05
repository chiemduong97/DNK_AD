package com.example.dnk_ad.lop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnk_ad.R;
import com.example.dnk_ad.hocvien.HocVien;

import java.util.ArrayList;

public class TenHocVienAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    ArrayList<HocVien> hocViens;
    RecyclerView recyclerView;

    public TenHocVienAdapter(Context context, ArrayList<HocVien> hocViens, RecyclerView recyclerView){
        this.context=context;
        this.hocViens=hocViens;
        this.recyclerView=recyclerView;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tenhv;
        public MyViewHolder(@NonNull View HocVienView) {
            super(HocVienView);
            tenhv=HocVienView.findViewById(R.id.ivlop_tenhv);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ten_hoc_vien,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HocVien hocVien=hocViens.get(position);
        ((MyViewHolder)holder).tenhv.setText(hocVien.getTenhv()+"");
    }


    
    @Override
    public int getItemCount() {
        return hocViens.size();
    }


}
