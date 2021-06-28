package com.example.cameraactivitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePage extends AppCompatActivity {
    CircleImageView Profilepic;
    TextView txt1,txt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        Profilepic=findViewById(R.id.pic);
        txt1=findViewById(R.id.profilename);
        txt2=findViewById(R.id.profileemail);
        Intent intent=getIntent();
        byte[] profile=intent.getByteArrayExtra("PROFILE");
        String name=intent.getStringExtra("NAME");
        String email=intent.getStringExtra("EMAIL");
        Bitmap bitmap=BitmapFactory.decodeByteArray(profile,0,profile.length);
        Profilepic.setImageBitmap(bitmap);
        txt1.setText(name);
        txt2.setText(email);


    }
}