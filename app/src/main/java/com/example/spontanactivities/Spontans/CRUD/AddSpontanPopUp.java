package com.example.spontanactivities.Spontans.CRUD;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableRow;

import com.bumptech.glide.Glide;
import com.example.spontanactivities.Data.AppDatabase;
import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Dtos.SpontanDto;
import com.example.spontanactivities.Dtos.SpontanTagDto;
import com.example.spontanactivities.Model.Tag;
import com.example.spontanactivities.R;
import com.example.spontanactivities.Dtos.TagDto;
import com.example.spontanactivities.View.TableLayoutArchitect;

import java.util.LinkedList;
import java.util.List;

public class AddSpontanPopUp {
    PopupWindow popupWindow;
    LinearLayout tableLayout;
    EditText editText;
    Button button;
    List<Tag> spontansTags;

    public AddSpontanPopUp(Context context) {
        this.spontansTags= new LinkedList<>();
        this.popupWindow = new PopupWindow(context);
        popupWindow.setContentView(this.getWindow(context));
    }

    public LinearLayout getWindow(Context context) {

        LinearLayout main = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.addspontanpopupwindow, null);

        this.tableLayout = main.findViewById(R.id.tableofspontans);
        this.button = main.findViewById(R.id.addspontanbutton);
        this.editText = main.findViewById(R.id.newspontanname);

        main.setOrientation(LinearLayout.VERTICAL);
        main.setBackgroundColor(Color.rgb(100, 100, 100));

        DatabaseAdapter databaseAdapter= new DatabaseAdapter();
        TableLayoutArchitect tableLayoutArchitect=fitTagsToTableLayout(context,TagDto.convert(databaseAdapter.getDatabase(context).tagDao().getAll()) );
        tableLayoutArchitect.setMargins(1);
        this.tableLayout.addView(tableLayoutArchitect);

        button.setText("Add");
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setGravity(Gravity.BOTTOM);
        final Context finalContext=context;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSpontanToDatabase(finalContext);

                popupWindow.dismiss();
            }
        });

        return main;
    }

    private void addSpontanToDatabase(Context context){
        String spontanName= editText.getText().toString();
        SpontanDto spontanDto= new SpontanDto(spontanName);
        DatabaseAdapter databaseAdapter= new DatabaseAdapter();
        AppDatabase appDatabase=databaseAdapter.getDatabase(context);
        int spontanId= (int)appDatabase.spontanDao().insert(spontanDto);
        System.out.println("Numer nowego spontana: "+  spontanId);

        List<SpontanTagDto> tagDtoTmpList= new LinkedList<>();
        for(Tag tag: spontansTags){
            tagDtoTmpList.add(new SpontanTagDto(spontanId,tag.getId()));
            System.out.println("SpontanTagDto : "+spontanId+", "+tag.getId());
        }

        appDatabase.spontanTagDao().insertAll(tagDtoTmpList);

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
        addTagToSpontanList(tag);
    }


    private void addTagToSpontanList(Tag tag) {
        this.spontansTags.add(tag);
    }

    private void removeTag(FrameLayout f, Tag tag) {
        unTickView(f);
        removeTadFromSpontanList(tag);

    }



    private void removeTadFromSpontanList(Tag tag) {
        this.spontansTags.remove(tag);
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

    public PopupWindow getPopUp() {
        return this.popupWindow;
    }
}
