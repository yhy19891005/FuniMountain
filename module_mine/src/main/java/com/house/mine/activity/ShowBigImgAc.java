package com.house.mine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;

public class ShowBigImgAc extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView iv = new ImageView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(lp);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        //iv.setBackgroundColor(ContextCompat.getColor(ShowBigImgAc.this, R.color.transparent));
        //iv.setPadding(0, 80, 0, 80);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setContentView(iv);

        String url = getIntent().getStringExtra("url");

        Glide.with(this).load(url).into(iv);
    }

}
