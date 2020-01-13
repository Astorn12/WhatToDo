package com.example.spontanactivities.Logic.ProgramicPatterns;

import com.example.spontanactivities.Model.Tag;

import java.util.List;

public interface TagTableObserver {

    void update(List<Tag> choosenTags);
}
