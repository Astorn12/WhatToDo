package com.example.spontanactivities.View.Listeners;

import android.view.View;

import com.example.spontanactivities.View.GalleryItem;

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
