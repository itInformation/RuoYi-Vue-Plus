package org.dromara.system.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.anno.ContentPermission;
import org.dromara.system.service.ICircleContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
<<<<<<< HEAD
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午9:33 2025/3/5
 * modified by
 */


@Aspect
@Component
public class ContentPermissionAspect {
    @Autowired
    private ICircleContentService contentService;

    @Around("@annotation(contentPerm)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, ContentPermission contentPerm) throws Throwable {

        Long contentId = Long.valueOf(contentPerm.getContentId());
        Long userId = LoginHelper.getUserId();
        if (!contentService.checkAccessPermission(contentId, userId)) {
            throw new ServiceException("无权限访问该内容");
        }
        return joinPoint.proceed();
    }
}
