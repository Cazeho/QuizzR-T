package com.example.onlinequizapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txt_name, txt_score;

    private ItemClickListener itemClickListener;

    public RangViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_name=(TextView)itemView.findViewById(R.id.txt_name);
        txt_score= (TextView)itemView.findViewById(R.id.txt_score);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }



    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);

    }
}
