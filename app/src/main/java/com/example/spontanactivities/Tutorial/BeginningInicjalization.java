package com.example.spontanactivities.Tutorial;

import android.content.Context;
import android.provider.ContactsContract;

import com.example.spontanactivities.Data.AppDatabase;
import com.example.spontanactivities.Data.Daos.StateDao;
import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Data.DefaultStorages.SpontanStorage;
import com.example.spontanactivities.Dtos.SpontanDto;
import com.example.spontanactivities.Dtos.SpontanTagDto;
import com.example.spontanactivities.Dtos.StateDto;
import com.example.spontanactivities.Dtos.TagDto;
import com.example.spontanactivities.Model.Spontan;
import com.example.spontanactivities.Model.Tag;
import com.example.spontanactivities.Data.DefaultStorages.TagStorage;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BeginningInicjalization {
    Context context;

    public void run(Context context) {
        if (this.context == null) {
            this.context = context;
            DatabaseAdapter databaseAdapter = new DatabaseAdapter();
            AppDatabase appDatabase = databaseAdapter.getDatabase(context);
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
        DatabaseAdapter databaseAdapter= new DatabaseAdapter();
        StateDao stateDao=databaseAdapter.getDatabase(context).stateDao();

        if(stateDao.getAll().size()!=0) {
            stateDao.insertAll(Arrays.asList(new StateDto(1, "Active"),
                                             new StateDto(2,"SuccessfullyCompleted"),
                                             new StateDto(3,"Canceled")));
        }
    }

    public void insertTags() {
        TagStorage tagStorage = TagStorage.getInstance();

        DatabaseAdapter databaseAdapter = new DatabaseAdapter();
        AppDatabase appDatabase = databaseAdapter.getDatabase(context);





        List<TagDto> tagDtoList = new LinkedList<>();
        for (Tag tag : tagStorage.getTags()) {
            tagDtoList.add(new TagDto(tag));
        }
        long[] tagIds =appDatabase.tagDao().insertAll(tagDtoList);

        for(int i=0;i<tagStorage.getTags().size();i++){
            tagStorage.getTags().get(i).setId((int)tagIds[i]);
        }
    }

    public void insertSpontans() {
        SpontanStorage spontanStorage = new SpontanStorage();
        DatabaseAdapter databaseAdapter = new DatabaseAdapter();
        AppDatabase appDatabase = databaseAdapter.getDatabase(context);


        List<SpontanDto> spontanDtosToInsert = new LinkedList<>();
        List<SpontanTagDto > spontanTagDtosToInsert= new LinkedList<>();

        List<Spontan> exampleSpontans= spontanStorage.getAll();

        for (Spontan spontan : exampleSpontans) {
            spontanDtosToInsert.add(new SpontanDto(spontan));

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
}
