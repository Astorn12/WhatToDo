package com.example.spontanactivities.Tags;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;

import com.example.spontanactivities.Model.Spontan;
import com.example.spontanactivities.Model.Tag;
import com.example.spontanactivities.Logic.ProgramicPatterns.Observed;

import java.util.LinkedList;
import java.util.List;

public class TagMenu extends Observed {
    List<Tag> allTags;
    LinearLayout linearLayout;


    /**
     * have to contain any tag from list
     */
  //  List<Tag> yellow;
    /**
     * have to contain each tags from list
     */
  //  List<Tag> orange;
    /**
     * can't contain any tag from list
     */
   // List<Tag> red;
        List<Tag> green;



    public TagMenu(LinearLayout linearLayout, List<Tag> allTags) {
        this.linearLayout = linearLayout;
        this.allTags = allTags;

       // this.yellow= new LinkedList<>();
       // this.orange= new LinkedList<>();
       // this.red= new LinkedList<>();
        this.green= new LinkedList<>();

    }
    public void load() {

        for (Tag tag : this.allTags) {
            View view = tag.getView(this.linearLayout.getContext());
            view.setBackgroundColor(Color.TRANSPARENT);
            this.linearLayout.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ColorDrawable drawable = (ColorDrawable) v.getBackground();
                    int color = drawable.getColor();


                    int newColor=0;
                    switch (color) {
                        case Color.TRANSPARENT:
                            newColor=Color.GREEN;
                            v.setBackgroundColor(newColor);

                            break;
                        case Color.GREEN:
                            newColor=Color.TRANSPARENT;
                            v.setBackgroundColor(newColor);
                            break;

                    }
                    manageTagChangesStates(newColor, v);
                    inform();
                }
            });


        }

    }
    private void manageTagChangesStates(int finalColor, View view) {
        int index = this.linearLayout.indexOfChild(view);

        Tag tag = this.allTags.get(index);
        switch (finalColor) {
            case Color.TRANSPARENT:
                if (green.contains(tag))
                    this.green.remove(tag);
                break;
            case Color.GREEN:

                this.green.add(tag);
                break;
        }
    }

    public List<Spontan> overallFilter(List<Spontan> spontans) {
        if(this.green.size()==0) return spontans;
        for(Tag tag:this.allTags){
            System.out.println("T"+tag.getName());
        }
        List<Spontan> filteredList = new LinkedList<>();
        for (Spontan spontan : spontans) {
            if (singleFilter(spontan)) {
                filteredList.add(spontan);
            }
        }
        return filteredList;
    }

    private boolean singleFilter(Spontan spontan) {
        if (hasAnyFilter(spontan) )
            return true;
        else return false;
    }

    private boolean hasAnyFilter(Spontan spontan) {
        for (Tag tagSpontan : spontan.getTags()) {
            for (Tag tagDatabase : this.green) {
                if (tagSpontan.getName().equals(tagDatabase.getName())) return true;
            }
        }
        return false;
    }

    public List<Tag> getTags(){
        return this.allTags;
    }


}
