package com.example.minesweeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BreakRecordFragment extends Fragment {

    public static final String SHARED_PREFS = "SharedPrefs";

    private String myKey;
    private String name;
    private String time;
    private EditText editName;

    public BreakRecordFragment() {
        // Required empty public constructor
    }


    public void saveData(String key) {

        Log.d("KEY_AT_FRAGMENT", "*********" + key + "***********");

        editName = getView().findViewById(R.id.editTextName);
        name = editName.getText().toString();

        Log.d("NAME", name);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, name);
        editor.apply();
        Toast.makeText(getActivity() , "your record saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myKey = this.getArguments().getString("recordBreakKey");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_break_record, container, false);

        ImageView image = (ImageView)view.findViewById(R.id.imT);
        playAmination(image);

        Button bSave = (Button) view.findViewById(R.id.bottomSaveRecord);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "save BUTTON PRESSED");

                if(isEmpty(getView().findViewById(R.id.editTextName)) == true) {
                    Toast.makeText(getActivity() , "Enter your name", Toast.LENGTH_SHORT).show();
                }
                else {
                    saveData(myKey);
                    getFragmentManager().beginTransaction().remove(BreakRecordFragment.this).commit(); //exit from fragment
                }
            }
        });




        return view;
    }

    private void playAmination(ImageView image) {
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(1000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        image.startAnimation(animation); //to start animation
    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }



    public void callParentMethod(){
        getActivity().onBackPressed();
    }
}
