package com.news.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.io.IOException;

/**
 * @author slioe shu
 */
public class AppUpdateUtil {
    public static final String APK_MIME = "application/vnd.android.package-archive";

    public static boolean installPackage(Activity activity, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            setPermissions(777, filePath);// 给APK文件设置权限
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), APK_MIME);
            activity.startActivity(intent);
            return true;
        } else {
        }
        return false;
    }

    public static void setPermissions(int permission, String path) {
        try {
            String command = "chmod " + permission + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
        }
    }
}
