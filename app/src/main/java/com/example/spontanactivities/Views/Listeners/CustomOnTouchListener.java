package com.example.spontanactivities.Views.Listeners;

import android.view.View;

import com.example.spontanactivities.Views.GalleryItem;

public class CustomOnTouchListener implements OnSthListener {
    View.OnTouchListener onTouchListener;

    public CustomOnTouchListener(View.OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    @Override
    public void addListener(GalleryItem view) {
        view.setOnTouchListener(this.onTouchListener);
    }
}
