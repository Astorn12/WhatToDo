package com.example.spontanactivities.Logic.TagBehaviors;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.spontanactivities.Data.AppDatabase;
import com.example.spontanactivities.Data.Daos.Interfaces.TagDao;
import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Helpers.SerialaizerDeserializer;
import com.example.spontanactivities.Logic.TagBehaviors.FriendBehavior.Friends;
import com.example.spontanactivities.Logic.TagBehaviors.Sensors.Compass;
import com.example.spontanactivities.Logic.TagBehaviors.Sensors.LightMeter;
import com.example.spontanactivities.Logic.TagBehaviors.Sensors.SensorList;
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
        @JsonSubTypes.Type(value = EmptyTagBehavior.class, name = "friends"),
        @JsonSubTypes.Type(value = SensorList.class, name = "sensorlist"),
        @JsonSubTypes.Type(value = LightMeter.class, name = "lightmeter"),
        @JsonSubTypes.Type(value = Compass.class, name = "compass")

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
