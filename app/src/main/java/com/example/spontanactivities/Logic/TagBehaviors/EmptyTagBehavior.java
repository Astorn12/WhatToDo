package com.example.spontanactivities.Logic.TagBehaviors;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("emptagbehavior")
public class EmptyTagBehavior extends CommonTagBehavior{

    public EmptyTagBehavior(){

    }
    @Override
    public View getAddingView(Context context) {


        return null;
    }

    @Override
    public View getDisplayView(Context context) {
        return null;
    }

    @Override
    public View getChangingView(Context context) {
        return null;
    }

    @Override
    public boolean hasBeenInitialized(Context context) {
        return true;
    }
}
