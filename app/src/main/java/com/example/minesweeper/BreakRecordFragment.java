package com.example.minesweeper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class BreakRecordFragment extends Fragment {

    public BreakRecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_break_record, container, false);
        Button b = (Button) view.findViewById(R.id.buttonExit);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "exit BUTTON PRESSED");

                // if (CreateUserFragment.this.mListener != null) {
//                     CreateUserFragment.this.mListener.viewAnimationRequested();

//                //}
//
            }

        });

        return view;
    }
}
