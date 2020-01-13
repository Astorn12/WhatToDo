package com.example.spontanactivities.Data.ObjectAdapters;

import android.content.Context;

import java.util.List;

public interface ObjectDataAdapter<T> {

    List<T> getAll(Context context);
}
