package com.example.spontanactivities.Data.DefaultStorages;

import com.example.spontanactivities.Data.DefaultStorages.TagStorage;
import com.example.spontanactivities.Model.Spontan;

import java.util.Arrays;
import java.util.List;

public class SpontanStorage {


    public List<Spontan> getAll() {
        TagStorage tagStorage =TagStorage.getInstance();

        //return Arrays.asList();
        return Arrays.asList(new Spontan("Basen", tagStorage.getByName("Sport", "Learning", "At night")),
                new Spontan("Kino", tagStorage.getByName("At night", "Entertainment")),
                new Spontan("PaintBall", tagStorage.getByName("Shooping", "Sth new")));


    }

    public Spontan get(int id) {
        return getAll().get(id);
    }

    public void save() {

    }

}
