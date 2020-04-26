package com.example.onlinequizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.View;
import android.widget.Button;
import java.util.Arrays;

import java.util.Collections;

public class Start extends AppCompatActivity {

    Button btnJouer;
    FirebaseDatabase database;
    DatabaseReference questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        database= FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");


        loadQuestion(Commun.categoryId);

        btnJouer =(Button)findViewById(R.id.btnPlay);

        btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, Jouer.class);
                startActivity(intent);
                finish();

            }
        });

    }
    private void loadQuestion(String categoryId){
        if(Commun.questionList.size() > 0)
            Commun.questionList.clear();

        questions.orderByChild("CategoryId").equalTo(categoryId).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Question ques = postSnapshot.getValue(Question.class);
                    Commun.questionList.add(ques);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });

    }


}
