package com.example.spontanactivities.Logic.TagBehaviors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.spontanactivities.Data.AppDatabase;
import com.example.spontanactivities.Data.Daos.Interfaces.TagDao;
import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Dtos.TagDto;
import com.example.spontanactivities.Helpers.SerialaizerDeserializer;
import com.example.spontanactivities.Logic.TagBehaviors.FriendBehavior.Friend;
import com.example.spontanactivities.Logic.TagBehaviors.FriendBehavior.Friends;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Photo.class, name = "photo"),
        @JsonSubTypes.Type(value = Friends.class, name = "emptytagbehavior"),
        @JsonSubTypes.Type(value = EmptyTagBehavior.class, name = "friends")

})
@JsonTypeName("commontagbehavior")
public abstract class CommonTagBehavior implements TagBehavior {
    int idToUpdate;

    @Override
    public View getView(Context context) {
        if(hasBeenInitialized(context)) return  getDisplayView(context);
        else return getAddingView(context);
    }
    public abstract View getAddingView(Context context);
    public abstract View getDisplayView(Context context);

    @Override
    public int getIdToUpdate() {
        return this.idToUpdate;
    }

    @Override
    public void setIdToUpdate(int id) {
        this.idToUpdate=id;
    }

    @Override
    public void update(Context context, Intent intent) {
        DatabaseAdapter databaseAdapter= new DatabaseAdapter();
        AppDatabase appDatabase=databaseAdapter.getDatabase(context);
        TagDao tagDao=appDatabase.tagDao();

        String JSON= SerialaizerDeserializer.serialize(this);

        tagDao.updateBehavior(this.getIdToUpdate(),JSON);
    }
}
