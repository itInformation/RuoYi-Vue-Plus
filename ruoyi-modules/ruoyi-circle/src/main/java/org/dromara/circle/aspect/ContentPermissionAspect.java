package org.dromara.circle.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.dromara.circle.anno.ContentPermission;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.circle.service.ICircleContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**

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

        String contentId = Long.valueOf(contentPerm.getContentId());
        // 获取当前用户
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (loginUser == null) {
            throw new ServiceException("请先登录");
        }
        Long userId = LoginHelper.getUserId();
        if (!contentService.checkAccessPermission(contentId, userId)) {
            throw new ServiceException("无权限访问该内容");
        }
        return joinPoint.proceed();
    }
}
