//package org.dromara.common.core.intercepter;
//
//
//
///**
// * @description:
// * @author: zhangminghui
// * @email: zhangminghui@gycloud.com
// * @date: 2025/3/6 16:24
// */
////@Intercepts({
////    @Signature(type= Executor.class, method = "query",
////        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
////    @Signature(type= Executor.class, method = "update", args = {MappedStatement.class, Object.class})
////})
//public class ContentFilterInterceptor implements Interceptor {
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        Object parameter = invocation.getArgs()[1];
//        if (parameter instanceof CircleContent) {
//            Long userId = SecurityUtils.getUserId();
//            // 自动过滤非管理员只能访问自己创建的内容
//            if (!SecurityUtils.isAdmin()) {
//                ((CircleContent) parameter).setUserId(userId);
//            }
//        }
//        return invocation.proceed();
//    }
//}
