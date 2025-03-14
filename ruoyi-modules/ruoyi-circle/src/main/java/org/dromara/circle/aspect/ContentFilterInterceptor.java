package org.dromara.circle.aspect;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:23 2025/3/5
 * modified by
 */
//@Intercepts({
//    @Signature(type= Executor.class, method = "query",
//        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
//    @Signature(type= Executor.class, method = "update", args = {MappedStatement.class, Object.class})
//})
public class ContentFilterInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object parameter = invocation.getArgs()[1];
//        if (parameter instanceof CircleContent) {
//            Long userId = SecurityUtils.getUserId();
//            // 自动过滤非管理员只能访问自己创建的内容
//            if (!SecurityUtils.isAdmin()) {
//                ((CircleContent) parameter).setUserId(userId);
//            }
//        }
        return invocation.proceed();
    }
}
