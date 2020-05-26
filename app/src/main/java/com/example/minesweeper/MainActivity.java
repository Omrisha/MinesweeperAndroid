package com.example.minesweeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minesweeper.logic.Game;
import com.example.minesweeper.logic.Level;
import com.example.minesweeper.logic.Tile;
import com.example.minesweeper.logic.TileType;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Game Activity";
    public final static String GAME_STATUS = "GAME_STATUS";

    private String mLevelString;
    private Level mLevel;

    Game mGame;
    GridView mGridView;
    TileAdapter mTileAdapter;
    Chronometer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mLevelString = this.getIntent().getStringExtra(MenuGame.GAME_LEVEL);
        Log.d(TAG, this.mLevelString);

        // Init widgets
        mGridView = findViewById(R.id.gridView);
        mTimer = findViewById(R.id.gameTimer);
        mTimer.start();

        this.mLevel = Level.valueOf(this.mLevelString.toUpperCase());
        mGame = new Game(this.mLevel);

        mTileAdapter = new TileAdapter(mGame.getmBoard(),this);
        mGridView.setNumColumns(this.mGame.getmBoard().getCols());
        mGridView.setAdapter(mTileAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Thread t = new Thread(new Runnable() {
                    Boolean isMine = false;
                    @Override
                    public void run() {
                        Boolean isFlagged = mGame.getmBoard().chooseTile(position).getmIsFlagged();
                        if (!isFlagged) {
                            isMine = mGame.getmBoard().playTile(position);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTileAdapter.notifyDataSetChanged();
                                }
                            });

                            if (isMine) {
                                mGame.getmBoard().revealBoard();
                                waitForEndScreen(1800);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        goToEndActivity(false);
                                    }
                                });
                            }
                        } else {
                            showAlertWindow("Illegal move", "You chose a flagged tile, please unflag it before choosing it.");
                        }
                        if (mGame.getmBoard().isGameOver()) {
                            mGame.getmBoard().revealBoard();
                            waitForEndScreen(1800);
                            goToEndActivity(true);
                        }
                    }
                });

                t.start();
            }
        });

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Tile tile = mGame.getmBoard().chooseTile(position);
                tile.setmIsFlagged(!tile.getmIsFlagged());
                mTileAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void waitForEndScreen(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.mTimer != null) {
            Log.d(TAG, "Stop timer.");
            this.mTimer.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.mTimer != null) {
            Log.d(TAG, "Resume timer.");
            this.mTimer.start();
        }
    }

    private void goToEndActivity(boolean status) {
        Intent intent = new Intent(getBaseContext(), EndGame.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(GAME_STATUS, status);
        startActivity(intent);
        this.finish();
    }


    private void showAlertWindow(String title, String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (!isFinishing()){
                    new AlertDialog.Builder(MainActivity.this)
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
