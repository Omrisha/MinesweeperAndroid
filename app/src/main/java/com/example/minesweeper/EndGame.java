package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EndGame extends AppCompatActivity {
    TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        mText = (TextView) findViewById(R.id.textView);
        String status = getIntent().getStringExtra(MainActivity.GAME_STATUS);
        mText.setText(status);

        //Exit Button
        findViewById(R.id.buttonExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
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