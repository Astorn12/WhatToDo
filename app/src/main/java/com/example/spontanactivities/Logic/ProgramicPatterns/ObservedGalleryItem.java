package com.example.spontanactivities.Logic.ProgramicPatterns;

import com.example.spontanactivities.View.GalleryItemState;

public interface ObservedGalleryItem {
        void add(ObserverGalleryItem observerGalleryItem);
        void remove(ObserverGalleryItem observerGalleryItem);
        void clear();
        void inform(GalleryItemState state);

}
