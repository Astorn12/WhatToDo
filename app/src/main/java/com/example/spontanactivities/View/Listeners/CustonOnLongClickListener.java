package com.example.spontanactivities.View.Listeners;

import android.view.View;

import com.example.spontanactivities.View.GalleryItem;

public class CustonOnLongClickListener implements OnSthListener {

    View.OnLongClickListener onLongClickListener;

    public CustonOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    @Override
    public void addListener(GalleryItem view) {
        view.setOnLongClickListener(this.onLongClickListener);
    }
}
