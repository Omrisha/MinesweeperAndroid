package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minesweeper.logic.Level;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class EndGame extends AppCompatActivity {
    TextView mText;
    private String recordBreakKey;

    public static final String SHARED_PREFS = "SharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);


        //  mText = (TextView) findViewById(R.id.textView);
        boolean status = getIntent().getBooleanExtra(MainActivity.GAME_STATUS, false);

        long time = (SystemClock.elapsedRealtime()) - (getIntent().getLongExtra(MainActivity.GAME_TIME, 10000));
        Log.d("TIME***********", time + "");
        String level = getIntent().getStringExtra(MainActivity.GAME_LEVEL);


        //String status = getIntent().getStringExtra(MainActivity.GAME_STATUS);
        // mText.setText(status);

        // ImageView mImageLoss = (ImageView) findViewById(R.id.im2);
        final ImageView mImageWin = (ImageView) findViewById(R.id.im1);

        FrameLayout f = (FrameLayout) findViewById(R.id.boom_fragment_container);
        BoomAnimationFragment boomFragment = new BoomAnimationFragment();

        if (status == false) {
            f.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.boom_fragment_container, boomFragment)
                    .commit();

        } else {
            mImageWin.setVisibility(View.VISIBLE);
            final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_run);
            mImageWin.startAnimation(zoomAnimation);


            recordBreakKey = checkIfBreakRecord(level, time);
            //recordBreakKey = Keys.BEGINNER_1_NAME.name();

            Log.d("RECORD_BREAK_KEY", recordBreakKey + "");


            if (recordBreakKey != null) {

                Log.d("IF", "Inside if (recordBreakKey != null)");
                Bundle bundle = new Bundle();
                bundle.putString("recordBreakKey", recordBreakKey);
                BreakRecordFragment fragInfo = new BreakRecordFragment();
                fragInfo.setArguments(bundle);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().add(R.id.container2, fragInfo).commit();
            }
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
                Intent intent = new Intent(EndGame.this, MenuGame.class);
                startActivity(intent);
            }
        });

    }

//    public void saveData(String name_time, String place, String level) {
//
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        String key = level.toUpperCase() +"_"+place;
//
//        editor.putString(Keys., domainCar);
//        editor.putString(CAR_ID, idCar);
//        editor.apply();
//        Toast.makeText(this , "data save", Toast.LENGTH_SHORT).show();
//    }
//
//
//    public void loadData() {
//        SharedPreferences sharedPreferences = getSharedPreferences(CreateUserActivity.SHARED_PREFS, MODE_PRIVATE);
//        this.elementCarDomain =
//                sharedPreferences.getString(Keys.BEGINNER_1.name(), "10000");
//        this.elementCarId = sharedPreferences.getString(CreateUserActivity.CAR_ID, "-1");
//
//    }

    private String checkIfBreakRecord(String level, long time) {
        Log.d("INTO FUNC", level + " , " + time);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString(Keys.BEGINNER_1.name(), "");
        long time1 = -1;
        long time2 = -1;
        long time3 = -1;

        String name1 = "";
        String name2 = "";
        String name3 = "";

        Log.d("LEVEL", Level.BEGINNER.name());

        if (level.toUpperCase().equals(Level.BEGINNER.name())) {
            time1 = sharedPreferences.getLong(Keys.BEGINNER_1_TIME.name(), -1);
            time2 = sharedPreferences.getLong(Keys.BEGINNER_2_TIME.name(), -1);
            time3 = sharedPreferences.getLong(Keys.BEGINNER_3_TIME.name(), -1);

            name1 = sharedPreferences.getString(Keys.BEGINNER_1_NAME.name(), "null");
            name2 = sharedPreferences.getString(Keys.BEGINNER_2_NAME.name(), "null");


            if (time < time1 || time1 == -1) {
                Log.d("IF1", "if  if (time < time1 || time1 == -1)");
                //place2 to place3
                editor.putLong(Keys.BEGINNER_3_TIME.name(), time2);
                editor.putString(Keys.BEGINNER_3_NAME.name(), name2);

                //place1 to place2
                editor.putLong(Keys.BEGINNER_2_TIME.name(), time1);
                editor.putString(Keys.BEGINNER_2_NAME.name(), name1);

                editor.putLong(Keys.BEGINNER_1_TIME.name(), time);
                editor.apply();
                return Keys.BEGINNER_1_NAME.name();

            } else if (time < time2 || time2 == -1) {
                Log.d("IF2", "else if (time < time2 || time2 == -1)");
                //place2 to place3
                editor.putLong(Keys.BEGINNER_3_TIME.name(), time2);
                editor.putString(Keys.BEGINNER_3_NAME.name(), name2);

                editor.putLong(Keys.BEGINNER_2_TIME.name(), time);
                editor.apply();
                return Keys.BEGINNER_2_NAME.name();

            } else if (time < time3 || time3 == -1) {
                Log.d("IF3", "else if (time < time3 || time3 == -1)");
                editor.putLong(Keys.BEGINNER_3_TIME.name(), time);
                editor.apply();
                return Keys.BEGINNER_3_NAME.name();
            }

        } else if (level.equals(Level.INTERMEDIATE.name())) {
            time1 = sharedPreferences.getLong(Keys.INTERMEDIATE_1_TIME.name(), -1);
            time2 = sharedPreferences.getLong(Keys.INTERMEDIATE_2_TIME.name(), -1);
            time3 = sharedPreferences.getLong(Keys.INTERMEDIATE_3_TIME.name(), -1);

            name1 = sharedPreferences.getString(Keys.INTERMEDIATE_1_NAME.name(), "null");
            name2 = sharedPreferences.getString(Keys.INTERMEDIATE_2_NAME.name(), "null");

            if (time < time1 || time1 == -1) {
                //place2 to place3
                editor.putLong(Keys.BEGINNER_3_TIME.name(), time2);
                editor.putString(Keys.BEGINNER_3_NAME.name(), name2);
                //place1 to place2
                editor.putLong(Keys.BEGINNER_2_TIME.name(), time1);
                editor.putString(Keys.BEGINNER_2_NAME.name(), name1);

                editor.putLong(Keys.BEGINNER_1_TIME.name(), time);
                editor.apply();
                return Keys.BEGINNER_1_NAME.name();

            } else if (time < time2 || time2 == -1) {
                //place2 to place3
                editor.putLong(Keys.BEGINNER_3_TIME.name(), time2);
                editor.putString(Keys.BEGINNER_3_NAME.name(), name2);

                editor.putLong(Keys.BEGINNER_2_TIME.name(), time);
                editor.apply();
                return Keys.BEGINNER_2_NAME.name();

            } else if (time < time3 || time3 == -1) {
                editor.putLong(Keys.BEGINNER_3_TIME.name(), time);
                editor.apply();
                return Keys.BEGINNER_3_NAME.name();
            }

        } else {
            time1 = sharedPreferences.getLong(Keys.EXPERT_1_TIME.name(), -1);
            time2 = sharedPreferences.getLong(Keys.EXPERT_2_TIME.name(), -1);
            time3 = sharedPreferences.getLong(Keys.EXPERT_3_TIME.name(), -1);

            name1 = sharedPreferences.getString(Keys.EXPERT_1_NAME.name(), "null");
            name2 = sharedPreferences.getString(Keys.EXPERT_2_NAME.name(), "null");

            if (time < time1 || time1 == -1) {
                //place2 to place3
                editor.putLong(Keys.BEGINNER_3_TIME.name(), time2);
                editor.putString(Keys.BEGINNER_3_NAME.name(), name2);
                //place1 to place2
                editor.putLong(Keys.BEGINNER_2_TIME.name(), time1);
                editor.putString(Keys.BEGINNER_2_NAME.name(), name1);

                editor.putLong(Keys.BEGINNER_1_TIME.name(), time);
                editor.apply();
                return Keys.BEGINNER_1_NAME.name();

            } else if (time < time2 || time2 == -1) {
                //place2 to place3
                editor.putLong(Keys.BEGINNER_3_TIME.name(), time2);
                editor.putString(Keys.BEGINNER_3_NAME.name(), name2);

                editor.putLong(Keys.BEGINNER_2_TIME.name(), time);
                editor.apply();
                return Keys.BEGINNER_2_NAME.name();

            } else if (time < time3 || time3 == -1) {
                editor.putLong(Keys.BEGINNER_3_TIME.name(), time);
                editor.apply();
                return Keys.BEGINNER_3_NAME.name();
            }
        }
        return null;
    }



}