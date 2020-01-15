package com.example.spontanactivities.Logic.TagBehaviors.Sensors;

import android.hardware.SensorEvent;

public interface SensorObserver {

    void update(SensorEvent sensorEvent);
}
