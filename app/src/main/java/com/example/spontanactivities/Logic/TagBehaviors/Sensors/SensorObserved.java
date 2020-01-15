package com.example.spontanactivities.Logic.TagBehaviors.Sensors;

import android.hardware.SensorEvent;

public interface SensorObserved {

    void addSensorObserver (SensorObserver sensorObserver);
    void removeSensorObserver(SensorObserver sensorObserver);

    void inform(SensorEvent sensorEvent);

}

