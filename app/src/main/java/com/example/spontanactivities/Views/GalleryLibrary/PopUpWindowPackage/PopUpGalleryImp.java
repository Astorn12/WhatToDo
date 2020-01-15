package com.example.spontanactivities.Views.GalleryLibrary.PopUpWindowPackage;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.spontanactivities.Views.GalleryLibrary.GalleryPackage.Gallery;
import com.example.spontanactivities.Views.GalleryLibrary.GalleryPackage.GalleryImp;

public class PopUpGalleryImp extends PopupWindow implements PopUpGallery{
    GalleryImp galleryImp;

    public PopUpGalleryImp(Context context, GalleryImp galleryImp) {
        super(context);
        this.galleryImp = galleryImp;
        this.setContentView(galleryImp);
    }


    @Override
    public void show(View view) {
        this.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 10, 10);
        this.update(0, 0, 700, 1200);
        this.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        dimBehind();

    }

    @Override
    public void hide() {
        this.dismiss();
    }

    @Override
    public Gallery getGallery() {
        return this.galleryImp;
    }

    public  void dimBehind() {
        View container;
        if (this.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) this.getContentView().getParent();
            } else {
                container = this.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) this.getContentView().getParent().getParent();
            } else {
                container = (View) this.getContentView().getParent();
            }
        }
        Context context = this.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }
}
