package com.example.onlinequizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Fait extends AppCompatActivity {

    Button btnRecommencer;
    TextView txtResultScore, getTxtResultQuestion;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference question_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fait);


        database= FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");

        txtResultScore = (TextView)findViewById(R.id.txtTotalScore);
        getTxtResultQuestion= (TextView)findViewById(R.id.txtTotalQuestion);
        progressBar= (ProgressBar)findViewById(R.id.faitProgressBar);
        btnRecommencer= (Button)findViewById(R.id.btnRecommencer);

        btnRecommencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fait.this, Home.class);
                startActivity(intent);
                finish();

            }
        });

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            int score = extra.getInt("Score");
            int totalQuestion = extra.getInt("TOTAL");
            int correctReponse = extra.getInt("CORRECTE");

            txtResultScore.setText(String.format("Score : %d",score));
            getTxtResultQuestion.setText(String.format("Réussie : %d / %d", correctReponse, totalQuestion));

            progressBar.setMax(totalQuestion);
            progressBar.setProgress(correctReponse);

            question_score.child(String.format("%s_%s",Commun.currentUser.getUserName(),
                                                       Commun.categoryId))
                    .setValue(new QuestionScore(String.format("%s_%s", Commun.currentUser.getUserName(),
                            Commun.categoryId),
                            Commun.currentUser.getUserName(),
                            String.valueOf(score)));


        }


    }
}
