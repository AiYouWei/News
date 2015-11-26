package com.news;

import android.content.Context;
import android.content.SharedPreferences;

import com.news.TYConstants.FontSize;

import org.androidx.frames.preferences.BaseSharedSettings;

/**
 * SharedPreferences的操作
 *
 * @author slioe shu
 */
public class TYSettings extends BaseSharedSettings {
    private final static String NEWS_NAME = "newsSettings";
    private SharedPreferences preferences;

    public TYSettings(Context context) {
        preferences = context.getSharedPreferences(NEWS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    protected SharedPreferences getPreferences() {
        return preferences;
    }

    public StringPreferences WEATHER = new StringPreferences("weather", "");

    public StringPreferences ALL_COLUMNS = new StringPreferences("all_columns", ""); // 所有栏目
    public StringPreferences CUSTOM_COLUMNS = new StringPreferences("custom_columns", ""); // 用户选择栏目
    public StringPreferences REMAINING_COLUMNS = new StringPreferences("remaining_columns", ""); // 未选择栏目
    public IntegerPreferences FONT_SIZE = new IntegerPreferences("fontSize", FontSize.NORMAL.ordinal());

    public StringPreferences USER_ID = new StringPreferences("user_id", ""); // 用户ID
    public StringPreferences USER_NICK = new StringPreferences("user_nick", ""); // 用户昵称
    public StringPreferences USER_HEAD = new StringPreferences("user_head", ""); // 用户头像
    public StringPreferences USER_SCORE = new StringPreferences("user_score", ""); // 用户积分
    public StringPreferences USER_SIGN = new StringPreferences("user_sign", ""); // 签到
    public StringPreferences NEWS_SEARCH = new StringPreferences("search", ""); // 搜索

    public StringPreferences DOWN_LIST = new StringPreferences("a", "");
}
