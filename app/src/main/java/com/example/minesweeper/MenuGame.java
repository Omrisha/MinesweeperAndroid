package com.example.minesweeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MenuGame extends AppCompatActivity {

    public final static String GAME_LEVEL = "GAME_LEVEL";
    private String mLevel;
    RadioGroup mLevelsRadioGroup;
    RadioButton mLevelChooser;
    Button mStartGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_game);

        this.mLevelsRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        this.mStartGameButton = (Button) findViewById(R.id.butStartGame);

        this.mStartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
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

    private void showAlertWindow(String title, String message) {
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
