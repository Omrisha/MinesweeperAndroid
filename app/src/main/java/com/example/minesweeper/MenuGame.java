package com.example.minesweeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MenuGame extends AppCompatActivity {

    final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";
    SharedPreferences sharedPreferences;
    public final static String GAME_LEVEL = "GAME_LEVEL";
    private String mLevel;
    RadioGroup mLevelsRadioGroup;
    RadioButton mLevelChooser;
    Button mStartGameButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_game);
        sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);

        this.mLevelsRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        this.mStartGameButton = (Button) findViewById(R.id.butStartGame);

        mLevelsRadioGroup.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
        LoadPreferences();

        this.mStartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        //Record List Button
        ImageView recordBut = (ImageView)findViewById(R.id.butRecordList);
        recordBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON","****** click on button record list ******");

                FragmentManager fm = getSupportFragmentManager();
                RecordListFragment fragment = new RecordListFragment();

                fm.beginTransaction().add(R.id.container , fragment).commit();
            }
        });
    }

    RadioGroup.OnCheckedChangeListener radioGroupOnCheckedChangeListener =
            new RadioGroup.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton checkedRadioButton = (RadioButton)mLevelsRadioGroup.findViewById(checkedId);
                    int checkedIndex = mLevelsRadioGroup.indexOfChild(checkedRadioButton);
                    SavePreferences(KEY_SAVED_RADIO_BUTTON_INDEX, checkedIndex);
                }};



    private void SavePreferences(String key, int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    private void LoadPreferences(){
        int savedRadioIndex = sharedPreferences.getInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0);
        RadioButton savedCheckedRadioButton = (RadioButton)mLevelsRadioGroup.getChildAt(savedRadioIndex);
        savedCheckedRadioButton.setChecked(true);
    }

    private void startGame() {
        // get selected radio button from radioGroup
        int selectedId = mLevelsRadioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        mLevelChooser = (RadioButton) findViewById(selectedId);
        if (mLevelChooser != null) {
            this.mLevel = mLevelChooser.getText().toString();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra(GAME_LEVEL, this.mLevel);
            startActivity(intent);
        } else {
            showAlertWindow("Error", "You didn't choose a difficulty level.");
        }
    }

    private void showAlertWindow(final String title, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (!isFinishing()){
                    new AlertDialog.Builder(MenuGame.this)
                            .setTitle(title)
                            .setMessage(message)
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });
    }
}
