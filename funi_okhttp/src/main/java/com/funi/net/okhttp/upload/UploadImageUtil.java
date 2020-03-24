package com.funi.net.okhttp.upload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.funi.utils.FuniLog;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

/**
 * UploadImageUtil
 *
 * @Description:上传图片工具类
 * @Author: pengqiang.zou
 * @CreateDate: 2019-02-26 14:50
 */
public class UploadImageUtil {
    //图片最大为3M
    private static final int MAX_IMAGE_SIZE = 3 * 1024;

    /**
     * 获得文件类型
     *
     * @param filePath
     * @return
     */
    public static String guessMimeType(String filePath) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();

        String mimType = fileNameMap.getContentTypeFor(filePath);

        if (TextUtils.isEmpty(mimType)) {
            return "application/octet-stream";
        }
        return mimType;
    }


    /**
     * 判断一个文件是否是图片
     *
     * @param filePath
     * @return
     */
    public static boolean isImageFile(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        if (options.outWidth == -1) {
            return false;
        }
        return true;
    }

    /**
     * 是否需要压缩图片
     * 大于3M压缩
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static boolean isCompress(File file) {
        try {
            FileInputStream fs = new FileInputStream(file);
            int len = fs.available() / 1024;
            if (len < MAX_IMAGE_SIZE) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 压缩图片质量
     *
     * @param bitmap
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        if (baos.toByteArray().length / 1024 >= MAX_IMAGE_SIZE) {
            //循环判断如果压缩后图片是否大于3m,大于继续压缩
            while (baos.toByteArray().length / 1024 > MAX_IMAGE_SIZE) {
                //重置baos即清空baos
                baos.reset();
                //这里压缩options%，把压缩后的数据存放到baos中
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                //每次都减少10
                options -= 10;
            }
            //把压缩后的数据baos存放到ByteArrayInputStream中
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            //把ByteArrayInputStream数据生成图片
            Bitmap bitmap2 = BitmapFactory.decodeStream(isBm, null, null);
            FuniLog.d("已经超过3M");
            return bitmap2;
        } else {
            FuniLog.d("没超过3M");
            return bitmap;
        }
    }


    /**
     * 压缩图片尺寸
     *
     * @param filePath 图片路径
     * @return
     */
    public static File compressBySize(String filePath) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        opts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, opts);
        // 得到图片的宽度、高度；
        float imgWidth = opts.outWidth;
        float imgHeight = opts.outHeight;

        FuniLog.d("W:" + imgWidth);
        FuniLog.d("H:" + imgHeight);
        //压缩比值越小压缩出的图片越大
        opts.inSampleSize = 3;
        //设置好缩放比例后，加载图片进内容；
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(filePath, opts);

        bitmap = compressImage(bitmap);
        File file = null;
        try {
            file = saveFile(bitmap, 100);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * 压缩之后保存到sdk中
     *
     * @param bm
     * @param size
     * @return
     * @throws Exception
     */
    public static File saveFile(Bitmap bm, int size) throws Exception {
        String tempPath = Environment.getExternalStorageDirectory() + File.separator + "cache_temp.jpg";

        File myCaptureFile = new File(tempPath);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        //size 100表示不进行压缩，70表示压缩率为30%
        bm.compress(Bitmap.CompressFormat.JPEG, size, bos);
        FileInputStream fs = new FileInputStream(myCaptureFile);
        int len = fs.available() / 1024;
        if (null != bm && Build.VERSION.SDK_INT >= 12) {
            FuniLog.d("压缩后：" + "大小" + len + "K，" + "宽：" + bm.getWidth() + "，高：" + bm.getHeight());
        } else {
            FuniLog.d("压缩后：" + "大小" + len + "K，" + "宽：" + bm.getWidth() + "，高：" + bm.getHeight());
        }
        bos.flush();
        bos.close();
        return myCaptureFile;
    }
}
