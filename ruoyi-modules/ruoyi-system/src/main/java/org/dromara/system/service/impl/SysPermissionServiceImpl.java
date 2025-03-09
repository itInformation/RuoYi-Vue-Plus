package org.dromara.system.service.impl;

import org.dromara.common.core.constant.TenantConstants;
import org.dromara.common.core.enums.CircleRoleTypeEnum;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.domain.CircleMember;
import org.dromara.system.service.ICircleMemberService;
import org.dromara.system.service.ISysMenuService;
import org.dromara.system.service.ISysPermissionService;
import org.dromara.system.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户权限处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

    private final ISysRoleService roleService;
    private final ISysMenuService menuService;
    @Autowired
    private ICircleMemberService circleMemberService;

    /**
     * 获取角色数据权限
     *
     * @param userId  用户id
     * @return 角色权限信息
     */
    @Override
    public Set<String> getRolePermission(Long userId) {
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if (LoginHelper.isSuperAdmin(userId)) {
            roles.add(TenantConstants.SUPER_ADMIN_ROLE_KEY);
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(userId));
        }

        // 2. 获取圈子成员角色权限（动态计算）
        List<CircleMember> relations = circleMemberService.selectByUserID(userId);
        relations.forEach(relation -> {
            if (CircleRoleTypeEnum.OWNER.getCode().equals( relation.getRoleType())) {
                roles.add("circle:admin:" + relation.getGroupId());
            }
        });
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param userId  用户id
     * @return 菜单权限信息
     */
    @Override
    public Set<String> getMenuPermission(Long userId) {
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (LoginHelper.isSuperAdmin(userId)) {
            perms.add("*:*:*");
        } else {
            perms.addAll(menuService.selectMenuPermsByUserId(userId));
        }
        return perms;
    }
}
