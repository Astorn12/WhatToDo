package com.example.spontanactivities.Logic.ProgramicPatterns;

import java.util.LinkedList;

public abstract class Observed extends LinkedList<Observer> {


    public void inform(){
        for(Observer singleObserver: this){
            singleObserver.update();
        }
    }
}
