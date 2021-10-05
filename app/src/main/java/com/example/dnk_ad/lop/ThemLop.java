package com.example.dnk_ad.lop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class ThemLop extends AppCompatActivity implements LopDAO{
    Toolbar toolbar;
    Button btnLuu;
    ImageView back;
    EditText tenlop,mota,batdau,ketthuc,hocphi;
    TextInputLayout inputLayoutTenLop,inputLayoutBD,inputLayoutKT,inputLayoutHP;
    Spinner spinnerThemGV,spinnerThemCaHoc;
    ImageView anhminhoa;
    Bitmap bm;
    int makh;
    int vitriGV;
    ArrayList<GiangVien> giangViens;
    ArrayList<String> tenGVs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_lop);
        toolbar=findViewById(R.id.toolbarThemLop);
        btnLuu=findViewById(R.id.btnLuuLop);
        back=findViewById(R.id.ivBack_AddLop);
        tenlop=findViewById(R.id.etThemTenLop);
        mota=findViewById(R.id.etThemMoTa);
        batdau=findViewById(R.id.etThemNgayBD);
        ketthuc=findViewById(R.id.etThemNgayKT);
        hocphi=findViewById(R.id.etThemHocPhi);
        inputLayoutTenLop=findViewById(R.id.textInputThemTenLop);
        inputLayoutBD=findViewById(R.id.textInputThemNgayBD);
        inputLayoutKT=findViewById(R.id.textInputThemNgayKT);
        inputLayoutHP=findViewById(R.id.textInputThemHocPhi);
        spinnerThemGV=findViewById(R.id.spinnerThemGV);
        spinnerThemCaHoc=findViewById(R.id.spinnerThemCaHoc);
        anhminhoa=findViewById(R.id.ivThemAnhMinhHoa);
        bm= BitmapFactory.decodeResource(getResources(),R.drawable.logo);
        makh=getIntent().getIntExtra(config.Ma_KH,-1);
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
        spinnerThemCaHoc.setAdapter(adapter);

        batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThemLop.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThemLop.this, new DatePickerDialog.OnDateSetListener() {
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
        anhminhoa.setOnClickListener(new View.OnClickListener() {
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

        getAllGV();


        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vitriGV=0;
                for(int i=0;i<giangViens.size();i++){
                    if(spinnerThemCaHoc.getSelectedItemPosition()==i){
                        vitriGV=giangViens.get(i).getMagv();
                        break;
                    }
                }
                try{
                    if(tenlop.getText().toString().equals("")||batdau.getText().toString().equals("")||ketthuc.getText().toString().equals("")||hocphi.getText().toString().equals("")||vitriGV==0){
                        Toast.makeText(ThemLop.this,"Không được để trống",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        add();
                    }
                }catch (Exception ex){
                    Toast.makeText(ThemLop.this,"Thêm thất bại!",Toast.LENGTH_SHORT).show();
                }
            }
        });
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


    public void add(){
        LopActivity.pDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.URL_Lop_Add, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonAddLop",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.trim());
                    if (jsonObject.getInt(config.ThanhCong) == 1) {
                        Toast.makeText(ThemLop.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(ThemLop.this, jsonObject.getString(config.ThongBao), Toast.LENGTH_LONG).show();
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
                Toast.makeText(ThemLop.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(config.Ten_Lop,tenlop.getText().toString());
                params.put(config.Mota_Lop,mota.getText().toString());
                params.put(config.Ma_KH,makh+"");
                params.put(config.Ma_GV,vitriGV+"");
                params.put(config.BD_Lop,batdau.getText().toString());
                params.put(config.KT_Lop,ketthuc.getText().toString());
                params.put(config.CaHoc_Lop,spinnerThemCaHoc.getSelectedItem().toString());
                params.put(config.Anh_Lop,imageStore());
                params.put(config.DanhGia_Lop,0.0+"");
                params.put(config.HocPhi_Lop,hocphi.getText().toString()+"");
                return params;
            }
        };
        Volley.newRequestQueue(ThemLop.this).add(stringRequest);

    }

    @Override
    public void update() {

    }

    @Override
    public void updateAnh() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void getGV() {

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
                        ArrayAdapter<String> adapter=new ArrayAdapter<>(ThemLop.this, android.R.layout.simple_spinner_dropdown_item,tenGVs);
                        spinnerThemGV.setAdapter(adapter);
                    }
                    else {
                        Toast.makeText(ThemLop.this,response.getString(config.ThongBao),Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException ex) {
                    Log.d("Thất bại","");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("thất bại",error.toString());
            }
        });
        Volley.newRequestQueue(ThemLop.this).add(jsonObjectRequest);
    }

    @Override
    public void getDSLop() {

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
                    anhminhoa.setImageBitmap(bitmap);
                    bm=bitmap;
                    Log.d("bitmap",bitmap.toString());
                }
                else {
                    Uri uri=data.getData();
                    Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    anhminhoa.setImageBitmap(bitmap);
                    bm=bitmap;
                    Log.d("Uri",bitmap.toString());
                }
            }catch (Exception ex){

            }
        }
        else
            Log.d("FAIL","FAIL");

    }
}