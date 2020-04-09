package com.example.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.minesweeper.logic.Board;
import com.example.minesweeper.logic.Tile;

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
            tileView.setText(tile.getmType().toString());
            tileView.setBackgroundColor(Color.DKGRAY);
        } else {
            tileView.setText("");
            tileView.setBackgroundColor(Color.GRAY);
        }

        if (tile.getmIsFlagged()) {
            tileView.setBackgroundColor(Color.RED);
        }
        Log.d(TAG, "View returned " + position);
        return tileView;
    }
}
