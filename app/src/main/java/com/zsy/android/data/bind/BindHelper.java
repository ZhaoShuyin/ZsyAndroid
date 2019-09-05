package com.zsy.android.data.bind;

import android.app.Activity;
import android.content.Context;
import android.os.Binder;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/7/30 16:46
 */

public class BindHelper {

    private Object object;
    private Context mContext;
    private Map mKeyView;

    public void inject(Activity activity) {
        this.mContext = activity;
        initView(activity);
    }


    private <E> void initView(E e) {
        if (e != null) {
            this.object = e;
            mKeyView = new LinkedHashMap<>();
//            mBeanBinders = new ArrayList<>();
            Class<?> clazz = e.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Class<?> type = field.getType();//成员变量的类型
                Log.e("BindHelper", "type: ==" + type);
                field.setAccessible(true);//设置私有成员变量可访问
                try {
                    Object o = field.get(e); //获取成员变量实例


//                    if (Table.class.isAssignableFrom(type)) {
//                        Table table = (Table) field.get(e);
//                        if (table == null) {
//                            // 若控件没有赋值（findViewById或new）则提示初始化
//                            Logger.e(tag, field.getType().getSimpleName() + " of " + clazz.getSimpleName()
//                                    + " is null and must be initialized.");
//                        } else {
//                            // 如果存在注解则优先将注解设置为关键词
//                            String key = getKey(field);
//                            if (TextUtils.isEmpty(key)) {
//                                if (table instanceof EditLayout) {
//                                    EditLayout layout = (EditLayout) table;
//                                    key = layout.getTitle();
//                                }
//                            }
//                            if (TextUtils.isEmpty(key)) continue;
//                            if (mKeyView.containsKey(key)) {
//                                throw new IllegalStateException("The " + key + " of key has already belonged to a View.");
//                            } else {
//                                // 若关键词不为空则创建绑定对象
//                                Binder binder = new Binder();
//                                binder.setKey(key);
//                                binder.setTable(table);
//                                binder.setViewField(field);
//                                if (field.isAnnotationPresent(BindView.class)) {
//                                    BindView bindView = field.getAnnotation(BindView.class);
//                                    binder.setBindView(bindView);
//                                }
//                                mKeyView.put(key, binder);
//                            }
//                        }
//                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
