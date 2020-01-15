package com.example.spontanactivities.Logic.TagBehaviors.Sensors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.TextView;

import com.example.spontanactivities.Activities.SpontanDetails.SpontanDetails;
import com.example.spontanactivities.Logic.TagBehaviors.CommonTagBehavior;
import com.example.spontanactivities.Logic.TagBehaviors.TagBehavior;
import com.fasterxml.jackson.annotation.JsonTypeName;

import static android.content.Context.SENSOR_SERVICE;
@JsonTypeName("lightmeter")
public class LightMeter extends CommonTagBehavior implements SensorObserver {

    TextView textView;
    @Override
    public View getAddingView(Context context) {
        return null;
    }

    @Override
    public View getDisplayView(Context context) {
        SpontanDetails spontanDetails= ((SpontanDetails)context);
        this.textView= new TextView(context);
        SensorManager mySensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        Sensor lightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        spontanDetails.registerSensorListener(lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        spontanDetails.addSensorObserver(this);
        textView.setText("Light meter");
        textView.setBackgroundColor(Color.WHITE);
        return textView;
    }

    @Override
    public void update(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];
        this.textView.setText(sensorType+" :"+currentValue);
    }

    @Override
    public View getChangingView(Context context) {
        return null;
    }

    @Override
    public boolean hasBeenInitialized(Context context) {
        return true;
    }
}
