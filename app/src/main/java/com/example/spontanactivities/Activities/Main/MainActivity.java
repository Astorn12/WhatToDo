package com.example.spontanactivities.Activities.Main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.spontanactivities.Data.AppDatabase;
import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Dtos.SpontanDto;
import com.example.spontanactivities.Dtos.TagDto;
import com.example.spontanactivities.Tutorial.BeginningInicjalization;
import com.example.spontanactivities.Model.Tag;
import com.example.spontanactivities.Logic.ProgramicPatterns.Observer;
import com.example.spontanactivities.R;
import com.example.spontanactivities.Model.Spontan;
import com.example.spontanactivities.Activities.SpontanDetails.SpontanAdapter;
import com.example.spontanactivities.Tags.TagMenu;


import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Observer {

    private RecyclerView recyclerView;
    private SpontanAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private LinearLayout tagsView;
    private TagMenu tagMenu;
    private LinearLayout mainScreen;

    private Boolean tagFilterModyfication;


    private boolean destroyingFlag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.tagsView = findViewById(R.id.tags);
        this.tagFilterModyfication= false;
        this.mainScreen = findViewById(R.id.mainscreen);

        setSupportActionBar(toolbar);

        FloatingActionMenu fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.activitieslist);
        final Context finalContextPointer=this;
        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                if(velocityY<0 && velocityY<-300 ) {
                    //List<Spontan> filtered= tagMenu.overallFilter(loadSpontans());
                    if(tagFilterModyfication) {
                        mAdapter.updateSpontanList(tagMenu.overallFilter(loadSpontans()));
                        mAdapter.notifyDataSetChanged();
                        tagFilterModyfication=false;
                    }
                }
                return false;
            }
        });


        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(destroyingFlag) clearDatabase();
        /**zalouduje podstawowe przykłady jeśli baza danych jest pusta,
        *  jest to rozumiane jako pierwsza inicjalizacja apki*/
        BeginningInicjalization beginningInicjalization = new BeginningInicjalization();
        beginningInicjalization.run(this);
        //List<Spontan>loadSpontans= loadSpontans();
        mAdapter = new SpontanAdapter(loadSpontans());
        recyclerView.setAdapter(mAdapter);
        this.tagMenu = new TagMenu(this.tagsView, loadTags());
        this.tagMenu.load();
        tagMenu.add(this);
        checkPermissions();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 0);

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS}, 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast toast=Toast.makeText(getApplicationContext(),
                "Resume",
                Toast.LENGTH_SHORT);
        toast.show();
        mAdapter.updateSpontanList(loadSpontans());
        mAdapter.notifyDataSetChanged();


    }

    public boolean isEquals(List<Spontan> firstList, List<Spontan> secondList){
        for(Spontan first: firstList){
            for (Spontan second: secondList){
                if(first.getId()!=second.getId()) return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void addCommonTask(View view) {

        AddSpontanPopUp addSpontanPopUp = new AddSpontanPopUp(this);
        PopupWindow popupWindow = addSpontanPopUp.getPopUp();

        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(mainScreen, Gravity.CENTER_HORIZONTAL, 10, 10);
        popupWindow.update(0, 0, 700, 1200);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                mAdapter.updateSpontanList(loadSpontans());
                mAdapter.notifyDataSetChanged();

                            }
        });
        dimBehind(popupWindow);
    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }

    public List<Tag> loadTags() {
        DatabaseAdapter databaseAdapter = new DatabaseAdapter();
        AppDatabase appDatabase = databaseAdapter.getDatabase(this);

        List<TagDto> tagDtos = appDatabase.tagDao().getAll();
        List<Tag> tagsToReturn = new LinkedList<>();
        for (TagDto tagDto : tagDtos) {
            tagsToReturn.add(new Tag(tagDto));
        }
        return tagsToReturn;
    }

    public List<Spontan> loadSpontans() {

        DatabaseAdapter databaseAdapter = new DatabaseAdapter();
        AppDatabase appDatabase = databaseAdapter.getDatabase(this);


        List<SpontanDto> spontanDtos = appDatabase.spontanDao().getAll();
        List<Spontan> spontanToReturn = new LinkedList<>();
        for (SpontanDto spontanDto : spontanDtos) {
            spontanToReturn.add(new Spontan(spontanDto, this));
        }

        return spontanToReturn;
    }


    private void clearDatabase(){
        DatabaseAdapter databaseAdapter= new DatabaseAdapter();

        AppDatabase appDatabase= databaseAdapter.getDatabase(this);
        appDatabase.clearAllTables();

    }


    @Override
    public void update() {
        this.tagFilterModyfication=true;
    }
}
