package com.aframe.appframe.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {


    /**
     * 获取屏幕的宽度
     */
    public static DisplayMetrics getWindowsDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    //检查是否有SD卡
    public static boolean isHasSDCard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static void mToast(Activity activity, String s) {
        Toast toast = Toast.makeText(activity, s, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 判断系统语言
     *
     * @param mContext
     * @return
     */
    public static boolean isChinese(Context mContext) {
        Locale locale = mContext.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

    /**
     * 隐藏系统键盘
     * <p/>
     * <br>
     * <b>警告</b> 必须是确定键盘显示时才能调用
     */
    public static void hideKeyBoard(Activity aty) {
        if (null != aty && aty.getCurrentFocus()!= null) {
            ((InputMethodManager) aty
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(
                            aty.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    /**
     * 设置字体大小，用px
     *
     * @param context
     * @param str     目标字符串
     * @param start   开始位置
     * @param end     结束位置
     * @param pxSize  sp大小  像素
     * @return
     */
    public static SpannableString getSizeSpanSpToPx(Context context, String str, String start, String end, int pxSize) {
        int startIndex = 0;
        int endIndex = 0;
        if (null == start || "".equals(start)) {
            startIndex = 0;
        } else {
            if (str.contains(start)) {
                startIndex = str.indexOf(start) + 1;
            }

        }
        if (null == end || "".equals(end)) {
            endIndex = str.length();
        } else {
            if (str.contains(end)) {
                endIndex = str.indexOf(end);
            }

        }
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new AbsoluteSizeSpan(pxSize), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }


    /**
     * 格式化文本
     *
     * @param s 格式的文本
     * @return
     */
    public static SpannableStringBuilder formatString2(String s, String indexStr) {
        int indx = 0;
        SpannableStringBuilder builder = new SpannableStringBuilder(s);
        ForegroundColorSpan buleSpan = new ForegroundColorSpan(Color.parseColor("#2ebeff"));
        if (s.contains(indexStr)) {
            indx = s.indexOf(indexStr);
        }
        builder.setSpan(buleSpan, (indx + 1), s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static SpannableStringBuilder formatString2(String s, String indexStr, String color) {
        int indx = 0;
        SpannableStringBuilder builder = new SpannableStringBuilder(s);
        ForegroundColorSpan buleSpan = new ForegroundColorSpan(Color.parseColor(color));
        if (s.contains(indexStr)) {
            indx = s.indexOf(indexStr);
        }
        builder.setSpan(buleSpan, (indx + 1), s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static SpannableStringBuilder formatString3(String s, String indexStr, String color) {
        int indx = 0;
        SpannableStringBuilder builder = new SpannableStringBuilder(s);
        ForegroundColorSpan buleSpan = new ForegroundColorSpan(Color.parseColor(color));
        if (s.contains(indexStr)) {
            indx = s.indexOf(indexStr);
        }
        builder.setSpan(buleSpan, indx, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * 注意  当startStr 为“”  默认从0开始   当endStr为 “” 默认从最尾部结束
     *
     * @param s
     * @param startStr
     * @param endStr
     * @param color
     * @return
     */
    public static SpannableStringBuilder formatStringWithColor(String s, String startStr, String endStr, String color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(s);
        ForegroundColorSpan buleSpan = new ForegroundColorSpan(Color.parseColor(color));
        int startIndex = 0;
        int endIndex = s.length();
        if (null != startStr && !"".equals(startStr)) {
            if (s.contains(startStr)) {
                startIndex = s.indexOf(startStr);
            }
        }
        if (null != endStr && !"".equals(endStr)) {
            if (s.contains(endStr)) {
                endIndex = s.indexOf(endStr);
            }
        }
        if (null == startStr || "".equals(startStr)) {
            builder.setSpan(buleSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return builder;
        }
        builder.setSpan(buleSpan, (startIndex + 1), endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static SpannableStringBuilder formatString(String s, String startStr, String endStr) {
        SpannableStringBuilder builder = new SpannableStringBuilder(s);
        ForegroundColorSpan buleSpan = new ForegroundColorSpan(Color.parseColor("#d00350"));
        int startIndex = 0;
        int endIndex = s.length();
        if (null != startStr && !"".equals(startStr)) {
            if (s.contains(startStr)) {
                startIndex = s.indexOf(startStr);
            }
        }
        if (null != endStr && !"".equals(endStr)) {
            if (s.contains(endStr)) {
                endIndex = s.indexOf(endStr);
            }

        }

        builder.setSpan(buleSpan, (startIndex + 1), endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

//    /**
//     * 设置字体颜色和背景
//     *
//     * @param context
//     * @param s
//     * @param startStr
//     * @param endStr
//     * @return
//     */
//    public static SpannableStringBuilder formatStringBackground(Context context, String s, String startStr, String endStr) {
//        SpannableStringBuilder builder = new SpannableStringBuilder(s);
//        ForegroundColorSpan buleSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.white));
//        BackgroundColorSpan bgSpan = new BackgroundColorSpan(ContextCompat.getColor(context, R.color.red_d00251));
//        int startIndex = 0;
//        int endIndex = s.length();
//        if (null != startStr && !"".equals(startStr)) {
//            if (s.contains(startStr)) {
//                startIndex = s.indexOf(startStr);
//            }
//
//        }
//        if (null != endStr && !"".equals(endStr)) {
//            if (s.contains(endStr)) {
//                endIndex = s.indexOf(endStr);
//            }
//
//        }
//
//        builder.setSpan(buleSpan, (startIndex), endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(bgSpan, (startIndex), endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return builder;
//    }

//    public static SpannableStringBuilder formatStringCirCle(Context context, String s, String startStr, String endStr) {
//        Drawable bg = context.getResources().getDrawable(R.drawable.bg_circle_tv);
//        SpannableStringBuilder builder = new SpannableStringBuilder(s);
//        ForegroundColorSpan buleSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.white));
//        BackgroundColorSpan bgSpan = new BackgroundColorSpan(ContextCompat.getColor(context, R.color.red_d00251));
//        int startIndex = 0;
//        int endIndex = s.length();
//        if (null != startStr && !"".equals(startStr)) {
//            if (s.contains(startStr)) {
//                startIndex = s.indexOf(startStr);
//            }
//
//        }
//        if (null != endStr && !"".equals(endStr)) {
//            if (s.contains(endStr)) {
//                endIndex = s.indexOf(endStr);
//            }
//
//        }
//
//        builder.setSpan(buleSpan, (startIndex), endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(new ImageSpan(bg) {
//            @Override
//            public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
//                             int bottom, Paint paint) {
//                paint.setTypeface(Typeface.create("normal", Typeface.NORMAL));
//                paint.setTextSize(30);
//                int len = Math.round(paint.measureText(text, start, end));
//                getDrawable().setBounds(0, 0, len, 60);
//                super.draw(canvas, text, start, end, x, top, y, bottom, paint);
//                paint.setTypeface(Typeface.create("normal", Typeface.NORMAL));
//                paint.setTextSize(30);
//                canvas.drawText(text.subSequence(start, end).toString(), x + 10, y, paint);
//            }
//        }, (startIndex), endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return builder;
//    }


    public static SpannableString formatStringUnderLine(String s, String startStr, String endStr) {
        SpannableString content = new SpannableString(s);
        int startIndex = 0;
        int endIndex = s.length();
        if (null != startStr && !"".equals(startStr)) {
            if (s.contains(startStr)) {
                startIndex = s.indexOf(startStr);
            }

        }
        if (null != endStr && !"".equals(endStr)) {
            if (s.contains(endStr)) {
                endIndex = s.indexOf(endStr);
            }

        }

        content.setSpan(new UnderlineSpan(), (startIndex + 1), endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return content;
    }


    /**
     * 手机号 身份证号密显
     *
     * @param s 根据传过来的参数的长度判断是否为手机或身份证
     * @return
     */
    public static String miXian(String s) {
        String str = s.trim().replace(" ", "");
        int len = str.length();
        if (11 == len) {
            str = str.substring(0, 3) + "****" + str.substring(7, len);
        } else if (18 == len) {
            str = str.substring(0, 6) + "**********" + str.substring(16, len);
        }
        return str;
    }

    /**
     * 邮箱密显
     *
     * @param s
     * @return
     */
//    public static String miXianEmail(String s) {
//        if (!IndentifyAuth.isEmail(s)) {
//            return "";
//        }
//        String str = s.trim().replace(" ", "");
//        int mak = str.indexOf("@");
//        int len = str.length();
//        str = str.substring(0, 2) + "****" + str.substring(mak - 2, len);
//        return str;
//    }


    /**
     * @param plainText 明文
     * @return 32位密文  (小写)
     */
    public static String enMD5(String plainText) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuilder buf = new StringBuilder("");
            for (byte aB : b) {
                i = aB;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }


    /**
     * 得到当前版本名或号
     *
     * @param isName 是否获取名, true 返回版本名如:4.0.2 , false 返回版本号如:6
     */
    public static String getVersion(Context context, boolean isName) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    PackageManager.PERMISSION_GRANTED);
            if (isName) {
                return info.versionName;
            } else {
                return info.versionCode + "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Description: 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * Description: 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 判断WIFI是否连接
     *
     * @return
     */
    public static boolean isWIFIConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            Log.d("NetUtil", "isWIFIConnection() " + networkInfo.isConnected());
            return networkInfo.isConnected();
        }

        return false;
    }

    /**
     * 判断是否有网络
     */
    public static boolean netWorkAvaiable(Context context) {
        // TODO Auto-generated method stub
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @return
     * @param,
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 如果下面还有文件
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取图片信息
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 图片旋转
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle=" + angle);
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 获取文件夹大小
     *
     * @param file 文件夹
     * @return 文件夹大小
     * @throws Exception 异常
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {

                // 如果下面还有文件
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size 格式化前的大小
     * @return 格式化后的大小
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 去除字符串空格、换行
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        // TODO Auto-generated method stub
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            str = m.replaceAll("");
            str = str.replace("%", "");
        }
        return str;
    }

    static ScanResult mScanResult;

    public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    /**
     * 根据图片路径转换Bitmap
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getBitmapByPath(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//	        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空
//
        newOpts.inJustDecodeBounds = false;
//	        int w = newOpts.outWidth;
//	        int h = newOpts.outHeight;
//	        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//	        float hh = 800f;//这里设置高度为800f
//	        float ww = 480f;//这里设置宽度为480f
//	        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//	        int be = 1;//be=1表示不缩放
//	        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
//	            be = (int) (newOpts.outWidth / ww);
//	        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
//	            be = (int) (newOpts.outHeight / hh);
//	        }
//	        if (be <= 0)
//	            be = 1;
//	        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inSampleSize = 4;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return qualityCompress(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 获取输入框或TextView内容
     *
     * @param tv
     * @return
     */
    public static String vToString(TextView tv) {
        if (null != tv) {
            return tv.getText().toString().trim();
        }
        return null;


    }

    public static String vToString(EditText edt) {
        if (null != edt) {
            return edt.getText().toString().trim();
        }
        return null;

    }

    public static Map<String, String> getDefaultParams(Context context) {
        // TODO Auto-generated method stub
        Map<String, String> map = new HashMap<String, String>();
        map.put("dvType", "2");//安卓2；苹果1
//	        map.put("dvInfo", android.os.Build.MODEL);
//	        map.put("appVersion", getVersionName(context));
//	        map.put("forceUpdateVersion", getForceUpdateCode(context));
        return map;

    }

    /**
     * * 获取版本号
     * * @return 当前应用的版本号
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取升级版本号
     *
     * @param context
     * @return
     */
    public static String getForceUpdateCode(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            int strForceCode = appInfo.metaData.getInt("FORCE_UPDATE_CODE");
            if (strForceCode > 0)
                return "" + strForceCode;
            else
                return "0";
//    		if (strForceCode.length() > 0)
//    			return  Integer.parseInt(strForceCode);
//    		else
//    			return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    /**
     * 第一：质量压缩方法：
     *
     * @param image
     * @return
     */
    public static Bitmap qualityCompress(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10  
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);
    }


    /**
     * 第二：图片按比例大小压缩方法（根据路径获取图片并压缩）
     * ：
     *
     * @param srcPath
     * @return
     */
    public static Bitmap compressByPath(String srcPath, int newWidth, int newHeight) {
        // 获得原图
        Bitmap beforeBitmap = BitmapFactory.decodeFile(srcPath);
        // 图片原有的宽度和高度
        float beforeWidth = beforeBitmap.getWidth();
        float beforeHeight = beforeBitmap.getHeight();

        // 计算宽高缩放率
        float scaleWidth = 0;
        float scaleHeight = 0;
        if (beforeWidth > beforeHeight) {
            scaleWidth = ((float) newWidth) / beforeWidth;
            scaleHeight = ((float) newHeight) / beforeHeight;
        } else {
            scaleWidth = ((float) newWidth) / beforeHeight;
            scaleHeight = ((float) newHeight) / beforeWidth;
        }

        // 矩阵对象
        Matrix matrix = new Matrix();
        // 缩放图片动作 缩放比例
        matrix.postScale(scaleWidth, scaleHeight);
        // 创建一个新的Bitmap 从原始图像剪切图像
        return Bitmap.createBitmap(beforeBitmap, 0, 0,
                (int) beforeWidth, (int) beforeHeight, matrix, true);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 获取原图片存储路径
     *
     * @return
     */
    public static String getPhotopath(String name) {
        // 照片全路径
        String fileName = "";
        // 文件夹路径
        File tmpDir = new File(Environment.getExternalStorageDirectory()
                + "/MTPolice");
        String imageName = name + ".jpg";
        File file = new File(tmpDir + "/Head");
        if (!file.exists()) {
            file.mkdirs();// 创建文件夹
        }
        fileName = tmpDir + imageName;
        return fileName;
    }

    /**
     * 获取apk下载存储路径
     *
     * @return
     * @throws IOException
     */
    public static String getApkPath(String name) throws IOException {
        // 照片全路径
        String fileName = "";
        // 文件夹路径
        File tmpDir = new File(Environment.getExternalStorageDirectory()
                + "/MTPolice");
        //如果当前文件夹不存在
//    if (!tmpDir.exists()) {
//        //创建文件夹
//        tmpDir.mkdir();
//    }
        String imageName = name + ".apk";
        File file = new File(tmpDir + "/ApkDownLoad");
        if (!file.exists()) {
            file.mkdir();//文件夹路径
        }
//    File apkFile = new File(file, imageName);
//    if (!apkFile.exists()) {
//        //创建文件夹
//    	apkFile.mkdir();
//    }
//    try {
//    	byte buffer[] = new byte[1024];
//    	FileInputStream fis = new FileInputStream(file);
//		FileOutputStream fos = new FileOutputStream(apkFile);
//		int len = 0;
//		while (len = ) {
//			type type = (type) en.nextElement();
//			
//		}
//		fos.write(buffer);
//	} catch (FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
        fileName = tmpDir + imageName;
        return fileName;
    }

    /**
     * 保存图片
     *
     * @param bm
     * @return
     */
    public static Uri saveBitmap(Bitmap bm, String name) {
        File tmpDir = new File(Environment.getExternalStorageDirectory()
                + "/MTpolice");
        //如果当前文件夹不存在
        if (!tmpDir.exists()) {
            //创建文件夹
            tmpDir.mkdir();
        }
        File file = new File(tmpDir + "/Head");
        if (!file.exists()) {
            file.mkdir();//文件夹路径
        }
        try {
            File img = new File(file, name + ".jpg");
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    /**
//     * 发送到短信验证码
//     */
//    static int statusCode = -2;
//    static String returnMsg;
//
//    public static int sendSmsCode(final Context context, Button btn, final String phone, final String type) {
//        if (!netWorkAvaiable(context)) {
//            return -99;
//        }
//        new TimerUtil(context, btn, context.getResources().getString(R.string.txt_resendcode)).runTimer();
//        AsyncTask<String, String, Integer> asyncTask = new AsyncTask<String, String, Integer>() {
//            @Override
//            protected Integer doInBackground(String... params) {
//                Map<String, String> paramsMap = new HashMap<String, String>();
//                paramsMap.put("mobile", phone);
//                paramsMap.put("type", type);
//                String resutl = HttpRequest.doPost(context, paramsMap, HttpAction.SmsCodeAction);
//                JSONObject json = null;
//                try {
//                    json = new JSONObject(resutl);
//                    statusCode = json.getInt("code");
//                    returnMsg = json.getString("msg");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return statusCode;
//            }
//
//            protected void onPostExecute(int s) {
//                super.onPostExecute(s);
//                if (statusCode == 0) {
//                    CustomToast.makeText(context, "发送成功");
//                } else {
//                    CustomToast.makeText(context, returnMsg);
//                }
//            }
//        };
//        asyncTask.execute();
//        return statusCode;
//    }


//    public static String getSignature(Context context) {
//        String pkgname = context.getPackageName();
//        StringBuilder builder = new StringBuilder();
//        String signatureStr = "";
//        boolean isEmpty = TextUtils.isEmpty(pkgname);
//        if (isEmpty) {
//            CustomToast.makeText(context, "应用程序的包名不能为空");
//        } else {
//            try {
//                /** 通过包管理器获得指定包名包含签名的包信息 **/
//                PackageManager manager = context.getPackageManager();
//                PackageInfo packageInfo = manager.getPackageInfo(pkgname, PackageManager.GET_SIGNATURES);
//                /******* 通过返回的包信息获得签名数组 *******/
////                String signatures = packageInfo.signatures;
//                /******* 循环遍历签名数组拼接应用签名 *******/
//                for (Signature signature : packageInfo.signatures) {
//                    builder.append(signature.toCharsString());
//                }
//                /************** 得到应用签名 **************/
//                signatureStr = builder.toString();
//                LogUtils.i("signature = " + signatureStr);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        return signatureStr;
//    }

    /**
     * 回收Imageview
     *
     * @param imageView
     */
    public static void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }


    /**
     * 用于获取状态栏的高度
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //按比例压缩图片
    public static Bitmap decodeImg(String pathStr) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathStr, options);
        int height = options.outHeight;
        int width = options.outWidth;
        options.inJustDecodeBounds = false;
        int inSampleSize = 1;
        int reqHeight = 800;
        int reqWidth = 480;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(pathStr, options);
        return bitmap;
    }


    //按比例压缩图片
    public static Bitmap decodeImgForStream(InputStream stream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream, null, options);
        int height = options.outHeight;
        int width = options.outWidth;
        options.inJustDecodeBounds = false;
        int inSampleSize = 1;
        int reqHeight = 800;
        int reqWidth = 480;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
        return bitmap;
    }

    //按比例压缩图片
    public static Bitmap decodeImgForByte(InputStream stream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream, null, options);
        int height = options.outHeight;
        int width = options.outWidth;
        options.inJustDecodeBounds = false;
        int inSampleSize = 1;
        int reqHeight = 800;
        int reqWidth = 480;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = null;
        try {
            byte[] bt = getBytes(stream);
            BitmapFactory.decodeByteArray(bt, 0, bt.length, options);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024]; // 用数据装
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            outstream.write(buffer, 0, len);
        }
        outstream.close();
        // 关闭流一定要记得。
        return outstream.toByteArray();
    }

    public static void isExist(File file) {
        Log.i("pwx", "文件存放目录: " + file.getParentFile());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }


    //存放在APPdata/data  下
    public static String getSubOfRootDir(Context context, String target) {
        String rootDir = "";
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            rootDir = getExternDir(rootDir) + "/" + target;
        } else {
            rootDir = context.getCacheDir().getPath() + "/" + target;
        }
        return rootDir;
    }

    public static String getExternDir(String dir) {
        String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        if (dir != null) {
            path += dir;
        }
        return path;
    }


    /**
     * 设置加载网络图片
     *
     * @param path
     * @param image
     */
//    public static void setNetImage(Context context, String path, ImageView image) {
//        Glide.with(context)
//                .load(path)
//                .placeholder(R.drawable.icon_take_photo)
//                .error(R.drawable.icon_take_photo)
//                .centerCrop()
//                .crossFade()
//                .into(image);
//    }

    /**
     * 根据手机分辨率画字体大小
     */
    public static int getFrontSize(Context context, int textSize) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        // screenWidth = screenWidth > screenHeight ? screenWidth :
        // screenHeight;
        int rate = (int) (textSize * (float) screenHeight / 1280);
        return rate;
    }

    /**
     * Date 转化为String
     */
    public static String formatDateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

//    /**
//     * 用字符串生成二维码
//     *
//     * @return
//     * @throws WriterException
//     */
//    public static Bitmap createQRCode(String str) throws WriterException {
//        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
//        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
//        int width = matrix.getWidth();
//        int height = matrix.getHeight();
//        // 二维矩阵转为一维像素数组,也就是一直横着排了
//        int[] pixels = new int[width * height];
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                if (matrix.get(x, y)) {
//                    pixels[y * width + x] = 0xff000000;
//                }
//            }
//        }
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        // 通过像素数组生成bitmap,具体参考api
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//        return bitmap;
//    }

//    /**
//     * 用字符串生成二维码（可以有中文）
//     */
//    public static Bitmap encodeToQR(String contentsToEncode) throws WriterException {
//        if (TextUtils.isEmpty(contentsToEncode)) {
//            return null;
//        }
//
//        BarcodeFormat format = BarcodeFormat.QR_CODE;
//        Map hints = new EnumMap(EncodeHintType.class);
//        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        BitMatrix result = new MultiFormatWriter().encode(contentsToEncode, format, 300, 300, hints);
//        int width = result.getWidth();
//        int height = result.getHeight();
//        int[] pixels = new int[width * height];
//        for (int y = 0; y < height; y++) {
//            int offset = y * width;
//            for (int x = 0; x < width; x++) {
//                pixels[offset + x] = result.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
//            }
//        }
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//
//        return bitmap;
//    }

    /**
     * 验证身份证号是否符合规则
     *
     * @param text 身份证号
     * @return
     */
    public static boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }
}
