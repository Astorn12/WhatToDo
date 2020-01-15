package com.example.spontanactivities.Views.GalleryLibrary.GalleryItemPackage;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.spontanactivities.R;
import com.example.spontanactivities.Views.GalleryItemState;

public abstract class GalleryItemImp extends LinearLayout implements GalleryItem {

    GalleryItemState state;
    Presentable presentable;
    public GalleryItemImp(Context context, Presentable presentable) {
        super(context);
        this.state=GalleryItemState.INACTIVE;
        this.presentable=presentable;
        this.addView(concatenateViews());
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               manageViewClick();
            }
        });
        this.setBackground(getResources().getDrawable(R.drawable.layoutbordershape));
    }

    public abstract View concatenateViews();


    @Override
    public GalleryItemState getState() {
        return this.state;
    }

    @Override
    public void changeState(GalleryItemState stateToChange) {
        viewReactForStateChanging(this.getState(), stateToChange);
        this.state=stateToChange;
    }

    public abstract void viewReactForStateChanging(GalleryItemState oldState,GalleryItemState newState);

    @Override
    public View getView(){
        return this;
    }
}
