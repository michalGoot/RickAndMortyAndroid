package com.example.rickandmorty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

//ניחוש מספר
public class GuessNumberActivity extends AppCompatActivity implements View.OnClickListener {
    //אתחול הviews והמשתנים
    EditText guessNumberInput;
    Button btnGuess, btnReset, btnMenu;
    TextView remainGuessLabel, labelTop;
    ImageView imgHelp;
    MediaPlayer winSound;
    MediaPlayer loseSound;
    //אתחול מספר רנדיומלי
    Random random = new Random();
    //מספר התחלתי של ניחושים
    int remainGuesses = 5;
    int randomNumber;
    //רמה התחלתית
    int level = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_number);
        //init views
        initViews();
        //init game with first level (1-10)
        int level = 1;
        createGameByLevel();
        //init buttons
        inItButtons();
    }



    private void inItButtons() {
        btnGuess.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        imgHelp.setOnClickListener(this);
    }

    private void createGameByLevel() {
        switch (level) {
            case 1:
                createGame(5, 10, level);
                break;
            case 2:
                createGame(8, 50, level);
                break;
            case 3:
                createGame(10, 100, level);
                break;
            case 4:
                createGame(15, 500, level);
                break;
        }
    }

    private void initViews() {
        imgHelp = findViewById(R.id.imgHelp);
        remainGuessLabel = findViewById(R.id.remainGuessLabel);
        labelTop = findViewById(R.id.labelTop);
        guessNumberInput = findViewById(R.id.guessNumberInput);
        btnGuess = findViewById(R.id.btnGuess);
        btnReset = findViewById(R.id.btnReset);
        btnMenu = findViewById(R.id.btnGuessToMenu);
    }

    //יצירת המשחק בהתאם לרמה ולטווח המספרים
    public void createGame(int remainGuesses, int randomUntilMaxNum, int level) {
        //מחרוזת השומרת את הטווח של המספרים לניחוש
        String labelRangeNumbers = "1-" + randomUntilMaxNum;
        //הצגת טווח המספרים לשחקן
        labelTop.setText("Guess the Number Between " + labelRangeNumbers);
        //הגדרת הניחושים הנותרים והצגתם לשחקן
        this.remainGuesses = remainGuesses;
        remainGuessLabel.setText("Remain Guesses is:" + remainGuesses);
        //הגרלת מספר רנדיומלי בין 1 למקסימום של הרמה הנוכחית
        randomNumber = 1 + random.nextInt(randomUntilMaxNum);
        //debug - הצגת המספר לצורך בדיקה
//        Toast.makeText(GuessNumberActivity.this, "New Random Number is:" + randomNumber, Toast.LENGTH_SHORT).show();
        //ניקוי תיבת הניחושים
        guessNumberInput.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //בעת לחיצה על כפתור ניחוש תיקרא מתודת btnGuessAction
            case R.id.btnGuess:
                btnGuessAction();
                break;
            //בעת לחיצה על איפוס המשחק תיקרא מתודת btnResetAction
            case R.id.btnReset:
                btnResetAction();
                break;
            case R.id.btnGuessToMenu:
                startActivity(new Intent(GuessNumberActivity.this, HomeActivity.class));
                break;
                //הצגת הוראות המשחק
            case R.id.imgHelp:
                ShowDialog();
                break;
        }
    }

    //מתודה לאיפוס המשחק
    private void btnResetAction() {
        level = 1;
        btnGuess.setEnabled(true);
        createGameByLevel();
    }

    //מתודת ניחוש מספר
    private void btnGuessAction() {
        //אם לא הוזן מספר - הצגת הודעה מתאימה
        if (TextUtils.isEmpty(guessNumberInput.getText().toString())) {
            guessNumberInput.setError("Empty");
        }
        else {
            //בדיקה שנותרו ניחושים והצגתם לשחקן
            if (remainGuesses != 0) {
                remainGuesses--;
                remainGuessLabel.setText("Remain Guesses is:" + remainGuesses);
                //אם ניחש מספר נכון - מעבר לרמה הבאה
                if (randomNumber == Integer.parseInt(guessNumberInput.getText().toString())) {
                    Toast.makeText(GuessNumberActivity.this, "Great, Moving to the next level!", Toast.LENGTH_SHORT).show();
                    level++;
                    //בדיקה שהגיע לרמה המקסימלית - במידה וכן הצג הודעת ניצחון והשמע צליל
                    if (level == 5) {
                        // winning case
                        Toast.makeText(GuessNumberActivity.this, "You Win!", Toast.LENGTH_SHORT).show();
                        winSound = MediaPlayer.create(GuessNumberActivity.this, R.raw.nice);
                        winSound.start();
                        //איפוס הרמות והתחלת משחק חדש
                        level = 1;
                    }
                    createGameByLevel();
                }
                // אם ניחש מספר גדול מהרנדיומלי - הצגת הודעה מתאימה
                else if (Integer.parseInt(guessNumberInput.getText().toString()) > randomNumber){
                    Toast.makeText(GuessNumberActivity.this, "Lower...", Toast.LENGTH_SHORT).show();
                }
                // כנל לגבי מספר קטן מהרנדיומלי
                else {
                    Toast.makeText(GuessNumberActivity.this, "Higher...", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                // אם לא נותרו ניחושים - הצגת הודעת הפסד והשמע סעונד מתאים
                Toast.makeText(GuessNumberActivity.this, "You Lose!!", Toast.LENGTH_SHORT).show();
                loseSound = MediaPlayer.create(GuessNumberActivity.this, R.raw.sorry);
                loseSound.start();
                btnGuess.setEnabled(false);
            }
        }
    }

    //הצגת כללי המשחק
    private void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("There are rules for the number guessing game: \n" +
                "Try to guess a number in the given range.\n" +
                "If you miss, you will receive a hint. \n" +
                "You have limited guesses, so be careful!\n" +
                "Good luck!");

        builder.show();
    }
}