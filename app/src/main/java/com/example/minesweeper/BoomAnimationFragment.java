package com.example.minesweeper;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoomAnimationFragment extends Fragment {

    public BoomAnimationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_boom_animation, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        ((AnimationDrawable)getView().findViewById(R.id.boom).getBackground()).start();

    }
}
