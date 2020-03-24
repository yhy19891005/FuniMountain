package com.house.mine.bean;

import com.yanzhenjie.album.AlbumFile;

import java.io.File;

public class UpImageInfo extends AlbumFile {

    private String  url;
    private File    mFile;
    private boolean isImage;

    public UpImageInfo() {
        this.isImage = true;
    }

    public UpImageInfo(boolean isImage) {
        this.isImage = isImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    public File getFile() {
        return mFile;
    }

    public void setFile(String url) {
        File file = new File(url);
        this.mFile = file;
    }
}

