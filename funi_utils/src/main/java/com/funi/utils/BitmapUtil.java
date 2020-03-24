package com.funi.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * BitmapUtil
 *
 * @Description:图片压缩工具类
 * @Author: pengqiang.zou
 * @CreateDate: 2017-06-13 14:32
 */
public class BitmapUtil {

    private final static int MAX_SIZE = 5 * 1024;

    private BitmapUtil() {

    }

    /**
     * 是否需要压缩图片
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean isCompress(String path) {
        try {
            if (null != path && !path.equals("")) {
                File file = new File(path);
                FileInputStream fs = new FileInputStream(file);
                int len = fs.available() / 1024;
                if (len < MAX_SIZE) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //旋转图片
    public static Bitmap rotateImage(int angle, Bitmap bitmap) {
        //旋转图片
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    //把本地图片显示成圆角
//    public static Bitmap getRoundBitmap(Context context, String url, float roundPx) {
//        try {
//            Bitmap bitmap = compressBySize(url, Utils.getScreenWidth(context), Utils.getScreenHeight(context));
//            return getRoundedCornerBitmap(bitmap, roundPx);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    //图片流转Bitmap
    public static Bitmap getBitmapFromByte(byte[] temp) {
        if (temp != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * 加载本地图片(将本地路径转为bitmap)
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //获得圆角图片的方法
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    //压缩图片质量
    public static Bitmap compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        if (baos.toByteArray().length / 1024 >= 5 * 1024) {
            while (baos.toByteArray().length / 1024 > 5 * 1024) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;//每次都减少10
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap2 = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
            return bitmap2;
        } else {
            return bitmap;
        }
    }


    //压缩图片尺寸
    public static Bitmap compressBySize(String pathName) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);
        // 得到图片的宽度、高度；
        float imgWidth = opts.outWidth;
        float imgHeight = opts.outHeight;

        opts.inSampleSize = 3;//压缩比值越小压缩出的图片越大
        //设置好缩放比例后，加载图片进内容；
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(pathName, opts);
        return compressImage(bitmap);
    }

    /**
     * 图片按比例大小压缩方法
     *
     * @param srcPath （根据路径获取图片并压缩）
     * @return
     */
    public static Bitmap getImage(String srcPath) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 1280f;// 这里设置高度为800f
        float ww = 720f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    //存储进SD卡
    public static File saveFile(Bitmap bm, int size) throws Exception {
        String tempPath = Environment.getExternalStorageDirectory() + File.separator + "FuniSilver/upload";
        File files = new File(tempPath);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!files.exists()) {
            files.mkdir();
        }
        File myCaptureFile = new File(files + "/upload_fyt.jpg");

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        //size 100表示不进行压缩，70表示压缩率为30%
        bm.compress(Bitmap.CompressFormat.JPEG, size, bos);
        FileInputStream fs = new FileInputStream(myCaptureFile);
        int len = fs.available() / 1024;
        if (null != bm && Build.VERSION.SDK_INT >= 12) {
            //FuniLog.d("压缩后：" + "大小" + len + "K，" + "宽：" + bm.getWidth() + "，高：" + bm.getHeight());
        } else {
            //FuniLog.d("压缩后：" + "大小" + len + "K，" + "宽：" + bm.getWidth() + "，高：" + bm.getHeight());
        }
        bos.flush();
        bos.close();
        return myCaptureFile;
    }


    //存储进SD卡-针对敏感影像资料 app退出后该文件夹将被删除
    public static File saveFile2(Bitmap bm, int size, String name) throws Exception {
        String tempPath = Environment.getExternalStorageDirectory() + File.separator + "FuniSilver/image";
        File files = new File(tempPath);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!files.exists()) {
            files.mkdir();
        }
        File myCaptureFile = new File(files + "/" + name + "_fyt.jpg");

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        //size 100表示不进行压缩，70表示压缩率为30%
        bm.compress(Bitmap.CompressFormat.JPEG, size, bos);
        FileInputStream fs = new FileInputStream(myCaptureFile);
        int len = fs.available() / 1024;
        if (null != bm && Build.VERSION.SDK_INT >= 12) {
            //FuniLog.d("压缩后：" + "大小" + len + "K，" + "宽：" + bm.getWidth() + "，高：" + bm.getHeight());
        } else {
            // FuniLog.d("压缩后：" + "大小" + len + "K，" + "宽：" + bm.getWidth() + "，高：" + bm.getHeight());
        }
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

    //存放头像影像
    public static File saveFile3(Bitmap bm, int size, boolean isDown) throws Exception {
        File myCaptureFile;
        String tempPath = Environment.getExternalStorageDirectory() + File.separator + "FuniSilver/avatar";
        File files = new File(tempPath);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!files.exists()) {
            files.mkdir();
        }
        if (isDown) {
            myCaptureFile = new File(files + "/avatar.jpg");
        } else {
            myCaptureFile = new File(files + "/avatar_1.jpg");
        }

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        //size 100表示不进行压缩，70表示压缩率为30%
        bm.compress(Bitmap.CompressFormat.JPEG, size, bos);
        FileInputStream fs = new FileInputStream(myCaptureFile);
        int len = fs.available() / 1024;
        if (null != bm && Build.VERSION.SDK_INT >= 12) {
            // FuniLog.d("压缩后：" + "大小" + len + "K，" + "宽：" + bm.getWidth() + "，高：" + bm.getHeight());
        } else {
            //FuniLog.d("压缩后：" + "大小" + len + "K，" + "宽：" + bm.getWidth() + "，高：" + bm.getHeight());
        }
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

    //头像文件是否存在
    public static String getAvatarPath() {
        //获取SD卡路径
        String path = Environment.getExternalStorageDirectory() + File.separator + "FuniSilver/avatar/avatar.jpg";
        File file = new File(path);
        if (file.exists()) {//如果路径存在
            return path;
        }
        return "";
    }

    //替换头像
    public static void replaceAvatar() {
        //获取SD卡路径
        String path = Environment.getExternalStorageDirectory() + File.separator + "FuniSilver/avatar/avatar.jpg";
        String path1 = Environment.getExternalStorageDirectory() + File.separator + "FuniSilver/avatar/avatar_1.jpg";
        File file = new File(path);
        File file1 = new File(path1);
        if (file1.exists()) {//如果路径存在
            file1.renameTo(file);
        }
    }

    //删除頭像文件夹
    public static void deleteAvatarFiles() {
        //获取SD卡路径
        String path = Environment.getExternalStorageDirectory() + File.separator + "FuniSilver/avatar";

        File file = new File(path);
        if (file.exists()) {//如果路径存在

            if (file.isDirectory()) {//如果是文件夹
                File[] childFiles = file.listFiles();//获取文件夹下所有文件
                if (childFiles == null || childFiles.length == 0) {//如果为空文件夹
                    file.delete();//删除文件夹
                    return;
                }

                for (int i = 0; i < childFiles.length; i++) {//删除文件夹下所有文件
                    childFiles[i].delete();
                }
                file.delete();//删除文件夹
            }
        }
    }


    //删除文件夹
    public static void deleteAllFiles() {
        //获取SD卡路径
        String path = Environment.getExternalStorageDirectory() + File.separator + "FuniSilver/image";

        File file = new File(path);
        if (file.exists()) {//如果路径存在

            if (file.isDirectory()) {//如果是文件夹
                File[] childFiles = file.listFiles();//获取文件夹下所有文件
                if (childFiles == null || childFiles.length == 0) {//如果为空文件夹
                    file.delete();//删除文件夹
                    return;
                }

                for (int i = 0; i < childFiles.length; i++) {//删除文件夹下所有文件
                    childFiles[i].delete();
                }
                file.delete();//删除文件夹
            }
        }
    }


    /**
     * 查看图片
     *
     * @param name
     * @return
     */
    public static String makeResourcesUri(String name) {
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + "com.funi.silver/" + "mipmap/" + name);
        return uri.toString();
    }

}
