package com.example.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.minesweeper.logic.Board;
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

        Tile tile = mBoard.chooseTile(position);
        if (tile.getmIsRevealed()) {
            if (tile.getmType().equals(TileType.MINE)) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
                tileView.mTextView.setBackgroundResource(R.drawable.mine);
                tileView.mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tileView.mTextView.setLayoutParams(layoutParams);
            } else {
                tileView.setText(tile.getmType().toString());
                setTileColorForNumber(tileView, tile);

            }
            tileView.setBackgroundColor(Color.GRAY);
        } else {
            tileView.setText("");
            tileView.setBackgroundColor(Color.DKGRAY);
        }

        if (tile.getmIsFlagged()) {
            tileView.setBackgroundColor(Color.RED);
        }
        Log.d(TAG, "View returned " + position);
        return tileView;
    }

    private void setTileColorForNumber(TileView tileView, Tile tile) {
        switch (tile.getmType()) {
            case ONE:
                tileView.mTextView.setTextColor(Color.BLUE);
                break;
            case TWO:
                tileView.mTextView.setTextColor(Color.GREEN);
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
