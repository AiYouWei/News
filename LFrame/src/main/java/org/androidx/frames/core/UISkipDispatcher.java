package org.androidx.frames.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import org.androidx.frames.utils.ResUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UI跳转dispatcher，实现UI之间的跳转和Tab的切换
 *
 * @author slioe shu
 */
public class UISkipDispatcher {
    private final static String SCHEME_CATEGORY_MOD = "mod";
    private final static String APP_URI_PREFIX = "app_uri_prefix";

    private enum Category {
        NAVI, PAGE
    }

    private static UISkipDispatcher dispatcher;
    private Context context;
    private static String URI_APP_NAME;
    private Map<String, String> mappings;
    private FragmentActivity homeActivity;
    private int contentId;
    private Fragment currFragment;

    static {
        URI_APP_NAME = ResUtil.getStringByName(APP_URI_PREFIX);
    }

    public static synchronized UISkipDispatcher init(Context context) {
        return dispatcher == null ? dispatcher = new UISkipDispatcher(context) : dispatcher;
    }

    public static UISkipDispatcher getInstance() {
        if (dispatcher == null) {
            throw new RuntimeException("UISkipDispatcher is not initialized by init()");
        }
        return dispatcher;
    }

    private UISkipDispatcher(Context context) {
        super();
        this.context = context;
        mappings = new HashMap<>();
    }

    /**
     * 添加UI跳转的URI键值对
     *
     * @param key   跳转的URI
     * @param value URI对应的Activity或Fragment
     */
    public void addRegistMappings(String key, String value) {
        mappings.put(key, value);
    }

    /**
     * UI之间的跳转
     *
     * @param key  跳转的URI
     * @param data 跳转时附加的数据
     */
    public void dispachetr(String key, Bundle data) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        data = data == null ? new Bundle() : data;
        Uri uri = Uri.parse(key);
        if (SCHEME_CATEGORY_MOD.equals(uri.getScheme())) {
            jumpByModProtocol(key, data, uri);
        }
    }

    /**
     * mod:// 协议跳转, 如mod://app.action.name#param1=1/param2=2
     *
     * @param key  跳转的URI
     * @param data 跳转时附加的数据
     * @param uri  跳转的URI
     */
    private void jumpByModProtocol(String key, Bundle data, Uri uri) {
        String authority = uri.getAuthority();
        if (!TextUtils.isEmpty(authority)) {
            String[] contents = authority.split("#");
            String[] authoritys = contents[0].split("\\.");
            String appName = authoritys[0], CategoryName = authoritys[1], className = authoritys[2];
            if (URI_APP_NAME.equals(appName)) {
                if (contents.length >= 2) {
                    String[] params = contents[1].split("/");
                    for (String param : params) {
                        String[] keyValue = param.split("/");
                        data.putString(keyValue[0], keyValue[1]);
                    }
                }
                if (Category.NAVI.toString().equalsIgnoreCase(CategoryName)) {
                    jumpByNavi(key, className, data);
                } else if (Category.PAGE.toString().equalsIgnoreCase(CategoryName)) {
                    jumpByPage(className, data);
                }
            } else {
                throw new IllegalArgumentException("app name is not match uri's app !!!");
            }
        } else {
            throw new IllegalArgumentException("uri \"" + key + "\"format error");
        }
    }

    /**
     * UI之间的跳转
     *
     * @param classKey 跳转的URI中的className部分
     * @param data     跳转时附加的数据
     */
    private void jumpByPage(String classKey, Bundle data) {
        String classValue = mappings.get(classKey);
        try {
            Intent intent = new Intent(context, Class.forName(classValue));
            intent.putExtras(data);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    /**
     * Tab间的切换
     *
     * @param key      跳转的URI
     * @param classKey 跳转的URI中的className部分
     * @param data     跳转时附加的数据
     */
    private void jumpByNavi(String key, String classKey, Bundle data) {
        Fragment fragment = homeActivity.getSupportFragmentManager().findFragmentByTag(key);
        FragmentTransaction transaction = homeActivity.getSupportFragmentManager().beginTransaction();
        if (fragment != null && fragment.isAdded()) {
            fragment.onResume();
        } else {
            String className = mappings.get(classKey);
            fragment = Fragment.instantiate(homeActivity, className, data);
            transaction.add(contentId, fragment, key);
        }
        if (currFragment != null) {
            transaction.hide(currFragment);
            currFragment.onPause();
        }
        currFragment = fragment;
        transaction.show(currFragment);
        transaction.commit();
    }

    public void setHomeTabs(FragmentActivity homeActivity, int contentId) {
        this.homeActivity = homeActivity;
        this.contentId = contentId;
    }

    public static View.OnClickListener getTabClickListener(final Context context, final String key) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<View> tabViews = TabsBarCreated.getInstance(context).getTabViews();
                for (int i = 0; i < tabViews.size(); i++) {
                    tabViews.get(i).setSelected(false);
                }
                v.setSelected(true);
                UISkipDispatcher.getInstance().dispachetr(key, null);
            }
        };
    }
}
