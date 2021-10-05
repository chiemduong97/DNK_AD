package com.example.dnk_ad.hocvien;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

public class HocVienAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    Context context;
    ArrayList<HocVien> hocViens;
    ArrayList<HocVien> hocViensOld;
    RecyclerView recyclerView;

    public HocVienAdapter(Context context, ArrayList<HocVien> hocViens, RecyclerView recyclerView){
        this.context=context;
        this.hocViens=hocViens;
        this.recyclerView=recyclerView;
        this.hocViensOld=hocViens;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch=constraint.toString();
                if(strSearch.isEmpty())
                    hocViens=hocViensOld;
                else {
                    ArrayList<HocVien> arrayList=new ArrayList<>();
                    for(int i=0;i<hocViensOld.size();i++){
                        if(hocViensOld.get(i).getTaikhoan().toLowerCase().contains(strSearch.toLowerCase()))
                            arrayList.add(hocViensOld.get(i));
                    }
                    hocViens=arrayList;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=hocViens;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                hocViens=(ArrayList<HocVien>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taikhoan;
        TextView trangthai;
        public MyViewHolder(@NonNull View HocVienView) {
            super(HocVienView);
            taikhoan=HocVienView.findViewById(R.id.hv_taikhoan);
            trangthai=HocVienView.findViewById(R.id.hv_trangthai);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.hoc_vien,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HocVien hocVien=hocViens.get(position);
        ((MyViewHolder)holder).taikhoan.setText(hocVien.getTaikhoan()+"");
        if(hocVien.getTrangthai()==1){
            ((MyViewHolder)holder).trangthai.setTextColor(Color.RED);
            ((MyViewHolder)holder).trangthai.setText("Active");
        }
        else{
            ((MyViewHolder)holder).trangthai.setTextColor(Color.GRAY);
            ((MyViewHolder)holder).trangthai.setText("DeActive");
        }
        ((MyViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ThongTinHocVien.class);
                intent.putExtra(config.Ma_HV,hocVien.getMahv());
                intent.putExtra(config.TaiKhoan_HV,hocVien.getTaikhoan());
                intent.putExtra(config.MatKhau_HV,hocVien.getMatkhau());
                intent.putExtra(config.Ten_HV,hocVien.getTenhv());
                intent.putExtra(config.Email_HV,hocVien.getEmail());
                intent.putExtra(config.SDT_HV,hocVien.getSdt());
                intent.putExtra(config.DiaChi_HV,hocVien.getDiachi());
                intent.putExtra(config.Avatar_HV,hocVien.getAvatar());
                intent.putExtra(config.TrangThai_HV,hocVien.getTrangthai());
                context.startActivity(intent);
            }
        });

    }


    
    @Override
    public int getItemCount() {
        return hocViens.size();
    }


}
