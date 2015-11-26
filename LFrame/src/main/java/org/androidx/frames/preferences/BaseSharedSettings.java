package org.androidx.frames.preferences;

import android.content.SharedPreferences;

/**
 * SharedPreferences基类
 *
 * @author slioe shu
 */
public abstract class BaseSharedSettings {
//    public final static String DEFAULT_NAME = "xframeSrttings";

    protected abstract SharedPreferences getPreferences();

    /**
     * SharedPreferences操作抽象类
     *
     * @param <T> SharedPreferences操作的数据类型
     * @author slioe shu
     */
    abstract class BasePreferences<T> {
        private String key;
        private T defValue;

        public BasePreferences(String key, T defValue) {
            this.key = key;
            this.defValue = defValue;
        }

        /**
         * 获取当前KEY对应的默认value值
         *
         * @return KEY对应的默认value值
         */
        protected T getDefValue() {
            return defValue;
        }

        /**
         * 获取当前KEY对应的value值
         *
         * @return KEY对应的value值
         */
        protected abstract T getValue();

        /**
         * 设置当前KEY对应的value值
         *
         * @param value KEY对应的value值
         * @return 设置成功为true, 失败则为false
         */
        protected abstract boolean setValue(T value);

        /**
         * 获取当前KEY的值
         *
         * @return 当前对象的key
         */
        protected String getKey() {
            return key;
        }

        /**
         * 重置当前key对应的value值为默认值
         */
        public void resetValue() {
            setValue(defValue);
        }
    }

    /**
     * String类型的SharedPreferences操作
     *
     * @author slioe shu
     */
    public class StringPreferences extends BasePreferences<String> {

        public StringPreferences(String key, String defValue) {
            super(key, defValue);
        }

        @Override
        public String getValue() {
            return getPreferences().getString(getKey(), getDefValue());
        }

        @Override
        public boolean setValue(String value) {
            return getPreferences().edit().putString(getKey(), value).commit();
        }
    }

    /**
     * Integer类型的SharedPreferences操作
     *
     * @author slioe shu
     */
    public class IntegerPreferences extends BasePreferences<Integer> {

        public IntegerPreferences(String key, int defValue) {
            super(key, defValue);
        }

        @Override
        public Integer getValue() {
            return getPreferences().getInt(getKey(), getDefValue());
        }

        @Override
        public boolean setValue(Integer value) {
            return getPreferences().edit().putInt(getKey(), value).commit();
        }
    }

    /**
     * Long类型的SharedPreferences操作
     *
     * @author slioe shu
     */
    public class LongPreferences extends BasePreferences<Long> {

        public LongPreferences(String key, Long defValue) {
            super(key, defValue);
        }

        @Override
        public Long getValue() {
            return getPreferences().getLong(getKey(), getDefValue());
        }

        @Override
        public boolean setValue(Long value) {
            return getPreferences().edit().putLong(getKey(), value).commit();
        }
    }

    /**
     * Boolean类型的SharedPreferences操作
     *
     * @author slioe shu
     */
    public class BooleanPreferences extends BasePreferences<Boolean> {

        public BooleanPreferences(String key, Boolean defValue) {
            super(key, defValue);
        }

        @Override
        public Boolean getValue() {
            return getPreferences().getBoolean(getKey(), getDefValue());
        }

        @Override
        public boolean setValue(Boolean value) {
            return getPreferences().edit().putBoolean(getKey(), value).commit();
        }
    }

    /**
     * Float类型的SharedPreferences操作
     *
     * @author slioe shu
     */
    public class FloatPreferences extends BasePreferences<Float> {

        public FloatPreferences(String key, Float defValue) {
            super(key, defValue);
        }

        @Override
        public Float getValue() {
            return getPreferences().getFloat(getKey(), getDefValue());
        }

        @Override
        public boolean setValue(Float value) {
            return getPreferences().edit().putFloat(getKey(), value).commit();
        }
    }
}
