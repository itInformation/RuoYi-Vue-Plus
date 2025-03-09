package org.dromara.system.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午9:35 2025/3/5
 * modified by
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentPermission {
    String value() default "";
}
