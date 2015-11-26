package org.androidx.frames.utils;

import android.os.Bundle;

/**
 * Bundle创建
 *
 * @author slioe shu
 */
public final class BundleUtil {

    /**
     * 创建Bundle对象，自动识别类型
     *
     * @param keyValues key-value键值对
     * @return Bundle对象
     */
    public static Bundle createBundle(Object... keyValues) {
        Bundle bundle = new Bundle();
        if (keyValues.length % 2 != 0) {
            throw new IllegalArgumentException("keyValues的数量必须是偶数");
        }
        for (int i = 0; i < keyValues.length; i += 2) {
            String key = String.valueOf(keyValues[i]);
            Object value = keyValues[i + 1];
            if (value instanceof String) {
                bundle.putString(key, String.valueOf(value));
            } else if (value instanceof Integer) {
                bundle.putInt(key, (int) value);
//            }else if (value instanceof  enum){
//                bundle.putSerializable(key, (Serializable)value);
            }
        }
        return bundle;
    }
}
