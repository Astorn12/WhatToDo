package com.example.spontanactivities.Views.GalleryLibrary.GalleryPackage;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.spontanactivities.R;
import com.example.spontanactivities.Views.GalleryItemState;
import com.example.spontanactivities.Views.GalleryLibrary.GalleryItemPackage.GalleryItem;
import com.example.spontanactivities.Views.GalleryLibrary.GalleryItemPackage.GalleryItemImp;

import java.util.LinkedList;
import java.util.List;

public class GalleryImp extends LinearLayout implements Gallery {
    List<GalleryItem> galleryItemList;

    LinearLayout contentListLayout;
    Button button;
    public GalleryImp(Context context) {
        super(context);
        this.contentListLayout= new LinearLayout(context);
        this.contentListLayout.setOrientation(VERTICAL);

        this.galleryItemList=new LinkedList<>();
        this.setOrientation(VERTICAL);
        button= new Button(context);
        button.setText("Add");

        ScrollView scrollView= new ScrollView(context);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));

        this.addView(scrollView);
        this.addView(button);
        button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300,1));
        scrollView.addView(contentListLayout);
        contentListLayout.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


    }


    @Override
    public List<GalleryItem> getEqualsToState(GalleryItemState galleryItemState) {

        List<GalleryItem> galleryItems= new LinkedList<>();
        for (GalleryItem galleryItem : this.galleryItemList){
            if (galleryItem.getState().equals(galleryItemState)){
                galleryItems.add(galleryItem);
            }
        }
        return galleryItems;
    }



    @Override
    public void changeStateOfAllTo(GalleryItemState galleryItemState) {
        for (GalleryItem galleryItem : this.galleryItemList){
            galleryItem.changeState(galleryItemState);
        }
    }

    @Override
    public void addListItem(GalleryItem galleryItem) {
        this.galleryItemList.add(galleryItem);
        this.contentListLayout.addView(galleryItem.getView());

    }
}
