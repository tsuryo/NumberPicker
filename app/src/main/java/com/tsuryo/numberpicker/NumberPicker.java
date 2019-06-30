package com.tsuryo.numberpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Timer;
import java.util.TimerTask;

import static com.tsuryo.numberpicker.Constants.NumberPicker.DELAY;
import static com.tsuryo.numberpicker.Constants.NumberPicker.FAST_PERIOD;
import static com.tsuryo.numberpicker.Constants.NumberPicker.MAX_VALUE_DEF;
import static com.tsuryo.numberpicker.Constants.NumberPicker.MIN_VALUE_DEF;
import static com.tsuryo.numberpicker.Constants.NumberPicker.SLOW_PERIOD;
import static com.tsuryo.numberpicker.Constants.NumberPicker.START_VALUE_DEF;

public class NumberPicker extends ConstraintLayout
        implements View.OnClickListener, View.OnLongClickListener {
    private boolean showText;
    private Integer startValue, minValue, maxValue, currentValue;
    private Integer numberTxtColor, btnTxtColor;

    private Button minusBtn, plusBtn;
    private TextView tvNumber;
    private Listener listener;

    //TODO: add setTextSize();
    public NumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.number_picker_layout, this, true);

        getAttributes(context, attrs);
        initViews();

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        tvNumber.setText(String.valueOf(currentValue));
        tvNumber.setTextColor(numberTxtColor);
        plusBtn.setTextColor(btnTxtColor);
        minusBtn.setTextColor(btnTxtColor);
        listener.onNumberChange(currentValue);
        super.onDraw(canvas);
    }

    private void getAttributes(Context context, AttributeSet attrs) {
        TypedArray tArr = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.NumberPicker,
                0, 0);
        try {
            this.showText = tArr.getBoolean(
                    R.styleable.NumberPicker_showText, true);
            this.btnTxtColor = tArr.getColor(
                    R.styleable.NumberPicker_btnTxtColor,
                    getResources().getColor(R.color.colorPrimary));
            this.numberTxtColor = tArr.getColor(
                    R.styleable.NumberPicker_numberTxtColor,
                    getResources().getColor(R.color.colorAccent));
            this.startValue = tArr.getInt(
                    R.styleable.NumberPicker_startValue,
                    START_VALUE_DEF);
            this.minValue = tArr.getInt(
                    R.styleable.NumberPicker_minValue,
                    MIN_VALUE_DEF);
            this.maxValue = tArr.getInt(
                    R.styleable.NumberPicker_maxValue,
                    MAX_VALUE_DEF);
            initCurrentNumber();
        } finally {
            tArr.recycle();
        }
    }

    private void initCurrentNumber() {
        if (minValue <= startValue
                && startValue <= maxValue) {
            this.currentValue = startValue;
        } else {
            currentValue = (maxValue + minValue) / 2;
        }
    }

    private void initViews() {
        minusBtn = findViewById(R.id.minusBtn);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn.setOnClickListener(this);
        plusBtn.setOnClickListener(this);
        plusBtn.setOnLongClickListener(this);
        minusBtn.setOnLongClickListener(this);
        tvNumber = findViewById(R.id.tvNumber);
    }

    /**
     * @return the tvNumber current value
     * if there is a problem with the TextView
     * will return START_VALUE_DEF
     */
    public int getValue() {
        try {
            return Integer.valueOf(tvNumber.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return START_VALUE_DEF;
        }
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
        tvNumber.setText(String.valueOf(currentValue));
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public boolean isShowText() {
        return showText;
    }

    public Integer getStartValue() {
        return startValue;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public Integer getNumberTxtColor() {
        return numberTxtColor;
    }

    public Integer getBtnTxtColor() {
        return btnTxtColor;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
        refresh();
    }

    public void setStartValue(Integer startValue) {
        this.startValue = startValue;
        refresh();
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
        refresh();
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
        refresh();
    }

    public void setNumberTxtColor(Integer numberTxtColor) {
        this.numberTxtColor = numberTxtColor;
        refresh();
    }

    public void setBtnTxtColor(Integer btnTxtColor) {
        this.btnTxtColor = btnTxtColor;
        refresh();
    }

    private void refresh() {
        invalidate();
        requestLayout();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.plusBtn
                && currentValue <= maxValue)
            currentValue++;
        else if (view.getId() == R.id.minusBtn
                && currentValue >= minValue)
            currentValue--;
        tvNumber.setText(String.valueOf(currentValue));
        listener.onNumberChange(currentValue);
    }

    @Override
    public boolean onLongClick(final View v) {
        final Handler h = new Handler(Looper.getMainLooper());
        final Timer timer = new Timer();
        final int[] counter = {0};

        final Runnable incrementTask = new Runnable() {
            @Override
            public void run() {
                switch (v.getId()) {
                    case R.id.plusBtn: {
                        if (currentValue <= maxValue)
                            currentValue++;
                        break;
                    }
                    case R.id.minusBtn: {
                        if (currentValue >= minValue)
                            currentValue--;
                        break;
                    }
                }
                tvNumber.setText(String.valueOf(currentValue));
                listener.onNumberChange(currentValue);
                counter[0]++;
            }
        };

        final TimerTask fastTask = new TimerTask() {
            @Override
            public void run() {
                if (v.isPressed())
                    h.post(incrementTask);
                else
                    timer.cancel();
            }
        };

        TimerTask slowTask = new TimerTask() {
            @Override
            public void run() {
                if (v.isPressed()) {
                    h.post(incrementTask);
                    if (counter[0] >= 3) {
                        this.cancel();
                        timer.schedule(fastTask, DELAY, FAST_PERIOD);
                    }
                } else
                    timer.cancel();

            }
        };
        timer.schedule(slowTask, DELAY, SLOW_PERIOD);

        return true;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, numberTxtColor, btnTxtColor);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setNumberTxtColor(savedState.getTxtColor());
        setBtnTxtColor(savedState.getBtnColor());
    }

    interface Listener {
        void onNumberChange(int num);
    }

    protected static class SavedState extends BaseSavedState {
        private final int txtColor;
        private final int btnColor;

        private SavedState(Parcelable superState, int txtColor, int number2) {
            super(superState);
            this.txtColor = txtColor;
            this.btnColor = number2;
        }

        private SavedState(Parcel in) {
            super(in);
            txtColor = in.readInt();
            btnColor = in.readInt();
        }

        int getTxtColor() {
            return txtColor;
        }

        int getBtnColor() {
            return btnColor;
        }

        @Override
        public void writeToParcel(Parcel destination, int flags) {
            super.writeToParcel(destination, flags);
            destination.writeInt(txtColor);
            destination.writeInt(btnColor);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}

