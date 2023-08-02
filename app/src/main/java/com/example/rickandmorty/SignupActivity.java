package com.example.rickandmorty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    // הגדרת regex לצורך בדיקת תקינות קלט מהמשתמש

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    String passRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$";

    //שמירת הviews והפיירבייס בתוך משתנים
    EditText userEmail, userPass, userPassConfirm;
    Button btnRegister, btnMoveToLogin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //אתחול הviews, הפיירבייס והכפתורים
        initViews();
        initParameters();
        initButtons();
    }

    //מתודה לאתחול הפיירבייס
    private void initParameters() {
        mAuth = FirebaseAuth.getInstance();
    }

    //הגדרת onclicklistener לכפתורים
    private void initButtons() {
        btnRegister.setOnClickListener(this);
        btnMoveToLogin.setOnClickListener(this);
    }

    //אתחול הviews לפי הid המתאימים
    private void initViews(){
        userEmail = findViewById(R.id.signupEmail);
        userPass = findViewById(R.id.signupPassword);
        userPassConfirm = findViewById(R.id.signupPasswordConfirm);
        btnRegister = findViewById(R.id.btnRegister);
        btnMoveToLogin = findViewById(R.id.btnMoveToLogin);
    }

    //מימוש מתודת onclick
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            //אם נלחץ כפתור register בצע פעולת הרשמה
            case R.id.btnRegister:
                signupAction();
                break;
            //אם נלחץ כפתור מעבר לlogin נעבור לעמוד ההתחברות
            case R.id.btnMoveToLogin:
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                break;
        }
    }

    //מתודת הרשמה
    private void signupAction() {
        // בדיקה שאין שדות ריקים
        if (userEmail.getText().toString().isEmpty() ||
            userPass.getText().toString().isEmpty() ||
            userPassConfirm.getText().toString().isEmpty()){
            Toast.makeText(SignupActivity.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
            return;
        }
        //שמירת האימייל, סיסמה ואימות סיסמה במחרוזות
        String email = userEmail.getText().toString();
        String pass = userPass.getText().toString();
        String passConf = userPassConfirm.getText().toString();

        // השוואת האימייל מול הregex
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);
        //
        boolean isValidEmail = emailMatcher.matches();

        // השוואת הסיסמה מול הregex
        Pattern passPattern = Pattern.compile(passRegex);
        Matcher passMatcher = passPattern.matcher(pass);
        boolean isValidPassword = passMatcher.matches();
        // בדיקה שהאימייל והסיסמה מתאימים לדרישות, ושהסיסמאות תואמות
        if (pass.equals(passConf) && isValidEmail && isValidPassword){
            // אם הכל עומד בדרישות, ניצור משתמש חדש בפיירבייס
            mAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    // ברגע שיצירת המשתמש הסתיימה בהצלחה נועבר לתפריט המשחקים
                    if (task.isSuccessful()){
                        Toast.makeText(SignupActivity.this, "Signed up successfully! Moving to main menu", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                    }
                    // במידה ולא הצליח ליצור משתמש - הצג הודעת שגיאה
                    else{
                        Toast.makeText(SignupActivity.this, "Failed creating user", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(SignupActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
        }

    }
}