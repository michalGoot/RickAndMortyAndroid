package com.example.rickandmorty;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
//תפריט המשחקים
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSevenBoom, btnGuessNumber, btnRPS, btnSignOut;
    FirebaseAuth mAuth;
    ImageView exitIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initParams();
        initViews();
        initButtons();
    }
    //התחברות מהפייר בייס
    private void initParams(){
        mAuth = FirebaseAuth.getInstance();
    }
    //הגדרת onclicklistener לכפתורים הרלוונטיים
    private void initButtons() {
        btnSevenBoom.setOnClickListener(this);
        btnGuessNumber.setOnClickListener(this);
        btnRPS.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        exitIcon.setOnClickListener(this);
    }
    //אתחול הviews לפי הid המתאימים
    private void initViews() {
        exitIcon = findViewById(R.id.exitIcon);
        btnSevenBoom = findViewById(R.id.btnSevenBoom);
        btnGuessNumber = findViewById(R.id.btnGuessNumber);
        btnRPS = findViewById(R.id.btnRPS);
        btnSignOut = findViewById(R.id.btnSignout);
    }

    //בעת לחיצת כפתור חזור - יוצג דיאלוג עם המשתמש
    @Override
    public void onBackPressed() {
        ShowDialog();
    }

    // דיאלוג האם לצאת מהאפליקציה או להישאר
    private void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    //מתודת onclick
    @Override
    public void onClick(View view) {
        //ניתן גם לעשות עם switch case

        //מעבר ל7 בום
        if(view == btnSevenBoom)
            startActivity(new Intent(HomeActivity.this, SevenBoomActivity.class));
        //מעבר לניחוש מספר
        else if (view == btnGuessNumber)
            startActivity(new Intent(HomeActivity.this, GuessNumberActivity.class));
        //מעבר לRPS
        else if (view == btnRPS)
            startActivity(new Intent(HomeActivity.this, RPSActivity.class));
        //יציאה באמצעות האייקון של היציאה (הצגת הדיאלוג)
        else if (view == exitIcon){
            ShowDialog();
        }
        // התנתקות מהחשבון ומעבר לדף ההתחברות
        else if (view == btnSignOut){
            mAuth.signOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        }
    }
}