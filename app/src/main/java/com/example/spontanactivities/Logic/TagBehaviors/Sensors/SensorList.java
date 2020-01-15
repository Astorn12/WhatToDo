package com.example.spontanactivities.Logic.TagBehaviors.Sensors;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.spontanactivities.Logic.TagBehaviors.CommonTagBehavior;
import com.example.spontanactivities.Views.Listeners.CustonOnLongClickListener;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("sensorlist")
public class SensorList extends CommonTagBehavior {


    @Override
    public View getAddingView(Context context) {
        return null;
    }


    @Override
    public View getDisplayView(Context context) {
        SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for(Sensor sensor:deviceSensors){
            TextView textView= new TextView(context);
            textView.setText(sensor.getName());
            textView.setBackgroundColor(Color.WHITE);
            linearLayout.addView(textView);
        }

        ScrollView scrollView= new ScrollView(context);
        scrollView.addView(linearLayout);
        scrollView.setLayoutParams(new LinearLayoutCompat.LayoutParams(1000,1000));
        return scrollView;
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
