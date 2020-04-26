package com.example.onlinequizapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemClickListener itemClickListener;

    public TextView t1;
    public ImageView i1;



    public CategoryViewHolder(View itemView){
        super(itemView);
        t1=(TextView)itemView.findViewById(R.id.category_name);
        i1= (ImageView)itemView.findViewById(R.id.category_image);

        itemView.setOnClickListener(this);



    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick( view, getAdapterPosition(), false);

    }






    /*
    public TextView category_name;
    public ImageView category_image;

    private ItemClickListener itemClickListener;



    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        category_image= (ImageView)itemView.findViewById(R.id.category_image);
        category_name= (TextView)itemView.findViewById(R.id.category_name);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick( view, getAdapterPosition(), false);

    }
    */
}
