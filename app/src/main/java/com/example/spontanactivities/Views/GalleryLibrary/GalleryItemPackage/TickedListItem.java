package com.example.spontanactivities.Views.GalleryLibrary.GalleryItemPackage;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.bumptech.glide.Glide;
import com.example.spontanactivities.Model.Tag;
import com.example.spontanactivities.R;
import com.example.spontanactivities.Views.GalleryItemState;

public class TickedListItem extends GalleryItemImp {
    FrameLayout tickableSquare;

    public TickedListItem(Context context, Presentable presentable) {
        super(context, presentable);

    }

    @Override
    public View concatenateViews() {

        LinearLayout linearLayout= new LinearLayout(this.getContext());
        linearLayout.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(HORIZONTAL);


        linearLayout.addView(presentable.getView(this.getContext()));

        this.tickableSquare=new FrameLayout(this.getContext());
        ImageView rectangle= new ImageView(this.getContext());
        rectangle.setImageResource(R.drawable.square);



        this.tickableSquare.addView(rectangle);
        tickableSquare.setLayoutParams(new LinearLayoutCompat.LayoutParams(80, 80,1));
        linearLayout.addView(tickableSquare);

        return linearLayout;
    }

    @Override
    public void viewReactForStateChanging(GalleryItemState oldState, GalleryItemState newState) {

        if(oldState.equals(GalleryItemState.INACTIVE)&newState.equals(GalleryItemState.ACTIVE)){
            tickView();
        } else if(oldState.equals(GalleryItemState.ACTIVE)&newState.equals(GalleryItemState.INACTIVE)){
            unTickView();
        }
    }

    private void unTickView() {

        this.tickableSquare.removeViewAt(1);
    }

    private void tickView() {
        FrameLayout f=this.tickableSquare;
        int i = f.getChildCount();
        ImageView tick = new ImageView(f.getContext());
        Glide.with(f.getContext()).load(R.drawable.tick).into(tick);
        f.addView(tick);
        tick.getLayoutParams().width = 50;
        tick.getLayoutParams().height = 50;
        //  if(f.getWidth()!=0) {
        tick.setX((f.getWidth() - tick.getLayoutParams().width)/2);
        //    }
        // else   tick.setX(0);
        //tick.setX(50);
        tick.setY((f.getHeight() - tick.getLayoutParams().height)/2);
        tick.invalidate();
        f.invalidate();
        f.refreshDrawableState();
    }

    @Override
    public void manageViewClick() {/**To co ma się wydarzyć po kliknięciu w item*/
        FrameLayout f= this.tickableSquare;
            int i = f.getChildCount();
            if (i == 1) {
                changeState(GalleryItemState.ACTIVE);

            } else changeState(GalleryItemState.INACTIVE);

    }
}
