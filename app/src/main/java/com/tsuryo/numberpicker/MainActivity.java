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

        /*
         * Additional attributes:
         * */

        /*
        mNumberPicker.setBtnTextSize(30);
        mNumberPicker.setBtnTxtColor(R.color.colorAccent);

        mNumberPicker.setTextSize(30);
        mNumberPicker.setNumberTxtColor(R.color.colorPrimary);

        mNumberPicker.setCurrentValue(50); //set the current number
        mNumberPicker.setMaxValue(150);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setStartValue(5); //if !minValue < currentValue < maxValue, currentValue = max+min / 2
        */
    }

    @Override
    public void onNumberChange(int num) {
        //returns the current number on + / - click
        Log.d(TAG, String.valueOf(num));
    }
}
