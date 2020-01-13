package com.example.spontanactivities.Views;

import android.content.Context;
import android.view.View;

import com.example.spontanactivities.Activities.SpontanDetails.ItemsStorager;
import com.example.spontanactivities.Model.Tag;
import com.example.spontanactivities.R;
import com.example.spontanactivities.Views.Listeners.CustomOnClickListener;
import com.example.spontanactivities.Views.Listeners.OnSthListener;

import java.util.LinkedList;
import java.util.List;

public class AddGalleryItem  extends GalleryItem{
    ItemsStorager itemsStorager;
    public AddGalleryItem(Context context, List<OnSthListener> listeners, final ItemsStorager itemsStorager) {
        super(context, listeners, new Tag("add",R.drawable.addicon));
        this.itemsStorager=itemsStorager;
        this.state=GalleryItemState.INACTIVE;
    }

    @Override
    public void setState(GalleryItemState state) {

    }

    @Override
    protected List<OnSthListener> getGalleryListeners() {
        List<OnSthListener> tmpListListeners= new LinkedList<>();

        OnClickListener onClickListener= new OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsStorager.extend();
            }
        };

        tmpListListeners.add(new CustomOnClickListener(onClickListener));


        return tmpListListeners;


    }
}
