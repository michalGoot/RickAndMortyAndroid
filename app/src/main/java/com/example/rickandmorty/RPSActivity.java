package com.example.rickandmorty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class RPSActivity extends AppCompatActivity {
    ImageView imgUserPaper, imgUserRock, imgUserScissors;
    ImageView imgComputerChoice;
    ImageView imgHelp;
    TextView txtResultMessage;
    MediaPlayer winSound, loseSound, tieSound;
    Button btnReady, btnRestart, btnMenu;
    int choice = 0; //0 - No, 1 - Rock, 2 - Paper, 3 - Scissors
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rps_activity);
        initViews();
        initGame();
        //הצגת ההוראות
        imgHelp.setOnClickListener(view -> {
            ShowDialog();
        });
        //בחירת אבן
        imgUserRock.setOnClickListener(view -> {
            imgUserRock.setBackground(getDrawable(R.color.player_selected));
            imgUserPaper.setBackground(getDrawable(R.color.white));
            imgUserScissors.setBackground(getDrawable(R.color.white));
            btnReady.setEnabled(true);
            choice = 1;
        });
        //בחירת נייר
        imgUserPaper.setOnClickListener(view -> {
            imgUserPaper.setBackground(getDrawable(R.color.player_selected));
            imgUserRock.setBackground(getDrawable(R.color.white));
            imgUserScissors.setBackground(getDrawable(R.color.white));
            btnReady.setEnabled(true);
            choice = 2;
        });
        //בחירת מספריים
        imgUserScissors.setOnClickListener(view -> {
            imgUserScissors.setBackground(getDrawable(R.color.player_selected));
            imgUserRock.setBackground(getDrawable(R.color.white));
            imgUserPaper.setBackground(getDrawable(R.color.white));
            btnReady.setEnabled(true);
            choice = 3;
        });
        //לחיצה על ready
        btnReady.setOnClickListener(view -> {
            //הגרלת מספר רנדיומלי בין 1 ל3 (מהלך של המחשב)
            int randomNum = random.nextInt(3) + 1;
            switch (randomNum) {
                //השוואת בחירת השחקן אל מול בחירת המחשב
                case 1: //Computer rock
                    imgComputerChoice.setImageResource(R.drawable.rock);
                    imgComputerChoice.setBackground(getDrawable(R.color.pc_selected));
                    if(choice == 1) //player rock to computer rock - tie
                    {
                        txtResultMessage.setText("Tie...");
                        tieSound = MediaPlayer.create(RPSActivity.this, R.raw.rpstiesound);
                        tieSound.start();
                    }
                    else if (choice == 2) //player Paper to computer rock - win
                    {
                        winSound = MediaPlayer.create(RPSActivity.this, R.raw.rpswinningsound);
                        winSound.start();
                        txtResultMessage.setText("You Win🥳🎊🎉🥓🧀");
                    }

                    else if (choice == 3) //player Scissors to computer rock - lose
                    {
                        txtResultMessage.setText("You Lose 👿");
                        loseSound = MediaPlayer.create(RPSActivity.this, R.raw.rpslosingsound);
                        loseSound.start();
                    }

                    break;
                case 2: //Computer paper
                    imgComputerChoice.setImageResource(R.drawable.hand);
                    imgComputerChoice.setBackground(getDrawable(R.color.pc_selected));
                    if(choice == 1)//rock to computer paper - lose
                    {
                        txtResultMessage.setText("You Lose 👿");
                        loseSound = MediaPlayer.create(RPSActivity.this, R.raw.rpslosingsound);
                        loseSound.start();
                    }
                    else if (choice == 2)//Paper to computer paper - tie
                    {
                        txtResultMessage.setText("Tie...");
                        tieSound = MediaPlayer.create(RPSActivity.this, R.raw.rpstiesound);
                        tieSound.start();

                    }

                    else if (choice == 3) ////Scissors to computer paper - win
                    {
                        winSound = MediaPlayer.create(RPSActivity.this, R.raw.rpswinningsound);
                        winSound.start();
                        txtResultMessage.setText("You Win🥳🎊🎉🥓🧀");
                    }
                    break;
                case 3://Computer scissors
                    imgComputerChoice.setImageResource(R.drawable.scissors);
                    imgComputerChoice.setBackground(getDrawable(R.color.pc_selected));
                    if(choice == 1) // Rock to Computer scissors - win
                    {
                        winSound = MediaPlayer.create(RPSActivity.this, R.raw.rpswinningsound);
                        winSound.start();
                        txtResultMessage.setText("You Win🥳🎊🎉🥓🧀");
                    }
                    else if (choice == 2) // Paper to Computer scissors - lose
                    {
                        txtResultMessage.setText("You Lose 👿");
                        loseSound = MediaPlayer.create(RPSActivity.this, R.raw.rpslosingsound);
                        loseSound.start();
                    }
                    else if (choice == 3) //Scissors to Computer scissors - tie
                    {
                        txtResultMessage.setText("Tie...");
                        tieSound = MediaPlayer.create(RPSActivity.this, R.raw.rpstiesound);
                        tieSound.start();
                    }
                    break;
            }
            btnReady.setEnabled(false);
        });
        //איפוס משחק
        btnRestart.setOnClickListener(view -> {
            initGame();
        });
        //מעבר לתפריט המשחקים
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RPSActivity.this, HomeActivity.class));
            }
        });
    }
    private void initViews() {
        imgHelp = findViewById(R.id.imgHelp);
        imgUserPaper = findViewById(R.id.player_paper);
        imgUserRock = findViewById(R.id.player_rock);
        imgUserScissors = findViewById(R.id.player_scissors);
        imgComputerChoice = findViewById(R.id.enemy_choice);
        txtResultMessage = findViewById(R.id.rps_text);
        btnReady = findViewById(R.id.rps_ready);
        btnRestart = findViewById(R.id.rps_restart);
        btnMenu = findViewById(R.id.btnRpsToMenu);
    }
    //אתחול המשחק
    private void initGame() {
        choice = 0;
        imgComputerChoice.setBackground(getDrawable(R.color.white));
        imgUserPaper.setBackground(getDrawable(R.color.white));
        imgUserRock.setBackground(getDrawable(R.color.white));
        imgUserScissors.setBackground(getDrawable(R.color.white));
        txtResultMessage.setText("Select Your Choice!");
        imgComputerChoice.setImageDrawable(null);
        btnReady.setEnabled(false);
    }

    //הצגת ההוראות
    private void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("There are rules for the RPS game: \n" +
                "Select your choice from the left panel\n" +
                "Then click 'Ready'\n" +
                "Try to beat the computer!\n" +
                "Press 'Restart' if you wish to start over.\n" +
                "Good luck!");

        builder.show();
    }
}