package com.example.spontanactivities.Logic.ProgramicPatterns;

public interface TagTableObserved {

    void addObserver(TagTableObserver observer);
    void removeObserver(TagTableObserver observer);

    void inform();

}
