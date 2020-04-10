package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EndGame extends AppCompatActivity {
    TextView mText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

      //  mText = (TextView) findViewById(R.id.textView);
       boolean status = getIntent().getBooleanExtra(MainActivity.GAME_STATUS, false);

        //String status = getIntent().getStringExtra(MainActivity.GAME_STATUS);
       // mText.setText(status);

       ImageView mImageLoss = (ImageView) findViewById(R.id.im2);
       ImageView mImageWin = (ImageView) findViewById(R.id.im1);


        if(status == false) {
            mImageLoss.setVisibility(View.VISIBLE);
        }
        else {
            mImageWin.setVisibility(View.VISIBLE);
        }

        //Exit Button
        findViewById(R.id.buttonExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); // Close all activites
                System.exit(0);  // Releasing resources
            }
        });


        //Play again Button
        findViewById(R.id.buttonPlayAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndGame.this,MenuGame.class);
                startActivity(intent);
            }
        });

    }
}