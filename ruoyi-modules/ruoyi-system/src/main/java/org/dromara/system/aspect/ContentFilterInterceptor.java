package org.dromara.system.aspect;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.dromara.system.domain.CircleContent;

import java.util.concurrent.Executor;

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
        if (parameter instanceof CircleContent) {
//            Long userId = SecurityUtils.getUserId();
//            // 自动过滤非管理员只能访问自己创建的内容
//            if (!SecurityUtils.isAdmin()) {
//                ((CircleContent) parameter).setUserId(userId);
//            }
        }
        return invocation.proceed();
    }
}
