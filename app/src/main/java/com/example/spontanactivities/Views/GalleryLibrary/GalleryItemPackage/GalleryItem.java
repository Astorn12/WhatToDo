package com.example.spontanactivities.Views.GalleryLibrary.GalleryItemPackage;

import android.view.View;

import com.example.spontanactivities.Views.GalleryItemState;

public interface GalleryItem {


     GalleryItemState getState();
     void changeState(GalleryItemState galleryItemState);
     void manageViewClick();

     View getView();

}
