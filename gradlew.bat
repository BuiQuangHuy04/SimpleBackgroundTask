<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:cardBackgroundColor="@color/white"
    app:cardElevation="8dp"
    android:layout_margin="8dp">
    
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">
        <TextView
            android:id="@+id/textView_title"
            android:text="Dish Name"
            android:singleLine="true"
            android:ellipsize="marquee"
            android:marqueeRepeatLimit="marquee_forever"
            android:scrollHorizontally="true"
            android:textStyle="bold"
            android:textColor="@color/orange"
            android:textSize="22sp"
            android:padding="8dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
        <ImageView
            android:id="@+id/imageView_food"
            android:scaleType="centerCrop"
            android:layout_margin="8dp"
            android:layout_width="match_parent"
            android:layout_height="200dp"/>
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            />
    </LinearLayout>


</androidx.cardview.widget.CardView>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         