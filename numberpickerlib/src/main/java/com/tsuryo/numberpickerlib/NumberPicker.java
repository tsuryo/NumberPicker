package com.tsuryo.numberpickerlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Timer;
import java.util.TimerTask;

import static com.tsuryo.numberpickerlib.Constants.NumberPicker.BTN_TEXT_SIZE_DEF;
import static com.tsuryo.numberpickerlib.Constants.NumberPicker.DELAY;
import static com.tsuryo.numberpickerlib.Constants.NumberPicker.FAST_PERIOD;
import static com.tsuryo.numberpickerlib.Constants.NumberPicker.MAX_VALUE_DEF;
import static com.tsuryo.numberpickerlib.Constants.NumberPicker.MIN_VALUE_DEF;
import static com.tsuryo.numberpickerlib.Constants.NumberPicker.SLOW_PERIOD;
import static com.tsuryo.numberpickerlib.Constants.NumberPicker.START_VALUE_DEF;
import static com.tsuryo.numberpickerlib.Constants.NumberPicker.TEXT_SIZE_DEF;

public class NumberPicker extends ConstraintLayout
        implements View.OnClickListener, View.OnLongClickListener {
    private Integer mStartValue, mMinValue, mMaxValue, mCurrentValue;
    private Integer mNumberTxtColor, mBtnTxtColor;
    private Integer mTextSize, mBtnTextSize;

    private Button mBtnMinus, mBtnPlus;
    private TextView mTvNumber;
    private Listener mListener;


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
        mTvNumber.setText(String.valueOf(mCurrentValue));
        mTvNumber.setTextColor(mNumberTxtColor);
        mTvNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mBtnPlus.setTextColor(mBtnTxtColor);
        mBtnMinus.setTextColor(mBtnTxtColor);
        mBtnPlus.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBtnTextSize);
        mBtnMinus.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBtnTextSize);
        mListener.onNumberChange(mCurrentValue);
        super.onDraw(canvas);
    }

    private void getAttributes(Context context, AttributeSet attrs) {
        TypedArray tArr = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.NumberPicker,
                0, 0);
        try {
            mBtnTxtColor = tArr.getColor(
                    R.styleable.NumberPicker_btnTxtColor,
                    getResources().getColor(R.color.btnDefColor));
            mNumberTxtColor = tArr.getColor(
                    R.styleable.NumberPicker_numberTxtColor,
                    getResources().getColor(R.color.textDefColor));
            mStartValue = tArr.getInt(
                    R.styleable.NumberPicker_startValue,
                    START_VALUE_DEF);
            mMinValue = tArr.getInt(
                    R.styleable.NumberPicker_minValue,
                    MIN_VALUE_DEF);
            mMaxValue = tArr.getInt(
                    R.styleable.NumberPicker_maxValue,
                    MAX_VALUE_DEF);
            mTextSize = tArr.getDimensionPixelSize(
                    R.styleable.NumberPicker_textSize,
                    TEXT_SIZE_DEF);
            mBtnTextSize = tArr.getDimensionPixelSize(
                    R.styleable.NumberPicker_btnTextSize,
                    BTN_TEXT_SIZE_DEF);
            initCurrentNumber();
        } finally {
            tArr.recycle();
        }
    }

    private void initCurrentNumber() {
        if (mMinValue <= mStartValue
                && mStartValue <= mMaxValue) {
            this.mCurrentValue = mStartValue;
        } else {
            mCurrentValue = (mMaxValue + mMinValue) / 2;
        }
    }

    private void initViews() {
        mBtnMinus = findViewById(R.id.minusBtn);
        mBtnPlus = findViewById(R.id.plusBtn);
        mBtnMinus.setOnClickListener(this);
        mBtnPlus.setOnClickListener(this);
        mBtnPlus.setOnLongClickListener(this);
        mBtnMinus.setOnLongClickListener(this);
        mTvNumber = findViewById(R.id.tvNumber);
    }

    /**
     * @return the mTvNumber current value
     * if there is a problem with the TextView
     * will return START_VALUE_DEF
     */
    public int getValue() {
        try {
            return Integer.valueOf(mTvNumber.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return START_VALUE_DEF;
        }
    }

    public void setCurrentValue(Integer currentValue) {
        this.mCurrentValue = currentValue;
        mTvNumber.setText(String.valueOf(currentValue));
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public Integer getStartValue() {
        return mStartValue;
    }

    public Integer getMinValue() {
        return mMinValue;
    }

    public Integer getMaxValue() {
        return mMaxValue;
    }

    public Integer getNumberTxtColor() {
        return mNumberTxtColor;
    }

    public Integer getBtnTxtColor() {
        return mBtnTxtColor;
    }

    public Integer getTextSize() {
        return mTextSize;
    }

    public Integer getBtnTextSize() {
        return mBtnTextSize;
    }

    public void setStartValue(Integer startValue) {
        mStartValue = startValue;
        refresh();
    }

    public void setMinValue(Integer minValue) {
        mMinValue = minValue;
        refresh();
    }

    public void setMaxValue(Integer maxValue) {
        mMaxValue = maxValue;
        refresh();
    }

    public void setNumberTxtColor(Integer numberTxtColor) {
        mNumberTxtColor = numberTxtColor;
        refresh();
    }

    public void setBtnTxtColor(Integer btnTxtColor) {
        mBtnTxtColor = btnTxtColor;
        refresh();
    }

    public void setTextSize(Integer textSize) {
        mTextSize = textSize;
        refresh();
    }

    public void setBtnTextSize(Integer btnTextSize) {
        mBtnTextSize = btnTextSize;
        refresh();
    }

    private void refresh() {
        invalidate();
        requestLayout();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.plusBtn
                && mCurrentValue <= mMaxValue)
            mCurrentValue++;
        else if (view.getId() == R.id.minusBtn
                && mCurrentValue >= mMinValue)
            mCurrentValue--;
        mTvNumber.setText(String.valueOf(mCurrentValue));
        mListener.onNumberChange(mCurrentValue);
    }

    @Override
    public boolean onLongClick(final View v) {
        final Handler h = new Handler(Looper.getMainLooper());
        final Timer timer = new Timer();
        final int[] counter = {0};

        final Runnable incrementTask = new Runnable() {
            @Override
            public void run() {
                int i = v.getId();
                if (i == R.id.plusBtn) {
                    if (mCurrentValue <= mMaxValue)
                        mCurrentValue++;
                } else if (i == R.id.minusBtn) {
                    if (mCurrentValue >= mMinValue)
                        mCurrentValue--;
                }
                mTvNumber.setText(String.valueOf(mCurrentValue));
                mListener.onNumberChange(mCurrentValue);
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
        return new SavedState(superState,
                mNumberTxtColor, mBtnTxtColor,
                mTextSize, mBtnTextSize);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setNumberTxtColor(savedState.getTxtColor());
        setBtnTxtColor(savedState.getBtnColor());
        setTextSize(savedState.getTextSize());
        setBtnTextSize(savedState.getBtnTextSize());
    }

    public interface Listener {
        void onNumberChange(int num);
    }


    protected static class SavedState extends View.BaseSavedState {
        private final int mTxtColor;
        private final int mBtnColor;
        private final int mTextSize, mBtnTextSize;

        private SavedState(Parcelable superState,
                           int txtColor, int number2,
                           int textSize, int btnTextSize) {
            super(superState);
            mTxtColor = txtColor;
            mBtnColor = number2;
            mTextSize = textSize;
            mBtnTextSize = btnTextSize;
        }

        private SavedState(Parcel in) {
            super(in);
            mTxtColor = in.readInt();
            mBtnColor = in.readInt();
            mTextSize = in.readInt();
            mBtnTextSize = in.readInt();
        }

        int getTxtColor() {
            return mTxtColor;
        }

        int getBtnColor() {
            return mBtnColor;
        }

        int getTextSize() {
            return mTextSize;
        }

        int getBtnTextSize() {
            return mBtnTextSize;
        }

        @Override
        public void writeToParcel(Parcel destination, int flags) {
            super.writeToParcel(destination, flags);
            destination.writeInt(mTxtColor);
            destination.writeInt(mBtnColor);
            destination.writeInt(mTextSize);
            destination.writeInt(mBtnTextSize);
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

