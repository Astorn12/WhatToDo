package com.example.spontanactivities.Logic.TagBehaviors.FriendBehavior;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.spontanactivities.Logic.TagBehaviors.CommonTagBehavior;
import com.example.spontanactivities.R;
import com.example.spontanactivities.Views.GalleryLibrary.GalleryItemPackage.TickedListItem;
import com.example.spontanactivities.Views.GalleryLibrary.GalleryPackage.GalleryImp;
import com.example.spontanactivities.Views.GalleryLibrary.PopUpWindowPackage.PopUpGalleryImp;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.LinkedList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;
@JsonTypeName("friends")
public class Friends extends CommonTagBehavior {





    @Override
    public View getAddingView(Context context) {
        ImageView imageView= new ImageView(context);
        imageView.setImageResource(R.drawable.friends);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Friend> friendList=getContactList(context);

                GalleryImp galleryImp= new GalleryImp(context);
                for(Friend friend: friendList){
                    galleryImp.addListItem(new TickedListItem(context, friend));
                }


                PopUpGalleryImp popUpGalleryImp= new PopUpGalleryImp(context,galleryImp);
                popUpGalleryImp.show(imageView);
            }
        });
        return imageView;
    }

    @Override
    public View getDisplayView(Context context) {

        return null;
    }

    @Override
    public View getChangingView(Context context) {
        return null;
    }

    @Override
    public boolean hasBeenInitialized(Context context) {
        return false;
    }




    private List<Friend> getContactList(Context context) {
        List<Friend> friendsList= new LinkedList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Friend friend= new Friend(name, phoneNo);
                        friendsList.add(friend);
                        Log.i(TAG, "Name: " + name);
                        Log.i(TAG, "Phone Number: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
        return friendsList;
    }
}
