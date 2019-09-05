package cn.azsy.zstokhttp.generic;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by zsy on 2017/8/8.
 */

public class GenericUtils {

    public static <T> Object get(T t) {
        Class<?> aClass = t.getClass();
        try {
            Object o = aClass.newInstance();
            return o;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    Type mType;

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }





    public class RawDao<T> {
        protected Class<T> clazz;

        @SuppressWarnings("unchecked")
        public RawDao() {
            @SuppressWarnings("rawtypes")
            Class clazz = getClass();

            while (clazz != Object.class) {
                Type t = clazz.getGenericSuperclass();
                if (t instanceof ParameterizedType) {
                    Type[] args = ((ParameterizedType) t).getActualTypeArguments();
                    if (args[0] instanceof Class) {
                        this.clazz = (Class<T>) args[0];
                        break;
                    }
                }
                clazz = clazz.getSuperclass();
            }
        }
    }













}
