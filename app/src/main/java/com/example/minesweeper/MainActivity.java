package com.example.minesweeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.GridView;

import com.example.minesweeper.logic.Game;
import com.example.minesweeper.logic.Level;
import com.example.minesweeper.logic.Tile;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements SensorServiceListener {

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mGame = (Game)savedInstanceState.getSerializable(GAME_STATUS);
        mTimer.setText(savedInstanceState.getString(GAME_TIME));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(GAME_STATUS, mGame);
        outState.putLong(GAME_TIME, mTimer.getBase());
        super.onSaveInstanceState(outState);
    }

    private static final String TAG = "Main Game Activity";
    public final static String GAME_STATUS = "GAME_STATUS";
    public final static String GAME_TIME = "GAME_TIME";

    private String mLevelString;
    private Level mLevel;

    SensorService.SensorServiceBinder mBinder;
    boolean isBound = false;
    Game mGame;
    GridView mGridView;
    TileAdapter mTileAdapter;
    Chronometer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mLevelString = this.getIntent().getStringExtra(MenuGame.GAME_LEVEL);

        // Init widgets
        mGridView = findViewById(R.id.gridView);
        mTimer = findViewById(R.id.gameTimer);

        this.mLevel = Level.valueOf(this.mLevelString.toUpperCase());

        if (savedInstanceState != null) {
            mGame = (Game) savedInstanceState.getSerializable(GAME_STATUS);
            mTimer.setBase(savedInstanceState.getLong(GAME_TIME));
        } else {
            mGame = new Game(this.mLevel);
        }

        mTimer.start();
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
    protected void onStart() {
        super.onStart();

        Intent serviceIntent = new Intent(this, SensorService.class);
        Log.d(TAG, "binding sensors service");
        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (isBound) {
            unbindService(mConnection);
            isBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("Service Connection", "bound to service");
            mBinder = (SensorService.SensorServiceBinder) service;
            mBinder.registerListener(MainActivity.this);
            Log.d("Service Connection", "registered as listener");
            isBound = true;
            mBinder.startSensors();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        if (isBound){
            mBinder.stopSensors();
        }

        if (this.mTimer != null) {
            Log.d(TAG, "Stop timer.");
            this.mTimer.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isBound){
            mBinder.startSensors();
        }

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

    @Override
    public void alarmStateChanged(ALARM_STATE state) {
        Log.d(TAG, "STATE: " + state);
    }
}
