package com.example.dnk_ad.lop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.dnk_ad.R;
import com.example.dnk_ad.config;
import com.example.dnk_ad.dao.LopDAO;
import com.example.dnk_ad.giangvien.GiangVien;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ThongTinLop extends AppCompatActivity implements LopDAO {
    EditText tenkh,tenlop,mota,batdau,ketthuc,hocphi;
    TextInputLayout inputLayoutTenLop,inputLayoutBD,inputLayoutKT,inputLayoutHP;
    ImageView back;
    TextView xemDSLop;
    ImageView anhminhhoa;
    Spinner spinnerGV,spinnerCaHoc;
    Button btnLuu,btnXoa;
    int malop;
    Bitmap bm;
    int vitriGV;
    int makh;
    double danhgia;
    ArrayList<GiangVien> giangViens;
    ArrayList<String> tenGVs;
    boolean change_anh=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_lop);
        tenkh=findViewById(R.id.etTenKH_TTLop);
        tenlop=findViewById(R.id.etTenLop_TTLop);
        mota=findViewById(R.id.etMoTa_TTLop);
        batdau=findViewById(R.id.etNgayBD_TTLop);
        ketthuc=findViewById(R.id.etNgayKT_TTLop);
        hocphi=findViewById(R.id.etHocPhi_TTLop);
        inputLayoutTenLop=findViewById(R.id.textInputTenLop_TTLop);
        inputLayoutBD=findViewById(R.id.textInputNgayBD_TTLop);
        inputLayoutKT=findViewById(R.id.textInputNgayKT_TTLop);
        inputLayoutHP=findViewById(R.id.textInputHocPhi_TTLop);
        back=findViewById(R.id.ivBack_TTLop);
        xemDSLop=findViewById(R.id.tvXemDSLop_TTLop);
        anhminhhoa=findViewById(R.id.ivAnhMinhHoa_TTLop);
        spinnerGV=findViewById(R.id.spinnerGV_TTLop);
        spinnerCaHoc=findViewById(R.id.spinnerCaHoc_TTLop);
        btnLuu=findViewById(R.id.btnLuu_TTLop);
        btnXoa=findViewById(R.id.btnXoa_TTLop);
        malop=getIntent().getIntExtra(config.Ma_Lop,-1);
        makh=getIntent().getIntExtra(config.Ma_KH,-1);
        danhgia=getIntent().getDoubleExtra(config.DanhGia_Lop,-1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        inputLayoutTenLop.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    inputLayoutTenLop.setError("Không được để trống");
                    inputLayoutTenLop.setErrorEnabled(true);
                }
                else
                    inputLayoutTenLop.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputLayoutBD.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    inputLayoutBD.setError("Không được để trống");
                    inputLayoutBD.setErrorEnabled(true);
                }
                else
                    inputLayoutBD.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputLayoutKT.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    inputLayoutKT.setError("Không được để trống");
                    inputLayoutKT.setErrorEnabled(true);
                }
                else
                    inputLayoutKT.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputLayoutHP.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    inputLayoutHP.setError("Không được để trống");
                    inputLayoutHP.setErrorEnabled(true);
                }
                else
                    inputLayoutHP.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,config.CaHoc);
        spinnerCaHoc.setAdapter(adapter);
        spinnerCaHoc.setSelection(config.CaHoc.indexOf(getIntent().getStringExtra(config.CaHoc_Lop)));

        tenkh.setText(getIntent().getStringExtra(config.Ten_KH));
        tenlop.setText(getIntent().getStringExtra(config.Ten_Lop));
        mota.setText(getIntent().getStringExtra(config.Mota_Lop));
        batdau.setText(getIntent().getStringExtra(config.BD_Lop));
        ketthuc.setText(getIntent().getStringExtra(config.KT_Lop));
        hocphi.setText(getIntent().getDoubleExtra(config.HocPhi_Lop,-1.0)+"");
        Glide.with(this).asBitmap().load(config.URL_Lop_AnhMinhHoa+getIntent().getStringExtra(config.Anh_Lop)).into(anhminhhoa);

        Glide.with(this).asBitmap().load(config.URL_Lop_AnhMinhHoa+getIntent().getStringExtra(config.Anh_Lop)).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                bm=resource;
                return false;
            }
        }).submit();


        batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThongTinLop.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        batdau.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        ketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThongTinLop.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        ketthuc.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        anhminhhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent take=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent pick=new Intent(Intent.ACTION_GET_CONTENT);
                pick.setType("image/*");
                Intent intent=Intent.createChooser(pick,"Chọn");
                intent.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{take});
                startActivityForResult(intent,999);
            }
        });
        xemDSLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ThongTinLop.this,DanhSachLop.class);
                intent.putExtra(config.Ten_Lop,tenlop.getText().toString());
                intent.putExtra(config.Ma_Lop,malop);
                startActivity(intent);
            }
        });

        getAllGV();
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vitriGV=0;
                for(int i=0;i<giangViens.size();i++){
                    if(spinnerGV.getSelectedItemPosition()==i){
                        vitriGV=giangViens.get(i).getMagv();
                        break;
                    }
                }
                if(tenlop.getText().toString().equals("")||hocphi.getText().toString().equals("")){
                    Toast.makeText(ThongTinLop.this,"Không được để trống",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(change_anh==true)
                        updateAnh();
                    update();
                }

            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ThongTinLop.this);
                View view= LayoutInflater.from(ThongTinLop.this).inflate(R.layout.dialog_xac_nhan,null);
                TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                xacnhan.setText("Bạn có thực sự muốn xóa");
                Button yes=view.findViewById(R.id.yes);
                Button no=view.findViewById(R.id.no);
                builder.setView(view);
                builder.setCancelable(false);
                AlertDialog dialog=builder.create();
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete();
                        dialog.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }



    public void getAllGV(){
        giangViens=new ArrayList<>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(config.URL_GV_GetAll, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("getAllGV",response.toString());
                try {
                    if(response.getInt(config.ThanhCong)==1){
                        JSONArray list = (JSONArray) response.getJSONArray(config.Table_GV);
                        for(int i=0;i<list.length();i++){
                            GiangVien x=new GiangVien();
                            JSONObject jsonObject=(JSONObject)list.get(i);
                            x.setMagv(jsonObject.getInt(config.Ma_GV));
                            x.setTengv(jsonObject.getString(config.Ten_GV));
                            giangViens.add(x);
                        }
                        tenGVs=new ArrayList<>();
                        for(int i=0;i<giangViens.size();i++){
                            tenGVs.add(giangViens.get(i).getTengv());
                        }
                        ArrayAdapter<String> adapter=new ArrayAdapter<>(ThongTinLop.this, android.R.layout.simple_spinner_dropdown_item,tenGVs);
                        spinnerGV.setAdapter(adapter);
                        GiangVien y=new GiangVien();
                        for(int i=0;i<giangViens.size();i++){
                            if(giangViens.get(i).getMagv()==getIntent().getIntExtra(config.Ma_GV,-1)){
                                y=giangViens.get(i);
                                break;
                            }
                        }
                        spinnerGV.setSelection(giangViens.indexOf(y));
                    }
                    else {
                        Toast.makeText(ThongTinLop.this,response.getString(config.ThongBao),Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException ex) {
                    Log.d("Thất bại","");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("thất bại",error.toString());
                LopActivity.pDialog.dismiss();
                Toast.makeText(ThongTinLop.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(ThongTinLop.this).add(jsonObjectRequest);
    }

    @Override
    public void getDSLop() {

    }



    @Override
    public void getAll() {

    }

    @Override
    public void getAllTheoKH() {

    }

    @Override
    public void getAllTheoKHActive() {

    }

    @Override
    public void add() {

    }

    public void update(){
        LopActivity.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_Lop_Update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonUpdateLop",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThongTinLop.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ThongTinLop.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                    }
                    finish();
                    LopActivity.pDialog.dismiss();
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
                params.put(config.Ma_Lop,malop+"");
                params.put(config.Ten_Lop,tenlop.getText().toString());
                params.put(config.Mota_Lop,mota.getText().toString());
                params.put(config.Ma_KH,makh+"");
                params.put(config.Ma_GV,vitriGV+"");
                params.put(config.BD_Lop,batdau.getText().toString());
                params.put(config.KT_Lop,ketthuc.getText().toString());
                params.put(config.CaHoc_Lop,spinnerCaHoc.getSelectedItem().toString());
                params.put(config.DanhGia_Lop,danhgia+"");
                params.put(config.HocPhi_Lop,hocphi.getText().toString()+"");
                return params;
            }
        };
        Volley.newRequestQueue(ThongTinLop.this).add(stringRequest);
    }

    @Override
    public void updateAnh() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_Lop_UpdateAnh, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonUpdateAnh",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                    }
                    else {
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
                params.put(config.Ma_Lop,malop+"");
                params.put(config.Anh_Lop,imageStore());
                return params;
            }
        };
        Volley.newRequestQueue(ThongTinLop.this).add(stringRequest);
    }

    public void delete(){
        LopActivity.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_Lop_Delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonDeleteLop",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThongTinLop.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ThongTinLop.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
                    }
                    finish();
                    LopActivity.pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LopActivity.pDialog.dismiss();
                Toast.makeText(ThongTinLop.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ma_Lop,malop+"");
                return params;
            }
        };
        Volley.newRequestQueue(ThongTinLop.this).add(stringRequest);
    }

    @Override
    public void getGV() {

    }


    public String imageStore(){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytes=byteArrayOutputStream.toByteArray();
        Log.d("bitmap",bm.toString());
        return android.util.Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==999){
            Log.d("OK","OK");
            try{
                if(data.getExtras()!=null){
                    Bundle bundle=data.getExtras();
                    Bitmap bitmap=(Bitmap)bundle.get("data");
                    anhminhhoa.setImageBitmap(bitmap);
                    bm=bitmap;
                    change_anh=true;
                    Log.d("bitmap",bitmap.toString());
                }
                else {
                    Uri uri=data.getData();
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    anhminhhoa.setImageBitmap(bitmap);
                    bm=bitmap;
                    change_anh=true;
                    Log.d("Uri",bitmap.toString());
                }
            }catch (Exception ex){

            }
        }
        else
            Log.d("FAIL","FAIL");

    }
}