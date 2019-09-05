package com.zsy.android.layout.activity.router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:类型Annotation(注解)
 * </p>
 *
 * @author Zsy
 * @date 2019/7/30 10:31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RouterNode {
    String host() default "123";
}
