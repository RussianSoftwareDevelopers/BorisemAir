package com.borisem.borisemair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


class AdapterBrands extends RecyclerView.Adapter<AdapterBrands.MyViewHolder>{
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_images, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        final ImageButton imageButton = (ImageButton) holder.view.findViewById(R.id.imageViewBrand);
        imageButton.setTag(position);
        imageButton.setImageResource(objects.get(position));

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton imageButton1 = (ImageButton)v;
                int id  =    (Integer)imageButton1.getTag();
                oncheckBrand.OnCheckBrandLisner(objects.get(id));
            }
        });
    }


    @Override
    public int getItemCount() {
        return objects.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public MyViewHolder(View v) {
            super(v);
            view  = v;
        }
    }

    private Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<Integer> objects;
    private  OncheckBrand oncheckBrand;

    AdapterBrands(Context context, ArrayList<Integer> infoGyms, OncheckBrand oncheckBrand1) {
        ctx = context;
        objects = infoGyms;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        oncheckBrand = oncheckBrand1;
    }


    public interface OncheckBrand
    {
        void OnCheckBrandLisner(int id);
    }
}