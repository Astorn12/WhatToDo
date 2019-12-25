package com.example.spontanactivities.View.Listeners;

import android.view.View;

import com.example.spontanactivities.View.GalleryItem;

public class CustomOnClickListener implements OnSthListener {

    View.OnClickListener onClickListener;

    public CustomOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void addListener(GalleryItem view) {
        view.setOnClickListener(this.onClickListener);
    }
}
