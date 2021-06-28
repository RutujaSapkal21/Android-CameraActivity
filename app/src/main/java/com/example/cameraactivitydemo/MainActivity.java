package com.example.cameraactivitydemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    CircleImageView circleImageView;
    int CAMERA_REQUEST_CODE=101;
    int CAMERA_PERMISSION_CODE=102;

    List<ModuleClass> profilelist;
    ProfileAdapter profileAdapter;

    Button addbtn;
    EditText edtname,edtemail;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleImageView=findViewById(R.id.img1);
        edtname=findViewById(R.id.name);
        edtemail=findViewById(R.id.email);
        addbtn=findViewById(R.id.add);
        recyclerView=findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        profilelist=new ArrayList<>();
        profileAdapter=new ProfileAdapter(this,profilelist);
        recyclerView.setAdapter(profileAdapter);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModuleClass module=new ModuleClass();
                String myname=edtname.getText().toString();
                String myemail=edtemail.getText().toString();

                byte[] bytearray=new byte[0];
                try {
                    bytearray=convert_to_byteArray(circleImageView);
                }
                catch (Exception e){

                }
                module.setName(myname);
                module.setEmail(myemail);
                module.setProfile(bytearray);
                profilelist.add(module);
                profileAdapter.notifyDataSetChanged();

            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               askPermission(Manifest.permission.CAMERA,CAMERA_REQUEST_CODE);

            }


        });

    }

    private void askPermission(String camera, int camera_request_code) {
        if(ContextCompat.checkSelfPermission(this,camera)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new  String[]{camera},camera_request_code);
        }
        else
        {
            openCamera();

        }
    }



    void openCamera()
    {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Bitmap imgdata=(Bitmap)data.getExtras().get("data");

        circleImageView.setImageBitmap(imgdata);
        //get data from
    }

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==CAMERA_PERMISSION_CODE){
            openCamera();
        }
        else{
            Toast.makeText(MainActivity.this,"Permission is Deny",Toast.LENGTH_LONG).show();
        }

    }*/

    public byte[] convert_to_byteArray(ImageView imageView){
        //to get image
        Bitmap myimg=((BitmapDrawable)imageView.getDrawable()).getBitmap();

        //convert into byteArray
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

        //compression,quality,where to store
        myimg.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

        byte[] bytearr=byteArrayOutputStream.toByteArray();
        return bytearr;
    }




}