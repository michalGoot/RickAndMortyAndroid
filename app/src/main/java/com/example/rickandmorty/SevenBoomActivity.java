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
import android.widget.Toast;

public class SevenBoomActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnBoom, btnMenu;
    ImageView imgHelp;
    Integer counter = 0;
    TextView showNum;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player=MediaPlayer.create(SevenBoomActivity.this,R.raw.goodidea);
        setContentView(R.layout.activity_seven_boom);
        initViews();
        initButtons();
        //הצגת ההוראות בעת לחיצה על התמונה של העזרה
        imgHelp.setOnClickListener(view -> {
            ShowDialog();
        });
        //הצגת מספר הלחיצות הנוכחי
        showNum.setText("Your Number Is: "+counter);

    }


    private void initViews() {
        imgHelp = findViewById(R.id.imgHelp);
        showNum = findViewById(R.id.showNum);
        btnBoom = findViewById(R.id.boomBtn);
        btnMenu = findViewById(R.id.btn7BoomToMenu);
    }
    private void initButtons() {
        btnBoom.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //אם לחצנו על btnBoom
        if(view == btnBoom) {
            //העלאת המונה ב1 ובדיקה אם הוא מתחלק ב7 או מכיל את הספרה 7
            counter++;
            //במידה וכן - הצג "בום"
            if(counter % 7 == 0 || counter.toString().contains("7")){
                player.start();
                showNum.setText(counter + " - 💣 I`m Boombastic 💣");
                Toast.makeText(SevenBoomActivity.this, "Seven Boom!",Toast.LENGTH_SHORT).show();
            }
            else
                //כל עוד המספר 'רגיל' - הצג הודעה מתאימה
                showNum.setText(counter + " - it's not a bombastic number");
        }
        //בעת לחיצה על הכפתור btnMenu מעבר לתפריט המשחקים
        else if (view == btnMenu){
            startActivity(new Intent(SevenBoomActivity.this, HomeActivity.class));
        }
    }
    //הצגת הוראות
    private void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("There are rules for the seven boom game: \n" +
                "Click the button and watch out for multiples of sevens\n" +
                "Digits of 7 are also a good idea" );
        builder.show();
    }
}
