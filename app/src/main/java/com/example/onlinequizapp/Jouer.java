package com.example.onlinequizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.Button;
import android.widget.TextView;
import com.squareup.picasso.Picasso;





public class Jouer extends AppCompatActivity implements View.OnClickListener{

    final static long INTERVAL = 1000;
    final static long TIMEOUT = 30000;
    int progressValue = 0;
    CountDownTimer mcountDown ;
    int index=0, score=0, thisQuestion= 0, totalQuestion, correctReponse;

    FirebaseDatabase database;
    DatabaseReference questions;
    ProgressBar progressBar;
    ImageView question_image;
    Button btnA,btnB,btnC;
    TextView txtScore, textQuestionNum, question_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jouer);

/*
        database= FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");
        */
        txtScore= (TextView)findViewById(R.id.txtScore);
        textQuestionNum= (TextView)findViewById(R.id.txtTotalQuestion);
        question_text= (TextView)findViewById(R.id.question_text);
        question_image =(ImageView)findViewById(R.id.question_image);
        progressBar= (ProgressBar)findViewById(R.id.progressBar);
        btnA= (Button)findViewById(R.id.btnReponseA);
        btnB= (Button)findViewById(R.id.btnReponseB);
        btnC= (Button)findViewById(R.id.btnReponseC);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);




    }

    @Override
    public void onClick(View view){

        mcountDown.cancel();
        if(index < totalQuestion){
            Button clickedButton= (Button)view;
            if(clickedButton.getText().equals(Commun.questionList.get(index).getCorrectReponse())){
                score+=10;
                correctReponse++;
                showQuestion(++index);
            }
            else{
                Intent intent = new Intent(this,Fait.class);
                Bundle dataSend = new Bundle();
                dataSend.putInt("Score", score);
                dataSend.putInt("TOTAL", totalQuestion);
                dataSend.putInt("CORRECTE", correctReponse);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
            }
            txtScore.setText(String.format("%d",score));
        }

    }

    private void showQuestion(int index){
        if(index < totalQuestion){
            thisQuestion++;
            textQuestionNum.setText(String.format("%d",thisQuestion,totalQuestion));
            progressBar.setProgress(0);
            progressValue=0;
            if(Commun.questionList.get(index).getIsImageQuestion().equals("true")){
                Picasso.get() //si image
                        .load(Commun.questionList.get(index).getQuestion())
                        .into(question_image);
                question_image.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);
            }
            else{ //si texte
                question_text.setText(Commun.questionList.get(index).getQuestion());
                question_image.setVisibility(View.INVISIBLE);
                question_text.setVisibility(View.VISIBLE);
            }
            btnA.setText(Commun.questionList.get(index).getReponseA());
            btnB.setText(Commun.questionList.get(index).getReponseB());
            btnC.setText(Commun.questionList.get(index).getReponseC());

            mcountDown.start(); //commence le compteur


        }
        else {
            Intent intent = new Intent(this,Fait.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("Score", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECTE", correctReponse);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion= Commun.questionList.size();
        mcountDown= new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long minisec) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                mcountDown.cancel();
                showQuestion(++index);

            }
        };
        showQuestion(index);
    }
}
