package com.example.spontanactivities.Logic.TagBehaviors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.View;

import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.lifecycle.LifecycleOwner;

import com.example.spontanactivities.Activities.CameraActivity;
import com.example.spontanactivities.Activities.SpontanDetails.SpontanDetails;
import com.example.spontanactivities.Data.AppDatabase;
import com.example.spontanactivities.Data.Daos.Interfaces.PhotoDao;
import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Dtos.PhotoDto;
import com.example.spontanactivities.Helpers.SerialaizerDeserializer;
import com.example.spontanactivities.Model.Spontan;
import com.example.spontanactivities.R;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.File;
import java.util.concurrent.Executors;

@JsonTypeName("photo")
public class Photo extends CommonTagBehavior {

    int photoId;


    public Photo(){

    }
    @Override
    public View getAddingView(Context context) {
        LinearLayout mainContainer= new LinearLayout(context);
        mainContainer.setOrientation(LinearLayout.HORIZONTAL);

        ImageView  cameraView= new ImageView(context);
        cameraView.setImageResource(R.drawable.camera);
        cameraView.setAdjustViewBounds(true);

        ImageView photoFromGalleryView= new ImageView(context);
        photoFromGalleryView.setImageResource(R.drawable.photoicon);
        photoFromGalleryView.setAdjustViewBounds(true);

        mainContainer.addView(cameraView);
        mainContainer.addView(photoFromGalleryView);


        setStartCameraListener(cameraView);
        setGalleryListener(photoFromGalleryView);

        return mainContainer;
    }
    @JsonIgnore
    public void setStartCameraListener(final ImageView imageView){
        Photo photo=this;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpontanDetails spontanDetails=((SpontanDetails)imageView.getContext());
                spontanDetails.startActiveTagBehavior(photo);
                Intent intent= new Intent(spontanDetails, CameraActivity.class);
                spontanDetails.startActivityForResult(intent,2);

            }
        });

    }

    @JsonIgnore
    public void setGalleryListener(final ImageView imageView){
        SpontanDetails spontanDetails=((SpontanDetails) imageView.getContext());
        spontanDetails.startActiveTagBehavior(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                spontanDetails.startActivityForResult(i, 2);
            }
        });
    }



    @Override
    public View getDisplayView(Context context) {
        DatabaseAdapter databaseAdapter= new DatabaseAdapter();
        AppDatabase appDatabase= databaseAdapter.getDatabase(context);
        PhotoDao photoDao= appDatabase.photoDao();

        PhotoDto  potoDto= photoDao.findById(this.photoId);
        File imgFile = new File(potoDto.url);

        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

        ImageView displayView= new ImageView(context);
        displayView.setImageBitmap(myBitmap);

        return displayView;
    }

    @Override
    public View getChangingView(Context context) {
        return null;
    }

    @Override
    public boolean hasBeenInitialized(Context context) {
        return this.photoId!=0;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    @Override
    public void update(Context context, Intent intent) {
        long photoIdFromDatabase=intent.getLongExtra("photoIdFromDatabase",0);
        if (photoIdFromDatabase!=0)
            this.photoId=(int)photoIdFromDatabase;
        else{/**Zdjęcie jest z galeri i należy najpierw zapisać jego ścieżkę do bazy danych*/
            Uri selectedImage = intent.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = context.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            DatabaseAdapter databaseAdapter= new DatabaseAdapter();
            AppDatabase appDatabase=databaseAdapter.getDatabase(context);
            PhotoDao photoDao= appDatabase.photoDao();
            PhotoDto photoDto= new PhotoDto(picturePath);
            long newPhotoPathId= photoDao.insert(photoDto);
            this.photoId=(int) newPhotoPathId;
        }


        super.update(context, intent);
    }
}
