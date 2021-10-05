package com.example.dnk_ad.giangvien;

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

import java.util.ArrayList;

public class GiangVienAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    Context context;
    ArrayList<GiangVien> giangViens;
    ArrayList<GiangVien> giangViensOld;
    RecyclerView recyclerView;

    public GiangVienAdapter(Context context, ArrayList<GiangVien> giangViens, RecyclerView recyclerView){
        this.context=context;
        this.giangViens=giangViens;
        this.recyclerView=recyclerView;
        this.giangViensOld=giangViens;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch=constraint.toString();
                if(strSearch.isEmpty())
                    giangViens=giangViensOld;
                else {
                    ArrayList<GiangVien> arrayList=new ArrayList<>();
                    for(int i=0;i<giangViensOld.size();i++){
                        if(giangViensOld.get(i).getTengv().toLowerCase().contains(strSearch.toLowerCase()))
                            arrayList.add(giangViensOld.get(i));
                    }
                    giangViens=arrayList;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=giangViens;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                giangViens=(ArrayList<GiangVien>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tengv;
        public MyViewHolder(@NonNull View GiangVienView) {
            super(GiangVienView);
            tengv=GiangVienView.findViewById(R.id.ten_gv);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.giang_vien,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GiangVien giangVien=giangViens.get(position);
        ((MyViewHolder)holder).tengv.setText(giangVien.getTengv());
        ((MyViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ThongTinGiangVien.class);
                intent.putExtra(config.Ma_GV,giangVien.getMagv());
                intent.putExtra(config.Ten_GV,giangVien.getTengv());
                context.startActivity(intent);
            }
        });

    }


    
    @Override
    public int getItemCount() {
        return giangViens.size();
    }


}
