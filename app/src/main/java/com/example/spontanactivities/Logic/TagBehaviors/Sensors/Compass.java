package com.example.spontanactivities.Logic.TagBehaviors.Sensors;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.spontanactivities.Activities.SpontanDetails.SpontanDetails;
import com.example.spontanactivities.Logic.TagBehaviors.CommonTagBehavior;
import com.example.spontanactivities.R;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("compass")
public class Compass extends CommonTagBehavior implements SensorObserver {
    TextView textView;
    ImageView imageView;
    float[] mGravity;
    float[] mGeomagnetic;
    int mod=0;
    float lastRotation=0;
    @Override
    public View getAddingView(Context context) {
        return null;
    }

    @Override
    public View getDisplayView(Context context) {
        SpontanDetails spontanDetails= ((SpontanDetails)context);
        this.textView= new TextView(context);
        imageView= new ImageView(context);
        SensorManager mySensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        Sensor lightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor lightSensor2 = mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        spontanDetails.registerSensorListener(lightSensor,SensorManager.SENSOR_DELAY_UI);
        spontanDetails.registerSensorListener(lightSensor2,SensorManager.SENSOR_DELAY_UI);
        spontanDetails.addSensorObserver(this);
        textView.setText("Light meter");
        imageView.setImageResource(R.drawable.compas);
        textView.setBackgroundColor(Color.WHITE);
        return imageView;
    }

    @Override
    public void update(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;

        if (mGravity != null && mGeomagnetic != null) {
            float M[] = new float[9];
            float I[] = new float[9];

            if (SensorManager.getRotationMatrix(M, I, mGravity, mGeomagnetic)) {

                // orientation contains azimut, pitch and roll
                float orientation[] = new float[3];
                SensorManager.getOrientation(M, orientation);

                float azimut = orientation[0];
                float rotation = -azimut * 360 / (2 * 3.14159f);


                //if(mod%50==0) {
                    RotateAnimation rotate = new RotateAnimation(lastRotation, rotation, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(5000);
                    rotate.setInterpolator(new LinearInterpolator());



                    imageView.startAnimation(rotate);
                    lastRotation = rotation;
               // }

               // mod++;
              /*
                    Glide
                            .with(imageView.getContext())
                            .load(R.drawable.compas)
                            .transform(new RotateTransformation(imageView.getContext(), rotation))
                            .into(imageView);
                }
                mod++;*/
                //this.textView.setText(rotation+" o");
            }
        }

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
