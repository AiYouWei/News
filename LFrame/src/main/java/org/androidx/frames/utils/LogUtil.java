package org.androidx.frames.utils;

import android.util.Log;

/**
 * Log输出工具
 *
 * @author slioe shu
 */
public final class LogUtil {
    private final static String TAG = "[DEBUG]";
    private final static boolean IS_LOG = true;

    /**
     * 输出Log信息, 优先级别VERBOSE
     *
     * @param debug 是否输出当前Log
     * @param tag   Log信息的Tag
     * @param msg   Log信息的Msg
     */
    public static void v(boolean debug, String tag, String msg) {
        print(debug, tag, msg, null, Log.VERBOSE);
    }

    /**
     * 输出Log信息, 优先级别VERBOSE
     *
     * @param debug 是否输出当前Log
     * @param tag   Log信息的Tag
     * @param msg   Log信息的Msg
     * @param tr    Log信息的异常信息
     */
    public static void v(boolean debug, String tag, String msg, Throwable tr) {
        print(debug, tag, msg, tr, Log.VERBOSE);
    }

    /**
     * 输出Log信息, 优先级别DEBUG
     *
     * @param debug 是否输出当前Log
     * @param tag   Log信息的Tag
     * @param msg   Log信息的Msg
     */
    public static void d(boolean debug, String tag, String msg) {
        print(debug, tag, msg, null, Log.DEBUG);
    }

    /**
     * 输出Log信息, 优先级别DEBUG
     *
     * @param debug 是否输出当前Log
     * @param tag   Log信息的Tag
     * @param msg   Log信息的Msg
     * @param tr    Log信息的异常信息
     */
    public static void d(boolean debug, String tag, String msg, Throwable tr) {
        print(debug, tag, msg, tr, Log.DEBUG);
    }

    /**
     * 输出Log信息, 优先级别INFO
     *
     * @param debug 是否输出当前Log
     * @param tag   Log信息的Tag
     * @param msg   Log信息的Msg
     */
    public static void i(boolean debug, String tag, String msg) {
        print(debug, tag, msg, null, Log.INFO);
    }

    /**
     * 输出Log信息, 优先级别INFO
     *
     * @param debug 是否输出当前Log
     * @param tag   Log信息的Tag
     * @param msg   Log信息的Msg
     * @param tr    Log信息的异常信息
     */
    public static void i(boolean debug, String tag, String msg, Throwable tr) {
        print(debug, tag, msg, tr, Log.INFO);
    }

    /**
     * 输出Log信息, 优先级别WARN
     *
     * @param debug 是否输出当前Log
     * @param tag   Log信息的Tag
     * @param msg   Log信息的Msg
     */
    public static void w(boolean debug, String tag, String msg) {
        print(debug, tag, msg, null, Log.WARN);
    }

    /**
     * 输出Log信息, 优先级别WARN
     *
     * @param debug 是否输出当前Log
     * @param tag   Log信息的Tag
     * @param msg   Log信息的Msg
     * @param tr    Log信息的异常信息
     */
    public static void w(boolean debug, String tag, String msg, Throwable tr) {
        print(debug, tag, msg, tr, Log.WARN);
    }

    /**
     * 输出Log信息, 优先级别ERROR
     *
     * @param debug 是否输出当前Log
     * @param tag   Log信息的Tag
     * @param msg   Log信息的Msg
     */
    public static void e(boolean debug, String tag, String msg) {
        print(debug, tag, msg, null, Log.ERROR);
    }

    /**
     * 输出Log信息, 优先级别ERROR
     *
     * @param debug 是否输出当前Log
     * @param tag   Log信息的Tag
     * @param msg   Log信息的Msg
     * @param tr    Log信息的异常信息
     */
    public static void e(boolean debug, String tag, String msg, Throwable tr) {
        print(debug, tag, msg, tr, Log.ERROR);
    }

    /**
     * 根据优先级别输出Log信息
     *
     * @param debug    是否输出当前Log
     * @param tag      Log信息的Tag
     * @param msg      Log信息的Msg
     * @param tr       Log信息的异常信息
     * @param priority Log信息的优先级别
     */
    private static void print(boolean debug, String tag, String msg, Throwable tr, int priority) {
        if (IS_LOG && debug) {
            switch (priority) {
                case Log.VERBOSE:
                    Log.v(tag, TAG + msg, tr);
                    break;
                case Log.DEBUG:
                    Log.d(tag, TAG + msg, tr);
                    break;
                case Log.INFO:
                    Log.i(tag, TAG + msg, tr);
                    break;
                case Log.WARN:
                    Log.w(tag, TAG + msg, tr);
                    break;
                case Log.ERROR:
                    Log.e(tag, TAG + msg, tr);
                    break;
                default:
                    break;
            }
        }
    }
}
