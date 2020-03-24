package com.house.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.funi.net.common.IRequestResult;
import com.funi.net.okhttp.OKHttpRequest;
import com.funi.utils.FuniLog;
import com.funi.utils.ToastUtil;
import com.house.mine.R;
import com.house.mine.activity.InfoDeclareStepFiveAc;
import com.house.mine.activity.ShowBigImgAc;
import com.house.mine.util.StringUtil;
import com.house.mine.util.UrlConfig;
import com.okhttplib.HttpInfo;
import com.okhttplib.callback.Callback;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.impl.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import yin.deng.dyutils.http.MyHttpUtils;

public class ImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater      mInflater;
    private OnItemClickListener mItemClickListener;
    private Context mContext;
    private String mImgType;

    private List<AlbumFile> mAlbumFiles;

    private int DEAL_TYPE_DELETE = 1;
    private int DEAL_TYPE_SHOWBIG = 2;

    public ImgAdapter(Context context, String imgType, OnItemClickListener itemClickListener) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mImgType = imgType;
        this.mItemClickListener = itemClickListener;
    }

    public void notifyDataSetChanged(List<AlbumFile> imagePathList) {
        this.mAlbumFiles = imagePathList;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        AlbumFile albumFile = mAlbumFiles.get(position);
        if (albumFile.getMediaType() == AlbumFile.TYPE_IMAGE) {
            return AlbumFile.TYPE_IMAGE;
        } else {
            return AlbumFile.TYPE_VIDEO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(mInflater.inflate(R.layout.item_rv_img, parent, false), mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ImageViewHolder) holder).setData(mAlbumFiles.get(position));
    }

    @Override
    public int getItemCount() {
        return mAlbumFiles == null ? 0 : mAlbumFiles.size();
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnItemClickListener mItemClickListener;
        private ImageView mIvImage,mIvDelete;
        private LinearLayout mLlNext;
        private TextView mTvUpload;
        private ProgressBar mPbUpload;

        ImageViewHolder(View itemView, OnItemClickListener itemClickListener) {
            super(itemView);
            this.mItemClickListener = itemClickListener;
            this.mIvImage = itemView.findViewById(R.id.img);
            this.mIvDelete = itemView.findViewById(R.id.delete);
            this.mLlNext = itemView.findViewById(R.id.ll_next);
            this.mTvUpload = itemView.findViewById(R.id.tv_upload);
            this.mPbUpload = itemView.findViewById(R.id.pb_upload);

            itemView.setOnClickListener(this);
            mIvImage.setOnClickListener(this);
            mIvDelete.setOnClickListener(this);
            mLlNext.setOnClickListener(this);
        }

        public void setData(AlbumFile albumFile) {
            Album.getAlbumConfig().
                    getAlbumLoader().
                         load(mIvImage, albumFile);
            mLlNext.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                if(v == mIvImage){
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                    //String up = mTvUpload.getText().toString();
                    //if("删除".equals(up)){
                    //    dealFile(DEAL_TYPE_SHOWBIG);
                    //}else {
                    //    mItemClickListener.onItemClick(v, getAdapterPosition());
                    //}
                }else if(v == mIvDelete){
                    if(mAlbumFiles != null){
                        mAlbumFiles.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                }else if(v == mLlNext){
                    int position = getAdapterPosition();
                    AlbumFile file = mAlbumFiles.get(position);
                    String up = mTvUpload.getText().toString();
                    if("上传".equals(up)){
                        upLoadFile(UrlConfig.UF_UPLOAD,file);
                    }else if("删除".equals(up)){
                        //deleteFile();
                        dealFile(DEAL_TYPE_DELETE);
                    }
                }
            }
        }

        private void upLoadFile(final String url,AlbumFile file){
            mTvUpload.setText("上传中");
            mPbUpload.setVisibility(View.VISIBLE);
            mLlNext.setClickable(false);
            mIvDelete.setVisibility(View.GONE);
            final String fileName = new File(file.getPath()).getName();
            OKHttpRequest.uploadFile(mContext,
                                     url + "?_dc=" + System.currentTimeMillis(),
                                     //UrlConfig.UF_UPLOAD,
                                     file.getPath(),
                                     null,
                                     new IRequestResult() {
                                         @Override
                                         public void success(String result) {
                                             createFile(result,fileName);
                                         }

                                         @Override
                                         public void failure(String message) {
                                             mTvUpload.setText("上传");
                                             mPbUpload.setVisibility(View.GONE);
                                             ToastUtil.show(mContext, message);
                                             mLlNext.setClickable(true);
                                             FuniLog.d(url + "  ERROR:  " + message);
                                         }
                                     });
        }

        //private String mImgUrl = "";
        private ArrayList<String> mImgUrlList = new ArrayList<>();
        private void createFile(String result,final String fileName) {
            Log.e("dddddddd","result = " + result);

            try {
                JSONObject object = new JSONObject(result);
                String fileId = object.optString("fileId");
                String fileUrl = object.optString("fileUrl");
                mImgUrlList.add(getAdapterPosition(),fileUrl);
                //mImgUrl = fileUrl;
                JSONArray array = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("imgId",fileId);
                obj.put("imgUrl",fileUrl);
                obj.put("imgName",fileName);
                array.put(object);

                if(mContext instanceof InfoDeclareStepFiveAc){
                    InfoDeclareStepFiveAc ac = (InfoDeclareStepFiveAc) mContext;
                    HashMap<String,String> params = new HashMap<>();
                    params.put("change","false");
                    params.put("dataSource","");
                    params.put("entFileConfId", StringUtil.getEntFileConfIdByImgType(mImgType,ac.mOrgType));
                    params.put("entOrgImgId","");
                    params.put("entOrgQualifyId",ac.mOrgData.get("orgQualifyId"));
                    params.put("fileId","");
                    params.put("fileName",mImgType);
                    params.put("fileNo","");
                    params.put("fileType","2");
                    params.put("fileTypeName","复印件");
                    params.put("id","extModel265");
                    params.put("imgId","");
                    params.put("imgUrl","");
                    params.put("inDate","");
                    params.put("inManCode","");
                    params.put("need","true");
                    params.put("orgFileImgList",array.toString().replaceAll("\\\\",""));
                    params.put("orgType","");
                    params.put("pOrder","0");
                    params.put("pageCount","0");
                    params.put("quantity","2");
                    params.put("status","");
                    params.put("sysCreateTime","");
                    params.put("sysUpdateTime","");

                    final MyHttpUtils myHttpUtils = ac.getHttpUtils();
                    myHttpUtils.sendMsgPost(UrlConfig.FORORGFILE_CREATEFILE,
                                               params,
                                               new Callback() {

                                                   @Override
                                                   public void onSuccess(HttpInfo info)
                                                           throws IOException
                                                   {
                                                       myHttpUtils.initSucessLog(info,true);

                                                       mTvUpload.setText("删除");
                                                       mPbUpload.setVisibility(View.GONE);
                                                       mLlNext.setClickable(true);
                                                       mIvDelete.setVisibility(View.GONE);

                                                       try {
                                                           JSONObject jsonObject = new JSONObject(info.getRetDetail());
                                                           String msg = jsonObject.optString("message");
                                                           if(!TextUtils.isEmpty(msg)){
                                                               ToastUtil.show(mContext,msg);
                                                           }
                                                       } catch (JSONException e) {
                                                           e.printStackTrace();
                                                       }
                                                   }

                                                   @Override
                                                   public void onFailure(HttpInfo info)
                                                           throws IOException
                                                   {
                                                       myHttpUtils.initSucessLog(info,false);

                                                       mTvUpload.setText("上传");
                                                       mPbUpload.setVisibility(View.GONE);
                                                       mLlNext.setClickable(true);
                                                       mIvDelete.setVisibility(View.VISIBLE);

                                                       try {
                                                           JSONObject jsonObject = new JSONObject(info.getRetDetail());
                                                           String msg = jsonObject.optString("message");
                                                           if(!TextUtils.isEmpty(msg)){
                                                               ToastUtil.show(mContext,msg);
                                                           }
                                                       } catch (JSONException e) {
                                                           e.printStackTrace();
                                                       }
                                                   }
                                               });
                }
            } catch (JSONException e) {
                e.printStackTrace();

                mTvUpload.setText("上传");
                mPbUpload.setVisibility(View.GONE);
                mLlNext.setClickable(true);
            }
        }

       private void dealFile(final int dealType){
           if(mContext instanceof InfoDeclareStepFiveAc){
               if(dealType == DEAL_TYPE_DELETE){
                   mTvUpload.setText("删除中");
                   mPbUpload.setVisibility(View.VISIBLE);
                   mLlNext.setClickable(false);
               }

               final InfoDeclareStepFiveAc ac = (InfoDeclareStepFiveAc) mContext;
               HashMap<String,String> params = new HashMap<>();
               params.put("orgType",ac.mOrgType);
               params.put("entOrgQualifyId",ac.mOrgData.get("orgQualifyId"));
               params.put("page","1");
               params.put("start","0");
               params.put("limit","25");
               Log.e("dddddd","ccccccc");
               final MyHttpUtils myHttpUtils = ac.getHttpUtils();
               myHttpUtils.sendMsgPost(UrlConfig.FORORGFILE_GETFILES + "?_dc=" + System.currentTimeMillis(),
                                          params,
                                          new Callback() {

                                              @Override
                                              public void onSuccess(HttpInfo info)
                                                      throws IOException
                                              {
                                                  myHttpUtils.initSucessLog(info,true);
                                                  String entFileConfId0 = StringUtil.getEntFileConfIdByImgType(mImgType,ac.mOrgType);
                                                  try {
                                                      JSONObject jsonObject = new JSONObject(info.getRetDetail());
                                                      JSONArray array = jsonObject.optJSONObject("result").optJSONArray("list");
                                                      for(int i = 0; i < array.length(); i ++){
                                                          String fileName = array.optJSONObject(i).optString("fileName");
                                                          String entFileConfId = array.optJSONObject(i).optString("entFileConfId");
                                                          if(entFileConfId.equals(entFileConfId0)){
                                                              String fileId = array.optJSONObject(i).optString("id");
                                                              Log.e("dddddd","aaaaaaa");
                                                              if(dealType == DEAL_TYPE_DELETE){
                                                                  Log.e("dddddd","bbbbbbb");
                                                                  removeFile(fileId,ac);
                                                              }else if(dealType == DEAL_TYPE_SHOWBIG){
                                                                  showBigImg(fileId,ac);
                                                              }
                                                          }
                                                          Log.e("ddddddd",fileName + " <-----> " + entFileConfId);

                                                      }
                                                  } catch (JSONException e) {
                                                      e.printStackTrace();
                                                  }
                                              }

                                              @Override
                                              public void onFailure(HttpInfo info)
                                                      throws IOException
                                              {
                                                  myHttpUtils.initSucessLog(info,false);
                                              }
                                          });
           }
       }

        private void removeFile(String fileId,InfoDeclareStepFiveAc ac){
            HashMap<String,String> params = new HashMap<>();
            params.put("fileId",fileId);
            Log.e("dddddd","ddddddd");
            final MyHttpUtils myHttpUtils = ac.getHttpUtils();
            myHttpUtils.sendMsgPost(UrlConfig.FORORGFILE_REMOVE + "?_dc=" + System.currentTimeMillis(),
                                    params,
                                    new Callback() {

                                        @Override
                                        public void onSuccess(HttpInfo info)
                                                throws IOException
                                        {
                                            myHttpUtils.initSucessLog(info,true);

                                            mTvUpload.setText("上传");
                                            mPbUpload.setVisibility(View.GONE);
                                            mLlNext.setClickable(true);

                                            if(mAlbumFiles != null){
                                                mAlbumFiles.remove(getAdapterPosition());
                                                notifyDataSetChanged();
                                            }

                                            try {
                                                JSONObject jsonObject = new JSONObject(info.getRetDetail());
                                                String msg = jsonObject.optString("message");
                                                if(!TextUtils.isEmpty(msg)){
                                                    ToastUtil.show(mContext,msg);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure(HttpInfo info)
                                                throws IOException
                                        {
                                            myHttpUtils.initSucessLog(info,false);

                                            mTvUpload.setText("删除");
                                            mPbUpload.setVisibility(View.GONE);
                                            mLlNext.setClickable(true);

                                            try {
                                                JSONObject jsonObject = new JSONObject(info.getRetDetail());
                                                String msg = jsonObject.optString("message");
                                                if(!TextUtils.isEmpty(msg)){
                                                    ToastUtil.show(mContext,msg);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
        }

        private void showBigImg(String fileId,InfoDeclareStepFiveAc ac){
            HashMap<String,String> params = new HashMap<>();
            params.put("orgFileId",fileId);
            final MyHttpUtils myHttpUtils = ac.getHttpUtils();
            myHttpUtils.sendMsgPost(UrlConfig.FORORGFILE_GETFILEIMG + "?_dc=" + System.currentTimeMillis(),
                                    params,
                                    new Callback() {

                                        @Override
                                        public void onSuccess(HttpInfo info)
                                                throws IOException
                                        {
                                            myHttpUtils.initSucessLog(info,true);
                                            Intent intent = new Intent(mContext, ShowBigImgAc.class);
                                            intent.putExtra("url",mImgUrlList);
                                            mContext.startActivity(intent);
                                        }

                                        @Override
                                        public void onFailure(HttpInfo info)
                                                throws IOException
                                        {
                                            myHttpUtils.initSucessLog(info,false);
                                        }
                                    });
        }

    }

}

  /*
    // extends BaseAdapter {
    private ArrayList<String> mData = new ArrayList<>();

    private Context mContext;

    public ImgAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<String> data){
        if(data != null){
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addData(ArrayList<String> data){
        if(data != null){
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_rv_img,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.img = convertView.findViewById(R.id.img);
        holder.delete = convertView.findViewById(R.id.delete);
        final String path = mData.get(position);
        Bitmap bitmap = BitmapUtil.getLoacalBitmap(path);
        if(BitmapUtil.isCompress(path)){
            bitmap = BitmapUtil.compressImage(bitmap);
        }
        holder.img.setImageBitmap(bitmap);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(path);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder{
        ImageView delete;
        SquareImageView img;
    }*/
