package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
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

      // ImageView mImageLoss = (ImageView) findViewById(R.id.im2);
        final ImageView mImageWin = (ImageView) findViewById(R.id.im1);

        FrameLayout f = (FrameLayout) findViewById(R.id.boom_fragment_container);
       BoomAnimationFragment boomFragment = new BoomAnimationFragment();

        if(status == false) {
            f.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.boom_fragment_container, boomFragment)
                    .commit();
        }
        else {
            mImageWin.setVisibility(View.VISIBLE);
            final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_run);
            mImageWin.startAnimation(zoomAnimation);
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