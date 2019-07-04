[![Release](https://jitpack.io/v/tsuryo/NumberPicker.svg)](https://jitpack.io/#tsuryo/NumberPicker)
# NumberPicker

Android library that provides a simple and customisable NumberPicker - Number Picker.

### Prerequisites
Android 5.0+ API 21+
# Features

* Customizable fonts.
* Long Click for faster progress.
* Also supports negative values.

# Usage
// Java
```
        mNumberPicker = findViewById(R.id.numberPicker);
        mNumberPicker.setListener(this);
	/*
         * Additional attributes:
         * */
        mNumberPicker.setBtnTextSize(30);
        mNumberPicker.setBtnTxtColor(R.color.colorAccent);

        mNumberPicker.setTextSize(30);
        mNumberPicker.setNumberTxtColor(R.color.colorPrimary);

        mNumberPicker.setCurrentValue(50); //set the current number
        mNumberPicker.setMaxValue(150);
        mNumberPicker.setMinValue(0); //if not set, default is 0
        mNumberPicker.setStartValue(5);
```
//XML
```
    <com.tsuryo.numberpickerlib.NumberPicker
        android:id="@+id/example2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        app:btnTextSize="40dp"
        app:btnTxtColor="@color/colorAccent"
        app:layout_constraintEnd_toEndOf="@id/gdV50"
        app:layout_constraintStart_toStartOf="@id/gdV50"
        app:layout_constraintTop_toBottomOf="@id/numberPicker"
        app:maxValue="150"
        app:minValue="3"
        app:numberTxtColor="@android:color/holo_blue_bright"
        app:startValue="7"
        app:textSize="33sp" />

```
### Installing

Add the JitPack repository to your build file.
Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add the dependency
```
dependencies {
	implementation 'com.github.tsuryo:NumberPicker:1.0'
}
```
