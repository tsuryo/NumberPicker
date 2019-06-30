package com.tsuryo.numberpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity
        implements NumberPicker.Listener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setListener(this);
    }

    @Override
    public void onNumberChange(int num) {
        //returns the current number on + / - click
        Log.d(TAG, String.valueOf(num));
    }
}
