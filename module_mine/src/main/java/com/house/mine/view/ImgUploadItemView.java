package com.house.mine.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.house.mine.R;
import com.house.mine.adapter.ImgAdapter;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.album.impl.OnItemClickListener;
import com.yanzhenjie.album.widget.divider.Api21ItemDivider;
import com.yanzhenjie.album.widget.divider.Divider;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ImgUploadItemView extends LinearLayout
        implements View.OnClickListener, ChoosePicPopuwindow.OnPicSelectListener
{

    private Context      mContext;
    private String       mImgType;
    private TextView     mTvImgType;
    private ImageView    mIvAddView;
    private RecyclerView mRvViews;

    private ArrayList<AlbumFile> mAlbumFiles;
    private ChoosePicPopuwindow  mPopuwindow;
    private ImgAdapter           mAdapter;

    public ImgUploadItemView(Context context) {
        this(context,null);
    }

    public ImgUploadItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImgUploadItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImgUploadItemView);
        mImgType = typedArray.getString(R.styleable.ImgUploadItemView_img_type);
        typedArray.recycle();

        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_img_upload, this);
        mTvImgType = view.findViewById(R.id.tv_img_type);
        mIvAddView = view.findViewById(R.id.iv_add_view);
        mRvViews = view.findViewById(R.id.imgs);

        mRvViews.setLayoutManager(new GridLayoutManager(mContext, 3));
        Divider divider = new Api21ItemDivider(Color.TRANSPARENT, 10, 10);
        mRvViews.addItemDecoration(divider);

        mAdapter = new ImgAdapter(mContext, mImgType,new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                previewImage(position);
            }
        });
        mRvViews.setAdapter(mAdapter);

        mTvImgType.setText(mImgType);
        mIvAddView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mIvAddView){
            if(mAdapter.getItemCount() >= 6){
                Toast.makeText(mContext,"最多只能上传6张照片",Toast.LENGTH_SHORT).show();
                return;
            }
            if(mContext instanceof Activity){
                if(mPopuwindow == null){
                    mPopuwindow = new ChoosePicPopuwindow((Activity)mContext);
                }
                mPopuwindow.setOnPicSelectListener(this);
                mPopuwindow.show(this);
            }

        }
    }

    //从相册中选取
    @Override
    public void mediaType() {
        mPopuwindow.dismiss();

        Album.image(mContext)
             .multipleChoice()
             .camera(true)
             .columnCount(3)
             .selectCount(6)
             .checkedList(mAlbumFiles)
             .widget(
                     Widget.newDarkBuilder(mContext)
                           .title("选取图片")
                           .build()
             )
             .onResult(new Action<ArrayList<AlbumFile>>() {
                 @Override
                 public void onAction(@NonNull ArrayList<AlbumFile> result) {
                     mAlbumFiles = result;
                     //for (int i = 0; i < mAlbumFiles.size(); i++) {
                     //   String path = mAlbumFiles.get(i).getPath();
                     //    mList.add(path);
                     //   Log.e("dddddd","path = " + path);
                     //}
                     mAdapter.notifyDataSetChanged(mAlbumFiles);
                     //mTvMessage.setVisibility(result.size() > 0 ? View.VISIBLE : View.GONE);
                 }
             })
             .onCancel(new Action<String>() {
                 @Override
                 public void onAction(@NonNull String result) {
                     Toast.makeText(mContext, "取消选择", Toast.LENGTH_LONG).show();
                 }
             })
             .start();
    }

    //拍摄
    @Override
    public void photoType() {
        mPopuwindow.dismiss();
        if(mAlbumFiles == null){
            mAlbumFiles = new ArrayList<>();
        }
        Album.camera(mContext)
             .image()
             //.filePath()
             .onResult(new Action<String>() {
                 @Override
                 public void onAction(@NonNull String result) {
                     Log.e("dddddd","result = " + result);
                     AlbumFile file = new AlbumFile();
                     file.setPath(result);
                     file.setAddDate(System.currentTimeMillis());
                     mAlbumFiles.add(file);
                     mAdapter.notifyDataSetChanged(mAlbumFiles);
                     //mTextView.setText(result);
                     //Album.getAlbumConfig()
                     //     .getAlbumLoader()
                     //     .load(mImageView, result);
                 }
             })
             .onCancel(new Action<String>() {
                 @Override
                 public void onAction(@NonNull String result) {
                     Toast.makeText(mContext, "取消拍照", Toast.LENGTH_LONG).show();
                 }
             })
             .start();
    }

    /**
     * Preview image, to album.
     */
    private void previewImage(int position) {
        if (mAlbumFiles == null || mAlbumFiles.size() == 0) {
            Toast.makeText(mContext, "bbbbb", Toast.LENGTH_LONG).show();
        } else {
            Album.galleryAlbum(mContext)
                 .checkable(true)
                 .checkedList(mAlbumFiles)
                 .currentPosition(position)
                 .widget(
                         Widget.newDarkBuilder(mContext)
                               .title("图片预览")
                               .build()
                 )
                 .onResult(new Action<ArrayList<AlbumFile>>() {
                     @Override
                     public void onAction(@NonNull ArrayList<AlbumFile> result) {
                         mAlbumFiles = result;
                         mAdapter.notifyDataSetChanged(mAlbumFiles);
                     }
                 })
                 .start();
        }
    }
}
