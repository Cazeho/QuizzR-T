package com.example.onlinequizapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RankingFragment extends Fragment {
    View mFragment;
    FirebaseDatabase database;
    DatabaseReference questionScore, RangR;
    RecyclerView listrang;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Rang, RangViewHolder> adapter;




    FirebaseRecyclerOptions<Rang> options;

    int sum=0;


    public static RankingFragment newInstance() {
        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment;

    }





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        questionScore = database.getReference().child("Question_Score");
        RangR = database.getReference().child("Rang");









    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          mFragment= inflater.inflate(R.layout.fragment_ranking,container, false);
          listrang = (RecyclerView)mFragment.findViewById(R.id.listrang);
          listrang.setHasFixedSize(true);
          layoutManager = new LinearLayoutManager(getActivity()){
              @Override
              public void setReverseLayout(boolean reverseLayout) {
                  super.setReverseLayout(reverseLayout);
                  super.setReverseLayout(true);
              }

              @Override
              public void setStackFromEnd(boolean stackFromEnd) {
                  super.setStackFromEnd(stackFromEnd);
                  super.setStackFromEnd(true);
              }
          };
          listrang.setLayoutManager(layoutManager);



          updateScore(Commun.currentUser.getUserName(), new RangCallBack<Rang>() {
              @Override
              public void callBack(Rang rang) {
                  RangR.child(rang.getUserName()).setValue(rang);
                  //showRanking();//

              }
          });

        options= new FirebaseRecyclerOptions.Builder<Rang>()
                .setQuery(RangR.orderByChild("score"), Rang.class)
                .build();

          adapter= new FirebaseRecyclerAdapter<Rang, RangViewHolder>(options) {
              @Override
              protected void onBindViewHolder(@NonNull RangViewHolder viewHolder, int position, @NonNull final Rang model) {
                  viewHolder.txt_name.setText(model.getUserName());
                  viewHolder.txt_score.setText(String.valueOf(model.getScore()));





              }

              @NonNull
              @Override
              public RangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rang,parent, false);
                  return new RangViewHolder(view);
              }
          };
        adapter.notifyDataSetChanged();
        listrang.setAdapter(adapter);





        return mFragment;
    }




    private void updateScore(final String userName, final RangCallBack<Rang> callback) {

        questionScore.orderByChild("user").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){

                    QuestionScore ques = data.getValue(QuestionScore.class);
                    sum = sum + Integer.parseInt(ques.getScore());


                }

                Rang rang = new Rang(userName, sum);
                callback.callBack(rang);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
