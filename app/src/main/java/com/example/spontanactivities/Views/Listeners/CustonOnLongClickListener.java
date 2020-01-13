package com.example.spontanactivities.Views.Listeners;

import android.view.View;

import com.example.spontanactivities.Views.GalleryItem;

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
