package com.example.spontanactivities.Logic.TagBehaviors.FriendBehavior;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.spontanactivities.Views.GalleryLibrary.GalleryItemPackage.Presentable;

import org.w3c.dom.Text;

public class Friend implements Presentable {
    String name;
    String phoneNumber;

    public Friend(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public View getView(Context context) {
        LinearLayout mainContainer= new LinearLayout(context);
        mainContainer.setBackgroundColor(Color.WHITE);
        mainContainer.setOrientation(LinearLayout.VERTICAL);
        mainContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        TextView nameView= new TextView(context);
        nameView.setText("Name: "+ this.name);
        nameView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        TextView phoneNumberView= new TextView(context);
        phoneNumberView.setText("Phone Number: "+phoneNumber);
        phoneNumberView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        mainContainer.addView(nameView);
        mainContainer.addView(phoneNumberView);

        return mainContainer;

    }
}
