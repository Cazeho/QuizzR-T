package com.example.onlinequizapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentActivity.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction;
import android.view.animation.Animation;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentActivity.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction;
import android.view.animation.Animation;
import android.widget.Toast;


import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CategoryFragment extends Fragment {





        View myFragment;
    FirebaseDatabase database;
    DatabaseReference categories;
    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;



    RecyclerView recyclerView;

    FirebaseRecyclerOptions<Category> options;




    public static CategoryFragment newInstance() {
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;

    }





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        categories = database.getReference().child("Category");








    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_category,container,false);
        listCategory = (RecyclerView)myFragment.findViewById(R.id.listCategory);
        listCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);



        options= new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(categories, Category.class)
                .build();

        adapter= new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CategoryViewHolder viewHolder, int position, @NonNull final Category model) {
                viewHolder.t1.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.i1, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                Toast.makeText(getActivity(), String.format("%s|%s",adapter.getRef(position).getKey(),model.getName()), Toast.LENGTH_SHORT).show();
                                Intent StartGame = new Intent(getActivity(),Start.class);
                                Commun.categoryId = adapter.getRef(position).getKey();
                                startActivity(StartGame);

                            }
                        });
                    }



                    @Override
                    public void onError(Exception e) {

                    }
                });


            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout,parent, false);
                return new CategoryViewHolder(view);
            }
        };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);



        return myFragment;
    }
    @Override
    public void onStart(){
        super.onStart();
        if (adapter!=null)
            adapter.startListening();

    }

    @Override
    public void onStop(){
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();


    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null)
            adapter.startListening();
    }







}
