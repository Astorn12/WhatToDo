package com.example.spontanactivities.Tutorial;

import android.content.Context;

import com.example.spontanactivities.Data.AppDatabase;
import com.example.spontanactivities.Data.Daos.Interfaces.SpontanDao;
import com.example.spontanactivities.Data.Daos.Interfaces.StateDao;
import com.example.spontanactivities.Data.Daos.Interfaces.TagDao;
import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Dtos.SpontanDto;
import com.example.spontanactivities.Dtos.SpontanTagDto;
import com.example.spontanactivities.Dtos.StateDto;
import com.example.spontanactivities.Dtos.TagDto;
import com.example.spontanactivities.Logic.TagBehaviors.FriendBehavior.Friends;
import com.example.spontanactivities.Logic.TagBehaviors.Photo;
import com.example.spontanactivities.Model.Spontan;
import com.example.spontanactivities.Model.Tag;
import com.example.spontanactivities.R;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BeginningInicjalization {
    Context context;
    DatabaseAdapter databaseAdapter;
    AppDatabase appDatabase;
    public void run(Context context) {
        if (this.context == null) {
            this.context = context;
            this.databaseAdapter = new DatabaseAdapter();
            this.appDatabase = databaseAdapter.getDatabase(context);
            if (appDatabase.tagDao().getAll().size() == 0 && appDatabase.spontanDao().getAll().size() == 0) {
                loadExamples();
            }
        }
    }

    private void loadExamples() {
        insertStates();
        insertTags();
        insertSpontans();
    }

    private void insertStates() {
        StateDao stateDao=databaseAdapter.getDatabase(context).stateDao();

        if(stateDao.getAll().size()==0) {
            stateDao.insertAll(Arrays.asList(new StateDto(1, "Active"),
                                             new StateDto(2,"SuccessfullyCompleted"),
                                             new StateDto(3,"Canceled")));
        }
        List<StateDto> stateDtoList= stateDao.getAll();
    }


    public void insertTags() {

        TagDao tagDao= appDatabase.tagDao();

      List<Tag> tags= Arrays.asList(
                new Tag("Sport", R.drawable.sporticon),
                new Tag("Deadline",R.drawable.alarm),
                new Tag("Learning",R.drawable.book),
                new Tag("On computer", R.drawable.computer),
                new Tag("With friends", R.drawable.friends,new Friends()),
                new Tag("Self-improvement", R.drawable.hinduistyogaposition),
                new Tag("At home",R.drawable.house ),
                new Tag("Sth new", R.drawable.idea),
                new Tag("Learning", R.drawable.learning),
                new Tag("Shooping",R.drawable.shoopinglist),
                new Tag("Portable",R.drawable.traveler),
                new Tag("At night",R.drawable.nighticon),
                new Tag("Outside",R.drawable.outsideicon),
                new Tag("Entertainment",R.drawable.entertinmenticon),
                new Tag("Work",R.drawable.workicon, new Photo())

        );
        tagDao.insertAll(Tag.convert(tags));
    }

    public void insertSpontans() {

        DatabaseAdapter databaseAdapter = new DatabaseAdapter();
        AppDatabase appDatabase = databaseAdapter.getDatabase(context);


        List<SpontanDto> spontanDtosToInsert = new LinkedList<>();
        List<SpontanTagDto > spontanTagDtosToInsert= new LinkedList<>();

        List<Spontan> exampleSpontans=  Arrays.asList(new Spontan("Basen",getTags("Sport", "Learning", "At night")),
                new Spontan("Kino", getTags("At night", "Entertainment")),
                new Spontan("PaintBall", getTags("Shooping", "Sth new")));

        for (Spontan spontan : exampleSpontans) {
            SpontanDto spontanDto= new SpontanDto(spontan);
            spontanDto.stateId=1;
            spontanDtosToInsert.add(spontanDto);

        }
        long[] spontansId=appDatabase.spontanDao().insertAll(spontanDtosToInsert);
        for(int i=0;i<exampleSpontans.size();i++) {
            for (Tag tag : exampleSpontans.get(i).getTags()) {
                SpontanTagDto tmp = new SpontanTagDto((int)spontansId[i], tag.getId());
                spontanTagDtosToInsert.add(tmp);
            }
        }
        appDatabase.spontanTagDao().insertAll(spontanTagDtosToInsert);
    }


    public List<Tag> getTags(String... params){
        TagDao tagDao= this.appDatabase.tagDao();

        return  dtoListToTagListConvertion(tagDao.getByNameList(params));
    }


    private List<Tag> dtoListToTagListConvertion(List<TagDto> dtos){
        List<Tag> tags= new LinkedList<>();

        for(TagDto  dto: dtos){
            tags.add(new Tag(dto));
        }
        return tags;
    }

}
