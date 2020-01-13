package com.example.spontanactivities.Views.Listeners;

import android.view.View;

import com.example.spontanactivities.Views.GalleryItem;

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
