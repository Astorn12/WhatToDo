package com.example.spontanactivities.Views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.spontanactivities.R;

import java.util.List;

public class TableLayoutArchitect extends TableLayout {

    List<View> views;
    int mod;
    int border=15;
    int margins=10;


    public TableLayoutArchitect(Context context, List<View> views, int mod) {
        super(context);
        this.views = views;
        this.mod = mod;
        this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setBackground(getResources().getDrawable(R.drawable.layoutbordershape));
        this.setPadding(border,border,border,border);



    }



    public void fileViews() {

        int iterator = 0;

        TableRow tableRow = null;//= new TableRow(context);
        for (View view : views) {
            if (iterator % mod == 0) {
                tableRow = new TableRow(this.getContext());
                tableRow.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));

                this.addView(tableRow);
            }
            iterator++;
            TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
            layoutParams.setMargins(margins,margins,margins,margins);

            view.setLayoutParams(layoutParams);
            view.setBackgroundColor(Color.WHITE);





            tableRow.addView(view);


        }



    }


    public void setBorder(int border) {
        this.border = border;
    }

    public void setMargins(int margins) {
        this.margins = margins;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }





}
