package com.raise.weapon_base;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 属性文件格式：
 * a=123
 * b={"a":"33"}
 * <p>
 * 如何使用：
 * PropUtil.loadPropFile(path);
 * PropUtil.hasKey("name");
 * PropUtil.get("age", "1");
 * Created by raise.yang on 18/10/16.
 */
public class PropUtil {

    private static final Map<String, String> propMap = new HashMap<>();

    /**
     * 加载属性文件
     *
     * @param path 属性文件绝对路径
     */
    public static void loadPropFile(@NonNull String path) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(path);
            Properties properties = new Properties();
            properties.load(is);
            Set<String> propertyNames = properties.stringPropertyNames();
            for (String propertyName : propertyNames) {
                propMap.put(propertyName, properties.getProperty(propertyName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeQuietly(is);
        }
    }

    /**
     * 属性文件中是否包含该key的value
     * 一定要有值且不为""(空字符)才会返回true
     */
    public static boolean hasKey(String key) {
        return !TextUtils.isEmpty(get(key));
    }

    /**
     * 获取属性文件value字段
     *
     * @param key 属性文件的key
     * @return value 默认为null
     */
    public static String get(String key) {
        return get(key, null);
    }

    /**
     * 获取属性文件value字段
     *
     * @param key          属性文件的key
     * @param defaultValue 默认值
     * @return value
     */
    public static String get(String key, String defaultValue) {
        if (!propMap.containsKey(key)) {
            return defaultValue;
        }
        return propMap.get(key);
    }

}
