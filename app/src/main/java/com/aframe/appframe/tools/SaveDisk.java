package com.aframe.appframe.tools;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.aframe.appframe.tools.listener.IDialogProgressListener;
import com.aframe.appframe.ui.applications.Apps;
import com.google.common.net.MediaType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import okhttp3.ResponseBody;

/**
 * Created by pwx on 2017/6/1.
 */

public class SaveDisk {
    public static boolean writeResponseBodyToDisk(Context context, ResponseBody body,IDialogProgressListener progressListener) {
        try {
            // todo change the file location/name according to your needs
            checkNetType(body);
            File futureStudioIconFile = new File(context.getExternalFilesDir(null) + File.separator + "Future Studio Icon.jpg");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
//                    BigDecimal bg = new BigDecimal(fileSizeDownloaded*1.0 / fileSize);
//                    float p = bg.setScale(2,BigDecimal.ROUND_DOWN).floatValue();
                    Log.d("pwx", "file download: " + fileSizeDownloaded + " of " + fileSize + "----"+(int)(fileSizeDownloaded*1.0 / fileSize *100));
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


    public static void checkNetType(ResponseBody body){
        String types = body.contentType().toString();
        switch (types){
            case "image/jpeg":
                break;
            case "image/png":
                break;
            case "image/gif":
                break;
            case "image/tiff":
                break;
            case "video/avi":
                break;
            case "video/mpg":
                break;
            case "audio/mp3":
                break;
            case "video/wav":
                break;
            case "video/aiff":
                break;
            case "video/mid":
                break;
            case "video/mpeg4":
                break;
            case "video/x-ms-wmv":
                break;
            case "application/vnd.android.package-archive":
                break;
            case "application/vnd.iphone":
                break;
            case "text/xml":
                break;
            case "text/html":
                break;
            case "text/css":
                break;

        }
        Log.i("pwx","下载类型是： "+body.contentType().toString());
    }
}
