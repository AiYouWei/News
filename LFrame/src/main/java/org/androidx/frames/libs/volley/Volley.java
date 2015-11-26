package org.androidx.frames.libs.volley;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build;

import java.io.File;

/**
 * volley使用工具
 *
 * @author slioe shu
 */
public final class Volley {

    /**
     * 创建一个默认缓存大小和Http栈的请求队列
     *
     * @param context context
     * @return 开启的请求队列
     */
    public static RequestQueue newRequestQueue(Context context) {
        return newRequestQueue(context, null);
    }

    /**
     * 创建一个默认缓存大小的请求队列
     *
     * @param context context
     * @param stack   http栈
     * @return 开启的请求队列
     */
    public static RequestQueue newRequestQueue(Context context, HttpStack stack) {
        return newRequestQueue(context, stack, -1);
    }

    /**
     * 创建一个默认Http栈的请求队列
     *
     * @param context           context
     * @param maxDiskCacheBytes 最大缓存缓存大小，-1表示默认大小(5M)
     * @return 开启的请求队列
     */
    public static RequestQueue newRequestQueue(Context context, int maxDiskCacheBytes) {
        return newRequestQueue(context, null, maxDiskCacheBytes);
    }

    /**
     * 创建一个请求队列
     *
     * @param context           context
     * @param stack             http栈
     * @param maxDiskCacheBytes 最大缓存缓存大小，-1表示默认大小(5M)
     * @return 开启的请求队列
     */
    public static RequestQueue newRequestQueue(Context context, HttpStack stack, int maxDiskCacheBytes) {
        String packageName = context.getPackageName();
        File cacheDir = new File(context.getCacheDir(), packageName); // 缓存目录<APP目录>/cache/包名
        String userAgent = packageName + "/0.0"; // 默认的userAgent
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (NameNotFoundException e) {
        }
        if (stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack();
            } else { // 2.3之前，HttpClient比HttpUrlConnection更好
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }
        Network network = new BasicNetwork(stack);
        RequestQueue queue;
        if (maxDiskCacheBytes <= -1) {
            queue = new RequestQueue(new DiskBasedCache(cacheDir), network);
        } else {
            queue = new RequestQueue(new DiskBasedCache(cacheDir, maxDiskCacheBytes), network);
        }
        queue.start();
        return queue;
    }
}
