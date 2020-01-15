package com.example.spontanactivities.Views.GalleryLibrary.GalleryPackage;

import com.example.spontanactivities.Views.GalleryItemState;
import com.example.spontanactivities.Views.GalleryLibrary.GalleryItemPackage.GalleryItem;

import java.util.List;

public interface Gallery {


    List<GalleryItem> getEqualsToState(GalleryItemState galleryItemState);

    void changeStateOfAllTo(GalleryItemState galleryItemState);
    void addListItem(GalleryItem galleryItem);



}
