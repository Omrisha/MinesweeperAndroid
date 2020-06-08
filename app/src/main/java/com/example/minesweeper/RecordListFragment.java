package com.example.minesweeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordListFragment extends Fragment {

    private final String SHARED_PREFS = "SharedPrefs";


    public RecordListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_list, container, false);

        //loadData
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        printResultBeginner(sharedPreferences, view);
        printResultIntermediate(sharedPreferences, view);
        printResultExpert(sharedPreferences, view);


        Button b = (Button) view.findViewById(R.id.buttonExit);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "exit BUTTON PRESSED");
                getFragmentManager().beginTransaction().remove(RecordListFragment.this).commit();
            }
        });
        return view;
    }





    public void printResultBeginner(SharedPreferences sharedPreferences, View v) {

        String b1_name= sharedPreferences.getString(Keys.BEGINNER_1_NAME.name(), "empty");
        String b1_time = (new SimpleDateFormat("mm:ss")).format(new Date
                (sharedPreferences.getLong(Keys.BEGINNER_1_TIME.name(), 0)));

        String b2_name = sharedPreferences.getString(Keys.BEGINNER_2_NAME.name(), "empty");
        String b2_time = (new SimpleDateFormat("mm:ss")).format(new Date
                (sharedPreferences.getLong(Keys.BEGINNER_2_TIME.name(), 0)));

        String b3_name = sharedPreferences.getString(Keys.BEGINNER_3_NAME.name(), "empty");
        String b3_time =  (new SimpleDateFormat("mm:ss")).format(new Date
                (sharedPreferences.getLong(Keys.BEGINNER_3_TIME.name(), 0)));


        ((TextView) v.findViewById(R.id.textView_Name_B1)).setText(b1_name);
        ((TextView) v.findViewById(R.id.textView_Time_B1)).setText(b1_time);
        ((TextView) v.findViewById(R.id.textView_Name_B2)).setText(b2_name);
        ((TextView) v.findViewById(R.id.textView_Time_B2)).setText(b2_time);
        ((TextView) v.findViewById(R.id.textView_Name_B3)).setText(b3_name);
        ((TextView) v.findViewById(R.id.textView_Time_B3)).setText(b3_time);
    }

    public void printResultIntermediate(SharedPreferences sharedPreferences, View v) {

        String I1_name = sharedPreferences.getString(Keys.INTERMEDIATE_1_NAME.name(), "empty");
        String I1_time =  (new SimpleDateFormat("mm:ss")).format(new Date
                (sharedPreferences.getLong(Keys.INTERMEDIATE_1_TIME.name(), 0)));
        String I2_name = sharedPreferences.getString(Keys.INTERMEDIATE_2_NAME.name(), "empty");
        String I2_time =  (new SimpleDateFormat("mm:ss")).format(new Date
                (sharedPreferences.getLong(Keys.INTERMEDIATE_2_TIME.name(), 0)));
        String I3_name = sharedPreferences.getString(Keys.INTERMEDIATE_3_NAME.name(), "empty");
        String I3_time = (new SimpleDateFormat("mm:ss")).format(new Date
                (sharedPreferences.getLong(Keys.INTERMEDIATE_3_TIME.name(), 0)));

        ((TextView) v.findViewById(R.id.textView_Name_I1)).setText(I1_name);
        ((TextView) v.findViewById(R.id.textView_Time_I1)).setText(I1_time);
        ((TextView) v.findViewById(R.id.textView_Name_I2)).setText(I2_name);
        ((TextView) v.findViewById(R.id.textView_Time_I2)).setText(I2_time);
        ((TextView) v.findViewById(R.id.textView_Name_I3)).setText(I3_name);
        ((TextView) v.findViewById(R.id.textView_Time_I3)).setText(I3_time);
    }

    public void printResultExpert(SharedPreferences sharedPreferences, View v) {

        String E1_name = sharedPreferences.getString(Keys.EXPERT_1_NAME.name(), "empty");
        String E1_time = (new SimpleDateFormat("mm:ss")).format(new Date
                (sharedPreferences.getLong(Keys.EXPERT_1_TIME.name(), 0)));
        String E2_name = sharedPreferences.getString(Keys.EXPERT_2_NAME.name(), "empty");
        String E2_time = (new SimpleDateFormat("mm:ss")).format(new Date
                (sharedPreferences.getLong(Keys.EXPERT_2_TIME.name(), 0)));
        String E3_name = sharedPreferences.getString(Keys.EXPERT_3_NAME.name(), "empty");
        String E3_time = (new SimpleDateFormat("mm:ss")).format(new Date
                (sharedPreferences.getLong(Keys.EXPERT_3_TIME.name(), 0)));

        ((TextView) v.findViewById(R.id.textView_Name_E1)).setText(E1_name);
        ((TextView) v.findViewById(R.id.textView_Time_E1)).setText(E1_time);
        ((TextView) v.findViewById(R.id.textView_Name_E2)).setText(E2_name);
        ((TextView) v.findViewById(R.id.textView_Time_E2)).setText(E2_time);
        ((TextView) v.findViewById(R.id.textView_Name_E3)).setText(E3_name);
        ((TextView) v.findViewById(R.id.textView_Time_E3)).setText(E3_time);
    }

}
