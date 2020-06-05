package com.example.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.minesweeper.logic.Board;
import com.example.minesweeper.logic.Level;
import com.example.minesweeper.logic.Tile;
import com.example.minesweeper.logic.TileType;

public class TileAdapter extends BaseAdapter {
    private static final String TAG = "Tile Adapter";
    private Board mBoard;
    private Context mContext;

    public TileAdapter(Board mBoard, Context mContext) {
        this.mBoard = mBoard;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return this.mBoard.getCount();
    }

    @Override
    public Object getItem(int position) {
        return this.mBoard.chooseTile(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TileView tileView;

        tileView = (TileView)convertView;
        if(tileView == null) {
            Log.d(TAG, "View created " + position);
            tileView = new TileView(mContext);
        }
        LinearLayout.LayoutParams layoutParams = getLayoutParams(tileView);
        Tile tile = mBoard.chooseTile(position);
        if (tile.getmIsRevealed()) {
            if (tile.getmType().equals(TileType.MINE)) {
                tileView.mTextView.setBackgroundResource(R.drawable.mine);
                tileView.mTextView.setLayoutParams(layoutParams);
            } else {
                tileView.setText(tile.getmType().toString());
                setTileColorForNumber(tileView, tile);
                tileView.mTextView.setBackgroundResource(0);
            }
            tileView.setBackgroundColor(Color.WHITE);
        } else {
            if (tile.getmIsFlagged()) {
                tileView.mTextView.setBackgroundResource(R.drawable.flag_red);
                tileView.mTextView.setLayoutParams(layoutParams);
            } else {
                tileView.mTextView.setBackgroundResource(0);
                tileView.setText("");
                tileView.setBackgroundColor(Color.parseColor("#a0a0a0"));
            }
        }


        Log.d(TAG, "View returned " + position);
        return tileView;
    }

    private LinearLayout.LayoutParams getLayoutParams(TileView tileView) {
        LinearLayout.LayoutParams layoutParams = null;
        switch(mBoard.getmLevel()) {
            case BEGINNER:
                layoutParams = new LinearLayout.LayoutParams(130, 100);
                tileView.mTextView.setTextSize(25);
                break;
            case INTERMEDIATE:
                layoutParams = new LinearLayout.LayoutParams(78, 60);
                tileView.mTextView.setTextSize(15);
                break;
            case EXPERT:
                layoutParams = new LinearLayout.LayoutParams(52, 40);
                tileView.mTextView.setTextSize(10);
                break;
        }
        return layoutParams;
    }

    private void setTileColorForNumber(TileView tileView, Tile tile) {
        switch (tile.getmType()) {
            case ONE:
                tileView.mTextView.setTextColor(Color.BLUE);
                break;
            case TWO:
                tileView.mTextView.setTextColor(Color.parseColor("#00cc00"));
                break;
            case THREE:
                tileView.mTextView.setTextColor(Color.RED);
                break;
            case FOUR:
                tileView.mTextView.setTextColor(Color.rgb(0,0,139));
                break;
            case FIVE:
                tileView.mTextView.setTextColor(Color.rgb(165,42,42));
                break;
            case SIX:
                tileView.mTextView.setTextColor(Color.rgb(64,224,208));
                break;
            case SEVEN:
                tileView.mTextView.setTextColor(Color.BLACK);
                break;
            case EIGHT:
                tileView.mTextView.setTextColor(Color.DKGRAY);
                break;
        }
    }
}
