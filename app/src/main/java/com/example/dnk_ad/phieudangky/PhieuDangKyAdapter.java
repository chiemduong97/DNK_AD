package com.example.dnk_ad.phieudangky;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dnk_ad.R;
import com.example.dnk_ad.config;
import com.example.dnk_ad.dao.PhieuDangKyDAO;
import com.example.dnk_ad.hocvien.HocVien;
import com.example.dnk_ad.lop.Lop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PhieuDangKyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements PhieuDangKyDAO {
    Context context;
    ArrayList<PhieuDangKy> phieuDangKIES;
    RecyclerView recyclerView;
    
    public PhieuDangKyAdapter(Context context,ArrayList<PhieuDangKy> phieuDangKIES,RecyclerView recyclerView){
        this.context=context;
        this.phieuDangKIES=phieuDangKIES;
        this.recyclerView=recyclerView;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mapdk;
        EditText taikhoan,tenlop,hocphi;
        SeekBar seekBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mapdk=itemView.findViewById(R.id.tvPhieuDK_Ma);
            taikhoan=itemView.findViewById(R.id.etPhieuDK_HV);
            tenlop=itemView.findViewById(R.id.etPhieuDK_Lop);
            hocphi=itemView.findViewById(R.id.etPhieuDK_HocPhi);
            seekBar=itemView.findViewById(R.id.seekbarPhieuDK);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.phieu_dang_ky,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PhieuDangKy phieuDangKy=phieuDangKIES.get(position);
        StringRequest stringRequestHocVien=new StringRequest(Request.Method.POST, config.URL_HV_Get, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonGetHocVien",response.toString());
                try {
                    JSONObject jsonOb=new JSONObject(response.trim());
                    if(jsonOb.getInt(config.ThanhCong)==1){
                        JSONArray list = (JSONArray) jsonOb.getJSONArray(config.Table_HV);
                        HocVien hocVien=new HocVien();
                        JSONObject jsonObject=(JSONObject)list.get(0);
                        hocVien.setMahv(jsonObject.getInt(config.Ma_HV));
                        hocVien.setTenhv(jsonObject.getString(config.Ten_HV));
                        hocVien.setTaikhoan(jsonObject.getString(config.TaiKhoan_HV));
                        hocVien.setMatkhau(jsonObject.getString(config.MatKhau_HV));
                        hocVien.setEmail(jsonObject.getString(config.Email_HV));
                        hocVien.setSdt(jsonObject.getString(config.SDT_HV));
                        hocVien.setDiachi(jsonObject.getString(config.DiaChi_HV));
                        hocVien.setAvatar(jsonObject.getString(config.Avatar_HV));
                        hocVien.setTrangthai(jsonObject.getInt(config.TrangThai_HV));
                        ((MyViewHolder)holder).taikhoan.setText(hocVien.getTaikhoan());
                    }
                    else {
                        Toast.makeText(context,jsonOb.getString(config.ThongBao),Toast.LENGTH_SHORT).show();
                    }

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
                params.put(config.Ma_HV,phieuDangKy.getMahv()+"");
                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequestHocVien);

        StringRequest stringRequestLop=new StringRequest(Request.Method.POST, config.URL_Lop_Get, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonGetLopTheoKH",response.toString());
                try {
                    JSONObject jsonOb=new JSONObject(response.trim());
                    if(jsonOb.getInt(config.ThanhCong)==1){
                        JSONArray list = (JSONArray) jsonOb.getJSONArray(config.Table_Lop);
                        JSONObject jsonObject=(JSONObject)list.get(0);
                        Lop lop=new Lop();
                        lop.setMalop(jsonObject.getInt(config.Ma_Lop));
                        lop.setTenlop(jsonObject.getString(config.Ten_Lop));
                        lop.setMakh(jsonObject.getInt(config.Ma_KH));
                        lop.setMagv(jsonObject.getInt(config.Ma_GV));
                        lop.setBatdau(jsonObject.getString(config.BD_Lop));
                        lop.setKetthuc(jsonObject.getString(config.KT_Lop));
                        lop.setCahoc(jsonObject.getString(config.CaHoc_Lop));
                        lop.setAnhminhhoa(jsonObject.getString(config.Anh_Lop));
                        lop.setDanhgia(jsonObject.getDouble(config.DanhGia_Lop));
                        lop.setHocphi(jsonObject.getDouble(config.HocPhi_Lop));
                        ((MyViewHolder)holder).tenlop.setText(lop.getTenlop());
                        phieuDangKy.setTiendong(lop.getHocphi());
                        phieuDangKy.setBatdau(lop.getBatdau());
                        phieuDangKy.setKetthuc(lop.getKetthuc());
                        ((MyViewHolder)holder).hocphi.setText(phieuDangKy.getTiendong()+"");
                    }
                    else {

                        Toast.makeText(context,jsonOb.getString(config.ThongBao),Toast.LENGTH_SHORT).show();
                    }

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
                params.put(config.Ma_Lop,phieuDangKy.getMalop()+"");
                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequestLop);

        ((MyViewHolder)holder).mapdk.setText("Mã phiếu #"+phieuDangKy.getMapdk());
        ((MyViewHolder)holder).seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress()>90){
                    phieuDangKy.setTrangthai(1);
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    phieuDangKy.setNgaydonghocphi(simpleDateFormat.format(calendar.getTime()));
                    StringRequest stringRequestUpdate=new StringRequest(Request.Method.POST, config.URL_PhieuDK_Update, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("jsonUpdatePhieuDK",response.toString());
                            try {
                                JSONObject jsonObject = new JSONObject(response.trim());
                                if (jsonObject.getInt(config.ThanhCong) == 1) {
                                    Toast.makeText(context, jsonObject.getString(config.ThongBao), Toast.LENGTH_SHORT).show();
                                    getAllDangCho();
                                }
                                else {
                                    Toast.makeText(context, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
                            params.put(config.Ma_PhieuDK,phieuDangKy.getMapdk()+"");
                            params.put(config.TrangThai_PhieuDK,phieuDangKy.getTrangthai()+"");
                            params.put(config.NgayDongHP_PhieuDK,phieuDangKy.getNgaydonghocphi());
                            params.put(config.TienDong_PhieuDK,phieuDangKy.getTiendong()+"");
                            params.put(config.BD_Lop,phieuDangKy.getBatdau());
                            params.put(config.KT_Lop,phieuDangKy.getKetthuc());
                            params.put(config.CaHoc_Lop,phieuDangKy.getCahoc());
                            return params;
                        }
                    };
                    Volley.newRequestQueue(context).add(stringRequestUpdate);

                }
            }
        });

        ((MyViewHolder)holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                View view= LayoutInflater.from(context).inflate(R.layout.dialog_xac_nhan,null);
                TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                xacnhan.setText("Bạn có muốn xóa phiếu đăng ký này?");
                Button yes=view.findViewById(R.id.yes);
                Button no=view.findViewById(R.id.no);
                builder.setView(view);
                builder.setCancelable(false);
                AlertDialog dialog=builder.create();
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_PhieuDK_Delete, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("jsonDeleteLop",response.toString());
                                try {
                                    JSONObject jsonObject = new JSONObject(response.trim());
                                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                                        Toast.makeText(context, jsonObject.getString(config.ThongBao), Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(context, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Kết nối thất bại", Toast.LENGTH_SHORT).show();
                            }

                        })
                        {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put(config.Ma_PhieuDK,phieuDangKy.getMapdk()+"");
                                return params;
                            }
                        };
                        Volley.newRequestQueue(context).add(stringRequest);
                        dialog.dismiss();
                        getAllDangCho();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return false;
            }
        });
    }

    @Override
    public void getAll() {

    }

    public void getAllDangCho(){
        phieuDangKIES=new ArrayList<>();
        FragmentPhieuDangKy.pDialog.show();
        JsonObjectRequest jsonObjectRequestGetAll=new JsonObjectRequest(config.URL_PhieuDK_GetAllWait, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("jsonGetPhieuDK",response.toString());
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
                        Toast.makeText(context,response.getString(config.ThongBao),Toast.LENGTH_SHORT).show();
                    }
                    PhieuDangKyAdapter adapter=new PhieuDangKyAdapter(context,phieuDangKIES,recyclerView);
                    recyclerView.setAdapter(adapter);
                    FragmentPhieuDangKy.pDialog.dismiss();

                } catch (JSONException ex) {
                    FragmentPhieuDangKy.pDialog.dismiss();
                    Log.d("Thất bại","");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("thất bại",error.toString());
            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequestGetAll);
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

    @Override
    public int getItemCount() {
        return phieuDangKIES.size();
    }
}
