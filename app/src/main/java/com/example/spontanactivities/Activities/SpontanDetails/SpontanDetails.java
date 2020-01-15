package com.example.spontanactivities.Activities.SpontanDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spontanactivities.Data.AppDatabase;
import com.example.spontanactivities.Data.Daos.Interfaces.SpontanTagDao;
import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Data.ObjectAdapters.TagDataAdapter;
import com.example.spontanactivities.Dtos.SpontanTagDto;
import com.example.spontanactivities.Logic.ProgramicPatterns.TagTableObserver;
import com.example.spontanactivities.Logic.TagBehaviors.Sensors.SensorObserved;
import com.example.spontanactivities.Logic.TagBehaviors.Sensors.SensorObserver;
import com.example.spontanactivities.Logic.TagBehaviors.TagBehavior;
import com.example.spontanactivities.Model.Spontan;
import com.example.spontanactivities.Model.Tag;
import com.example.spontanactivities.Logic.ProgramicPatterns.ObserverGalleryItem;
import com.example.spontanactivities.R;
import com.example.spontanactivities.Views.AdapterTableLayoutArchitect;
import com.example.spontanactivities.Views.AddGalleryItem;
import com.example.spontanactivities.Views.GalleryItem;
import com.example.spontanactivities.Views.GalleryItemState;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.LinkedList;
import java.util.List;

public class SpontanDetails extends AppCompatActivity  implements ObserverGalleryItem,ItemsStorager, TagTableObserver, SensorEventListener, SensorObserved {

    TextView name;
    Spontan spontan;
    FrameLayout tagTable;
    FrameLayout extras;
    AdapterTableLayoutArchitect tableLayoutArchitect;
    LinearLayout backgroundLayout;
    CustomBottomSheetBehavior<View> bottomSheetBehavior;

    LinearLayout remove;
    LinearLayout tagBehaviorsLayout;
    TagBehavior activeTagBehavior;

    SensorManager mSensorManager;
    List<SensorObserver> sensorObservers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spontan_details);
        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);

        this.sensorObservers= new LinkedList<>();
        tagBehaviorsLayout=findViewById(R.id.tagBehaviorsLayout);
        this.remove = findViewById(R.id.garbageicon);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSpontans();
            }
        });

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.filter_fragment);
        bottomSheetBehavior = CustomBottomSheetBehavior.from(fragment.getView());
        bottomSheetBehavior.setState(CustomBottomSheetBehavior.STATE_HIDDEN);
        backgroundLayout = findViewById(R.id.detailbackground);
        name = findViewById(R.id.activityname);
        tagTable = findViewById(R.id.tagsTable);
        name.setTextSize(30);
        name.setGravity(Gravity.CENTER);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        DatabaseAdapter databaseAdapter = new DatabaseAdapter();
        this.spontan = new Spontan(databaseAdapter.getDatabase(this).spontanDao().findById(id), this);
        name.setText(spontan.getName());
        loadTags();

        backgroundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayoutArchitect.deactivateAll();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });


    }
    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    public void registerSensorListener(Sensor sensor, int samplingPeriodUS){
        mSensorManager.registerListener(this,sensor,samplingPeriodUS);
    }






    private void removeSpontans() {
        List<Tag> selectedTags = tableLayoutArchitect.getSelectedTags();
        DatabaseAdapter databaseAdapter = new DatabaseAdapter();
        AppDatabase appDatabase = databaseAdapter.getDatabase(this);
        SpontanTagDao spontanTagDao = appDatabase.spontanTagDao();
        List<Integer> tagsIds = new LinkedList<>();
        for (Tag tag : selectedTags) {
            tagsIds.add(tag.getId());
        }
        spontanTagDao.delete(spontan.getId(), tagsIds);

        //tableLayoutArchitect.removeSelected();

        loadTags();

    }

    public void loadTags() {
        DatabaseAdapter databaseAdapter = new DatabaseAdapter();
        List<GalleryItem> views = new LinkedList<>();
        for (Tag tag : databaseAdapter.getDatabase(this).tagDao().getTagListBySpontanId(this, spontan.getId())) {
            GalleryItem galleryItem = new GalleryItem(this, null, tag);
            galleryItem.add(this);
            views.add(galleryItem);
            addTagBehavior(tag);
        }
        AddGalleryItem addGalleryItem = new AddGalleryItem(this, null, this);

        views.add(addGalleryItem);
        tableLayoutArchitect = new AdapterTableLayoutArchitect(this, views, 5);
        tableLayoutArchitect.fileViews();
        this.tagTable.addView(tableLayoutArchitect);
    }


    @Override
    public void update(GalleryItemState state) {

        switch (state) {
            case CHOOSEN:

                break;
            case ACTIVE:
                this.tableLayoutArchitect.activateAll();

                break;
            case INACTIVE:
                this.tableLayoutArchitect.deactivateAll();
                break;
        }
        if (tableLayoutArchitect.notAnyChoosen() && bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        } else if (!tableLayoutArchitect.notAnyChoosen() && bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }


    @Override
    public void extend() {
        AddTagPopUp popupWindow = new AddTagPopUp(this, getUntickedTags());
        popupWindow.addObserver(this);
        // TextView textView= new TextView(this);
        //textView.setText("Dodaj tagi");
        //popupWindow.setContentView(textView);

        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(this.backgroundLayout, Gravity.CENTER_HORIZONTAL, 10, 10);
        popupWindow.update(0, 0, 700, 1200);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }

    private List<Tag> getUntickedTags() {
        TagDataAdapter tagDataAdapter = new TagDataAdapter();
        List<Tag> yetUnchoosenTags = tagDataAdapter.getAll(this);

        List<Tag> garbage = new LinkedList<>();

        for (Tag spontansTag : this.spontan.getTags()) {
            for (Tag tagFromDatabase : yetUnchoosenTags) {
                if (spontansTag.getId() == tagFromDatabase.getId()) garbage.add(tagFromDatabase);
            }
        }


        for (Tag tag : garbage) {
            yetUnchoosenTags.remove(tag);
        }
        return yetUnchoosenTags;
    }


    @Override
    public void update(List<Tag> choosenTags) {
        addTagsToSpontan(choosenTags);
    }

    private void addTagsToSpontan(List<Tag> choosenTags) {

        if (choosenTags.size() != 0) {


            DatabaseAdapter databaseAdapter = new DatabaseAdapter();
            AppDatabase appDatabase = databaseAdapter.getDatabase(this);
            SpontanTagDao spontanTagDao = appDatabase.spontanTagDao();

            List<SpontanTagDto> spontanTagDtoList = new LinkedList<>();


            for (Tag tag : choosenTags) {
                SpontanTagDto spontanTagDto = new SpontanTagDto(this.spontan.getId(), tag.getId());
                spontanTagDtoList.add(spontanTagDto);
                //addTagBehavior(tag);
                this.spontan.getTags().add(tag);
            }

            spontanTagDao.insertAll(spontanTagDtoList);
            this.tagTable.removeAllViews();
            this.tagBehaviorsLayout.removeAllViews();
            loadTags();

        }
    }


    private void addTagBehavior(Tag tag) {
        TagBehavior tagBehavior=tag.getTagBehavior();
        View view= tagBehavior.getView(this);
        if(view!=null) {
            view.setLayoutParams(new LinearLayout.LayoutParams(800, 300));
            this.tagBehaviorsLayout.addView(view);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {


            long photoIdFromDatabase=data.getLongExtra("photoIdFromDatabase",0);
            Toast toast= Toast.makeText(this,photoIdFromDatabase+"",Toast.LENGTH_LONG);
            toast.show();

            if(this.activeTagBehavior!=null)
            {
                this.activeTagBehavior.update(this,data);
            }

            for(Tag tag: this.spontan.getTags()){
                if(tag.getId()==activeTagBehavior.getIdToUpdate()) tag.setTagBehavior(activeTagBehavior);
            }
            updateBehaviors();
            //textView1.setText(message);
        }
    }

    private void updateBehaviors() {
        this.tagBehaviorsLayout.removeAllViews();
        loadTagBehaviors();
    }

    private void loadTagBehaviors() {
        for(Tag tag : this.spontan.getTags()){
            View tagBehaviorView=tag.getTagBehavior().getView(this);
            if(tagBehaviorView!=null)
            this.tagBehaviorsLayout.addView(tagBehaviorView);
        }
    }

    public void startActiveTagBehavior(TagBehavior tagBehavior){
        this.activeTagBehavior=tagBehavior;
    }

    /**Sensord managemant*/
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        inform(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void addSensorObserver(SensorObserver sensorObserver) {
        this.sensorObservers.add(sensorObserver);
    }

    @Override
    public void removeSensorObserver(SensorObserver sensorObserver) {
        this.sensorObservers.remove(sensorObserver);
    }

    @Override
    public void inform(SensorEvent sensorEvent) {
        for(SensorObserver sensorObserver:this.sensorObservers){
            sensorObserver.update(sensorEvent);
        }
    }
}
