package com.example.spontanactivities.Views;

import android.content.Context;
import android.view.View;

public class ViewWithId extends View {
    int id;

    public ViewWithId(Context context, int id) {
        super(context);
        this.id = id;
    }
}
