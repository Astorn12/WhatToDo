package com.example.spontanactivities.Model;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.bumptech.glide.Glide;
import com.example.spontanactivities.Dtos.TagDto;
import com.example.spontanactivities.Helpers.SerialaizerDeserializer;
import com.example.spontanactivities.Logic.TagBehaviors.EmptyTagBehavior;
import com.example.spontanactivities.Logic.TagBehaviors.TagBehavior;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Tag {
    @PrimaryKey
    int id;
    String name;
    int imageResource;
    TagBehavior tagBehavior;

    @Ignore
    public Tag(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
        this.tagBehavior=new EmptyTagBehavior();
    }

    public Tag(int id,String name, int imageResource) {
        this.id=id;
        this.name = name;
        this.imageResource = imageResource;
    }
    public Tag(String name, int imageResource,TagBehavior tagBehavior) {

        this.name = name;
        this.imageResource = imageResource;
        this.tagBehavior= tagBehavior;
    }

    public Tag(TagDto tagDto){
        this.id=tagDto.getId();
        this.name= tagDto.getName();
        this.imageResource= tagDto.getImageResource();
        this.tagBehavior= SerialaizerDeserializer.deserialize(tagDto.getJSON());
        tagBehavior.setIdToUpdate(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public View getView(Context context) {
        LinearLayout linearLayout= new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView= new TextView(context);
        textView.setText(this.name);
        textView.setGravity(Gravity.CENTER);
        linearLayout.addView(textView);

        ImageView imageView= new ImageView(context);
        Glide.with(context)
                .load(this.imageResource)
                .into(imageView);

        imageView.setAdjustViewBounds(true);

        linearLayout.addView(imageView);
        imageView.getLayoutParams().height=60;

        return linearLayout;
    }
    public ImageView getIcon(Context context){
        ImageView imageView= new ImageView(context);

        Glide.with(context)
                .load(this.getImageResource())
                .into(imageView);
        imageView.setAdjustViewBounds(true);


        return imageView;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id=id;
    }

    public TagBehavior getTagBehavior() {
        return tagBehavior;
    }

    public static List<TagDto> convert (List<Tag> tags){
        List<TagDto> tagDtos= new LinkedList<>();

        for(Tag tag: tags){
            tagDtos.add(new TagDto(tag));
        }
        return tagDtos;
    }

    public void setTagBehavior(TagBehavior tagBehavior) {
        this.tagBehavior = tagBehavior;
    }
}

