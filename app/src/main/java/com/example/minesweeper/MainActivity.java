package com.example.minesweeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.minesweeper.logic.Game;
import com.example.minesweeper.logic.Level;
import com.example.minesweeper.logic.Tile;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements SensorServiceListener {

    private static final String GAME_ORIENTATION = "GAME_ORIENTATION";
    private static final String TAG = "Main Game Activity";
    public final static String GAME_STATUS = "GAME_STATUS";
    private final static String GAME_TIME = "GAME_TIME";

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mGame = (Game)savedInstanceState.getSerializable(GAME_STATUS);
        mTimer.setBase(savedInstanceState.getLong(GAME_TIME));
        mBinder = (SensorService.SensorServiceBinder) savedInstanceState.getBinder(GAME_ORIENTATION);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(GAME_STATUS, mGame);
        outState.putLong(GAME_TIME, mTimer.getBase());
        outState.putBinder(GAME_ORIENTATION, mBinder);
        super.onSaveInstanceState(outState);
    }

    private String mLevelString;
    private Level mLevel;

    SensorService.SensorServiceBinder mBinder;
    boolean isBound = false;
    Game mGame;
    GridView mGridView;
    TileAdapter mTileAdapter;
    Chronometer mTimer;
    ImageView mLockRotation;
    LinearLayout mMainLayout;
    GradientDrawable mStroke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mLevelString = this.getIntent().getStringExtra(MenuGame.GAME_LEVEL);

        // Init widgets
        mGridView = findViewById(R.id.gridView);
        mTimer = findViewById(R.id.gameTimer);
        mLockRotation = findViewById(R.id.lock_img_view);
        mMainLayout = findViewById(R.id.main_screen);
        mStroke = new GradientDrawable();
        mStroke.setStroke(10, Color.RED);

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

        mGridView.setOnItemClickListener(getIllegal_move());

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mGame.flagATile(position);
                mTileAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private AdapterView.OnItemClickListener getIllegal_move() {
        return new AdapterView.OnItemClickListener() {
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
        };
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
            mLockRotation.setVisibility(View.VISIBLE);
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

    CountDownTimer countDownTimer = new CountDownTimer(20000, 5000) {
        @Override
        public void onTick(long millisUntilFinished) {
            Toast.makeText(getApplicationContext(), "You change the device orientation, you have " + millisUntilFinished/1000 + " to change it back.", Toast.LENGTH_SHORT).show();
            mGame.handlePenalty();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTileAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onFinish() {
            this.start();
        }
    };

    @Override
    public void alarmStateChanged(ALARM_STATE state) {
        Log.d(TAG, "STATE: " + state);
        switch(state){
            case ON:
                countDownTimer.start();
                this.mMainLayout.setBackground(mStroke);
                break;
            case OFF:
                countDownTimer.cancel();
                this.mMainLayout.setBackground(null);
                break;
        }
    }
}
