package com.example.spontanactivities.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.spontanactivities.Logic.ProgramicPatterns.ObservedGalleryItem;
import com.example.spontanactivities.Logic.ProgramicPatterns.ObserverGalleryItem;
import com.example.spontanactivities.R;
import com.example.spontanactivities.View.Listeners.CustomOnClickListener;
import com.example.spontanactivities.View.Listeners.CustonOnLongClickListener;
import com.example.spontanactivities.View.Listeners.OnSthListener;
import com.google.android.flexbox.FlexboxLayout;

import java.util.LinkedList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GalleryItem extends FlexboxLayout  implements ObservedGalleryItem {
    GalleryItemState state;
    ConstraintLayout constraintLayout;
    ImageView imageView;
    ImageView leftUpCorner;
    List<ObserverGalleryItem> observed;

    public GalleryItem(Context context,List<OnSthListener> listeners, int resource) {
        super(context);
        this.observed=new LinkedList<>();
        //this.addView(inflate(context, R.layout.gallerysketch,this));
        constraintLayout= new ConstraintLayout(context);
        this.state=GalleryItemState.INACTIVE;
        this.imageView= new ImageView(context);
        Glide.with(context)
                .load(resource)
                .into(imageView);
        imageView.setAdjustViewBounds(true);

        this.leftUpCorner= new ImageView(context);
        leftUpCorner.setLayoutParams(new FrameLayout.LayoutParams(50,50));

        /*Glide.with(context)
                .load(R.drawable.tickedcircle)
                .into(leftUpCorner);*/

        leftUpCorner.setAdjustViewBounds(true);

        constraintLayout.setLayoutParams(new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        constraintLayout.addView(imageView);
        constraintLayout.addView(leftUpCorner);
        this.addView(constraintLayout);
        imageView.setBackgroundColor(Color.rgb(152,166,172));
        this.setBackgroundColor(Color.rgb(152,166,172));
        setListeners(getGalleryListeners());

    }

    private List<OnSthListener> getGalleryListeners() {
        List<OnSthListener> tmpListListeners= new LinkedList<>();

        OnLongClickListener onLongClickListener= new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                inform(GalleryItemState.ACTIVE);
                return true;
            }
        };

        OnClickListener onClickListener= new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(state==GalleryItemState.ACTIVE) {

                    choose();
                    inform(GalleryItemState.CHOOSEN);
                }else if(state==GalleryItemState.CHOOSEN){

                    dechoose();
                    inform(GalleryItemState.ACTIVE);
                }

            }
        };

        tmpListListeners.add(new CustonOnLongClickListener(onLongClickListener));
        tmpListListeners.add(new CustomOnClickListener(onClickListener));

        return tmpListListeners;
    }




    private void setListeners(List<OnSthListener> listeners){
        for(OnSthListener osl: listeners){
            osl.addListener(this);
        }
    }


    /*private void changeState(GalleryItemState state){
        switch(state){
            case INACTIVE:
                break;
            case ACTIVE:
                break;
            case CHOOSEN:
                break;
        }
    }*/



    public void activate(){
        ifChangeTo(GalleryItemState.INACTIVE,GalleryItemState.ACTIVE,R.drawable.emptycircle,0);
    }

    public void choose(){
        ifChangeTo(GalleryItemState.ACTIVE,GalleryItemState.CHOOSEN,R.drawable.tickedcircle,600);
        this.imageView.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
    }
    public void deactivate(){
        ifChangeTo(GalleryItemState.CHOOSEN,GalleryItemState.INACTIVE,0,0);
        ifChangeTo(GalleryItemState.ACTIVE,GalleryItemState.INACTIVE,0,0);
        this.imageView.setColorFilter(0);
    }

    public void dechoose(){
        ifChangeTo(GalleryItemState.CHOOSEN,GalleryItemState.ACTIVE,R.drawable.emptycircle,600);
        this.imageView.setColorFilter(0);
    }

    private void ifChangeTo(GalleryItemState ifState, GalleryItemState toState,int resource,int time){
        if(this.state==ifState){
            Glide.with(this)
                    .load(resource)
                    .transition(DrawableTransitionOptions.with(new DrawableAlwaysCrossFadeFactory(time)))
                    .into(this.leftUpCorner);
            this.state=toState;
        }
    }

    @Override
    public void add(ObserverGalleryItem observerGalleryItem) {
        this.observed.add(observerGalleryItem);
    }

    @Override
    public void remove(ObserverGalleryItem observerGalleryItem) {
        this.observed.remove(observerGalleryItem);
    }

    @Override
    public void clear() {
        this.observed.clear();

    }

    @Override
    public void inform(GalleryItemState galleryItemState) {
        for(ObserverGalleryItem observer: this.observed){
            observer.update(galleryItemState);
        }
    }
}
