package com.example.spontanactivities.Logic.TagBehaviors;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CommonTagBehavior.class, name = "commontagbehavior"),

})


 public interface TagBehavior {


     View getView(Context context);
     View getChangingView(Context context);
     boolean hasBeenInitialized(Context context);
     int getIdToUpdate();
     void setIdToUpdate(int id);
     void update(Context context, Intent intent);




}
