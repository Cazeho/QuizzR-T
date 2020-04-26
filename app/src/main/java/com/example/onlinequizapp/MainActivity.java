package com.example.onlinequizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    MaterialEditText edtNewUser, edtNewPassword, edtNewEmail;
    MaterialEditText edtUser,edtPassword;
    Button btnSignUp, btnSignIn;
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        edtUser= (MaterialEditText)findViewById(R.id.edtUser);
        edtPassword= (MaterialEditText)findViewById(R.id.edtPassword);

        btnSignUp = (Button)findViewById(R.id.btn_sign_up);
        btnSignIn = (Button)findViewById(R.id.btn_sign_in);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(edtUser.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    private void signIn(final String user, final String pwd){
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user).exists()) {
                    if(!user.isEmpty()) {

                        User login = dataSnapshot.child(user).getValue(User.class);
                        if(login.getPassword().equals(pwd)){
                            Toast.makeText(MainActivity.this, "login ok", Toast.LENGTH_SHORT).show();
                            Commun.currentUser = login;
                            Intent homeActivity = new Intent(MainActivity.this, Home.class);
                            startActivity(homeActivity);
                            finish();
                        }

                        else
                            Toast.makeText(MainActivity.this, "Mauvais password", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(MainActivity.this, "merci de remplir votre Username", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(MainActivity.this, "L'utilisateur n'existe pas", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void showSignUpDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Inscription");
        alertDialog.setMessage("Merci de remplir");

        LayoutInflater inflater = this.getLayoutInflater();
        View sign_up_layout = inflater.inflate(R.layout.sign_up_layout, null);

        edtNewUser= (MaterialEditText)sign_up_layout.findViewById(R.id.edtNewUserName);
        edtNewPassword= (MaterialEditText)sign_up_layout.findViewById(R.id.edtNewPassword);
        edtNewEmail= (MaterialEditText)sign_up_layout.findViewById(R.id.edtNewEmail);

        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);


        alertDialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setPositiveButton("Oui",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final User user = new User(edtNewUser.getText().toString(),
                        edtNewPassword.getText().toString(),
                        edtNewEmail.getText().toString());


                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getUserName()).exists())
                            Toast.makeText(MainActivity.this, "Users existe déja", Toast.LENGTH_SHORT).show();
                        else {
                            users.child(user.getUserName()).setValue(user);
                            Toast.makeText(MainActivity.this, "Users succés de l'enregistrement", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();

    }
}
