package com.example.cameraactivitydemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileHolder> {
    Context mContext;
    List<ModuleClass> profilelist;
    public ProfileAdapter(Context mContext, List<ModuleClass> profilelist) {
        this.mContext = mContext;
        this.profilelist = profilelist;
    }

    @NonNull
    @Override
    public ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview;
        myview= LayoutInflater.from(mContext).inflate(R.layout.customdesign,parent,false);
        return new ProfileHolder(myview);
    }



    @Override
    public void onBindViewHolder(@NonNull ProfileHolder holder, int position) {
        ModuleClass module=profilelist.get(position);
        holder.txt1.setText(module.getName());
        //holder.txt2.setText(module.getEmail());

        byte[] bytes=module.getProfile();
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        holder.profileimg.setImageBitmap(bitmap);


        holder.txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(mContext,holder.txt1);
                MenuInflater menuInflater=popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.view:
                                Intent intent1=new Intent(mContext,ProfilePage.class);
                                Toast.makeText(mContext,"You clicked on View",Toast.LENGTH_LONG).show();
                                intent1.putExtra("NAME",module.getName());
                                intent1.putExtra("EMAIL",module.getEmail());
                                intent1.putExtra("PROFILE",module.getProfile());
                                mContext.startActivity(intent1);
                                break;

                            case R.id.delete:
                                profilelist.remove(module);
                                notifyDataSetChanged();
                                Toast.makeText(mContext,"You clicked on Delete",Toast.LENGTH_LONG).show();
                                break;
                        }
                                    return true;
                    }
                });
                popupMenu.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return profilelist.size();
    }

    public class ProfileHolder extends RecyclerView.ViewHolder {

        TextView txt1,txt2;
        CircleImageView profileimg;
        public ProfileHolder(@NonNull View itemView) {
            super(itemView);

            txt1=itemView.findViewById(R.id.namee);
           // txt2=itemView.findViewById(R.id.emaill);
            profileimg=itemView.findViewById(R.id.profile);
        }
    }


}
