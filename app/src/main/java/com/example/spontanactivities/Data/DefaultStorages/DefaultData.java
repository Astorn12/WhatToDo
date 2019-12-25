package com.example.spontanactivities.Data.DefaultStorages;

import com.example.spontanactivities.Model.Spontan;
import com.example.spontanactivities.Model.Tag;
import com.example.spontanactivities.R;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DefaultData {

    public List<Tag>  getTags(){


            List<Tag> tags=Arrays.asList(
                    new Tag("Sport", R.drawable.sporticon),
                    new Tag("Deadline",R.drawable.alarm),
                    new Tag("Learning",R.drawable.book),
                    new Tag("On computer", R.drawable.computer),
                    new Tag("With friends", R.drawable.friends),
                    new Tag("Self-improvement", R.drawable.hinduistyogaposition),
                    new Tag("At home",R.drawable.house ),
                    new Tag("Sth new", R.drawable.idea),
                    new Tag("Learning", R.drawable.learning),
                    new Tag("Shooping",R.drawable.shoopinglist),
                    new Tag("Portable",R.drawable.traveler),
                    new Tag("At night",R.drawable.nighticon),
                    new Tag("Outside",R.drawable.outsideicon),
                    new Tag("Entertainment",R.drawable.entertinmenticon),
                    new Tag("Work",R.drawable.workicon)

            );
            return tags;
        }

        private List<Tag> getByName(String... names)  {
            List<Tag> tmpTags= new LinkedList<>();
            for(Tag tag: getTags()){
                for(String name: names) {
                    if (tag.getName().equals(name)) tmpTags.add(tag);
                }
            }
            return tmpTags;
        }


    public List<Spontan> getSpontans(){
        return Arrays.asList(new Spontan("Basen",getByName("Sport","Learning","At night")),
                new Spontan("Kino",getByName("At night", "Entertainment")),
                new Spontan("PaintBall",getByName("Shooping","Sth new")));
    }
}
