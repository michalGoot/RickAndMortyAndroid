package com.example.rickandmorty;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    //שמירת הviews בתוך משתנים
    EditText userEmail, userPassword;
    Button btnLogin, btnSignup;
    //אתחול משתנה של פיירבייס
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //אתחול המשתנים
        initParams();
        initViews();
        initButtons();
    }
    //שליפת האובייקט של פיירבייס
    private void initParams() {
        mAuth = FirebaseAuth.getInstance();
    }

    //הוספת onclicklistener לכפתורים
    private void initButtons() {
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    //אתחול הviews לפי הid המתאים של כל אחד
    private void initViews() {
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
    }

    //דריסת מתודת onbackpressed כדי שבעת לחיצה על כפתור חזרה מתוך הדף הנוכחי - נישאל האם לצאת מהאפליקציה לגמרי
    @Override
    public void onBackPressed() {
        ShowDialog();
    }
    // מתודה להצגת הדיאלוג עם המשתמש
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

    // מימוש מתודת onclick
    @Override
    public void onClick(View view) {
        //אם נלחץ הכפתור של login תבוצע מתודת loginAction
        if(view == btnLogin)
            loginAction();
        //אם נלחץ הכפתור של signup נועבר למסך ההרשמה
        else if(view == btnSignup)
            startActivity(new Intent(MainActivity.this, SignupActivity.class));
    }

    //מתודת התחברות למשתמש קיים באמצעות פיירבייס
    private void loginAction() {
        // אם אחד מהשדות ריקים - הצגת הודעה מתאימה
        if (userEmail.getText().toString().isEmpty() || userPassword.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this, "One or more fields are empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        // אם הפרטים שהוזנו תקינים תבוצע התחברות דרך פיירבייס
        mAuth.signInWithEmailAndPassword(userEmail.getText().toString(),
                userPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // הצגת הודעה שההתחברות הצליחה ומעבר לתפריט המשחקים
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
                // במקרה ואחד מהשדות (או יותר) לא תקינים - תוצג הודעת שגיאה
                else{
                    Toast.makeText(MainActivity.this, "Failed to sign in, email or password is incorrect.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
