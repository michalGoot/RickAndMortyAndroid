package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
//הורשה בינהם
public class SplashScreen extends AppCompatActivity {

    public int DELAY_TIME = 2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //בונה את הוויואס מהקסמל
        setContentView(R.layout.activity_splash_screen);
        //שמירת אייקון בתוך משתנה
        ImageView icon = findViewById(R.id.iconSplashScreen);
        //ומפעיל את האנימציה שלו
        icon.animate().rotation(360).scaleX(2f).scaleY(2f).setDuration(DELAY_TIME).start();
        // פונקציה אשר פועלת לאחר 2 וחצי שניות ומבצעת מעבר מהספלאש סקרין לדף הבית (התחברות)
        new Handler().postDelayed(new Runnable() {
            @Override
            //ריצה לחלון אחר
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            }
        }, DELAY_TIME);
//MainActivity אחרי שתיים וחצי שניות יופעל
    }
}