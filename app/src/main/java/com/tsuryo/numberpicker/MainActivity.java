package com.tsuryo.numberpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.tsuryo.numberpickerlib.NumberPicker;

public class MainActivity extends AppCompatActivity
        implements NumberPicker.Listener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private NumberPicker mNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumberPicker = findViewById(R.id.numberPicker);
        mNumberPicker.setListener(this);
    }

    @Override
    public void onNumberChange(int num) {
        //returns the current number on + / - click
        Log.d(TAG, String.valueOf(num));
    }
}
