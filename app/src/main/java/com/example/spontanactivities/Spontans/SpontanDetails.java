package com.example.spontanactivities.Spontans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.spontanactivities.Activities.SpontanDetails.CustomBottomSheetBehavior;
import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Model.Spontan;
import com.example.spontanactivities.Model.Tag;
import com.example.spontanactivities.Logic.ProgramicPatterns.ObserverGalleryItem;
import com.example.spontanactivities.R;
import com.example.spontanactivities.View.AdapterTableLayoutArchitect;
import com.example.spontanactivities.View.GalleryItem;
import com.example.spontanactivities.View.GalleryItemState;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.LinkedList;
import java.util.List;

public class SpontanDetails extends AppCompatActivity  implements ObserverGalleryItem {

    TextView name;
    Spontan spontan;
    FrameLayout tagTable;
    FrameLayout extras;
    AdapterTableLayoutArchitect tableLayoutArchitect;
    LinearLayout backgroundLayout;

    CustomBottomSheetBehavior<View> bottomSheetBehavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spontan_details);



        Fragment fragment= getSupportFragmentManager().findFragmentById(R.id.filter_fragment);

        bottomSheetBehavior=CustomBottomSheetBehavior.from(fragment.getView());
        bottomSheetBehavior.setState(CustomBottomSheetBehavior.STATE_HIDDEN);
        //bottomSheetBehavior.setBottomSheetCallback(new MyBottomSheetCallback());


        backgroundLayout= findViewById(R.id.detailbackground);
        name= findViewById( R.id.activityname);
        tagTable=findViewById(R.id.tagsTable);
        //extras=findViewById(R.id.extras);
        name.setTextSize(30);
        name.setGravity(Gravity.CENTER);

        Intent intent= getIntent();
        int  id=intent.getIntExtra("id",0);

        DatabaseAdapter databaseAdapter= new DatabaseAdapter();

        this.spontan= new Spontan(databaseAdapter.getDatabase(this).spontanDao().findById(id),this);
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

   /*class MyBottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback {
        @Override
        public void onStateChanged( View bottomSheet, int newState) {

            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                if (bottomSheetBehavior instanceof LockableBottomSheetBehavior) {
                    ((LockableBottomSheetBehavior) bottomSheetBehavior).setLocked(true);
                }
            }
        }

        @Override
        public void onSlide( View bottomSheet, float slideOffset) {}

    }*/

    public void loadTags(){
        DatabaseAdapter databaseAdapter= new DatabaseAdapter();
        List<GalleryItem> views= new LinkedList<>();

        for(Tag tag: databaseAdapter.getDatabase(this).tagDao().getTagListBySpontanId(this,spontan.getId()) ){
            //View view=tag.getIcon(this);
            //view.setBackgroundColor(Color.rgb(152,166,172));

            GalleryItem galleryItem=new GalleryItem(this,null,tag.getImageResource());
            galleryItem.add(this);
            views.add(galleryItem);
        }

         tableLayoutArchitect=new AdapterTableLayoutArchitect(this,views,5);
        tableLayoutArchitect.fileViews();
        this.tagTable.addView(tableLayoutArchitect);

    }
    @Override
    public void update(GalleryItemState state) {



        switch(state){
            case CHOOSEN:

                break;
            case ACTIVE:
                this.tableLayoutArchitect.activateAll();

                break;
            case INACTIVE:
                this.tableLayoutArchitect.deactivateAll();
                break;
        }

        if(tableLayoutArchitect.notAnyChoosen()&& bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        }else if(!tableLayoutArchitect.notAnyChoosen()&& bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        }

    }







}
