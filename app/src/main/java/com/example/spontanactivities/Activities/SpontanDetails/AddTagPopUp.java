package com.example.spontanactivities.Activities.SpontanDetails;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.bumptech.glide.Glide;
import com.example.spontanactivities.Data.AppDatabase;
import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Dtos.SpontanDto;
import com.example.spontanactivities.Dtos.SpontanTagDto;
import com.example.spontanactivities.Dtos.TagDto;
import com.example.spontanactivities.Logic.ProgramicPatterns.TagTableObserved;
import com.example.spontanactivities.Logic.ProgramicPatterns.TagTableObserver;
import com.example.spontanactivities.Model.Tag;
import com.example.spontanactivities.R;
import com.example.spontanactivities.Views.TableLayoutArchitect;

import java.util.LinkedList;
import java.util.List;

public class AddTagPopUp extends PopupWindow implements TagTableObserved {
    LinearLayout tableLayout;

    Button button;
    List<Tag> allNotChoosenTags;

    List<TagTableObserver> observers;

    List<Tag> tickedTags= new LinkedList<>();

    Context context;
    public AddTagPopUp(Context context, List<Tag> tags) {
        super(context);
        this.context=context;

        observers= new LinkedList<>();

        this.allNotChoosenTags=tags;

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        this.setContentView(this.getWindow());
    }

    private Context getContext(){
        return this.context;
    }


    public LinearLayout getWindow() {

        LinearLayout main = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.addspontanpopupwindow, null);

        this.tableLayout = main.findViewById(R.id.tableofspontans);
        this.button = main.findViewById(R.id.addspontanbutton);


        main.setOrientation(LinearLayout.VERTICAL);
        main.setBackgroundColor(Color.rgb(100, 100, 100));

        DatabaseAdapter databaseAdapter= new DatabaseAdapter();
        TableLayoutArchitect tableLayoutArchitect=fitTagsToTableLayout(getContext(), this.allNotChoosenTags );
        tableLayoutArchitect.setMargins(1);
        this.tableLayout.addView(tableLayoutArchitect);

        button.setText("Add");
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setGravity(Gravity.BOTTOM);
        final Context finalContext=getContext();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updateSpontan(finalContext);
                inform();
                dismiss();
            }
        });

        return main;
    }


    public List<Tag> getTickedTags(){
        return this.tickedTags;
    }



    private TableLayoutArchitect fitTagsToTableLayout(Context context, List<Tag> tags) {

        List<View> frameLayouts= new LinkedList<>();
        for (Tag tag : tags) {


            final ImageView view = tag.getIcon(context);
            view.setAdjustViewBounds(true);

            FrameLayout tagContainer = new FrameLayout(context);
            tagContainer.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            tagContainer.addView(view);
            tagContainer.setBackgroundColor(Color.WHITE);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            frameLayouts.add(tagContainer);

            final Tag finalTag = tag;
            tagContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manageViewClick((FrameLayout) v, finalTag);
                }
            });

        }
        TableLayoutArchitect tableLayoutArchitect=new TableLayoutArchitect(context, frameLayouts,5);
        tableLayoutArchitect.setMargins(1);
        tableLayoutArchitect.fileViews();
        return tableLayoutArchitect;
    }

    public void manageViewClick(FrameLayout f, Tag tag) {
        int i = f.getChildCount();
        if (i == 1) {

            addTag(f, tag);
        } else removeTag(f, tag);
    }

    private void addTag(FrameLayout f, Tag tag) {
        tickView(f);
        addToTickedList(tag);

    }

    private void addToTickedList(Tag tag) {
        this.tickedTags.add(tag);
    }

    private void removeTag(FrameLayout f, Tag tag) {
        unTickView(f);
        removeFromTickedList(tag);
    }

    private void removeFromTickedList(Tag tag) {
        this.tickedTags.remove(tag);
    }

    private void unTickView(FrameLayout f) {
        f.removeViewAt(1);
    }

    public void tickView(FrameLayout f) {
        int i = f.getChildCount();
        ImageView tick = new ImageView(f.getContext());
        Glide.with(f.getContext()).load(R.drawable.greentick).into(tick);
        f.addView(tick);
        tick.getLayoutParams().width = 50;
        tick.getLayoutParams().height = 50;
        //  if(f.getWidth()!=0) {
        tick.setX(f.getWidth() - tick.getLayoutParams().width * (i));
        //    }
        // else   tick.setX(0);
        //tick.setX(50);
        tick.setY(0);
        tick.invalidate();
        f.invalidate();
        f.refreshDrawableState();
    }


    @Override
    public void addObserver(TagTableObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(TagTableObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void inform() {

        List<Tag> tags=getTickedTags();
        for(TagTableObserver observer: this.observers){
            observer.update(tags);
        }
    }
}
