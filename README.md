[![Release](https://jitpack.io/v/tsuryo/NumberPicker.svg)]
(https://jitpack.io/#tsuryo/NumberPicker)
# NumberPicker

Android library that provides a simple and customisable NumberPicker - Number Picker.

### Prerequisites

What things you need to install the software and how to install them

```
Give examples
```
# Features
....

# Usage
// Java
```
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
///

```

//Attributes

```
///

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
https://jitpack.io/#tsuryo/NumberPicker/1.0
