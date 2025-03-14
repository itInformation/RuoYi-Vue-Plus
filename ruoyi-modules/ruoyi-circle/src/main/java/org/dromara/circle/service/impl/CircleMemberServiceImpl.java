package org.dromara.circle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.dromara.common.core.enums.CircleRoleTypeEnum;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.dromara.circle.domain.bo.CircleMemberBo;
import org.dromara.circle.domain.vo.CircleMemberVo;
import org.dromara.circle.domain.CircleMember;
import org.dromara.circle.mapper.CircleMemberMapper;
import org.dromara.circle.service.ICircleMemberService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户-圈子关系Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class CircleMemberServiceImpl implements ICircleMemberService {

    private final CircleMemberMapper baseMapper;

    /**
     * 查询用户-圈子关系
     *
     * @param memberId 主键
     * @return 用户-圈子关系
     */
    @Override
    public CircleMemberVo queryById(Long memberId){
        return baseMapper.selectVoById(memberId);
    }

    /**
     * 分页查询用户-圈子关系列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户-圈子关系分页列表
     */
    @Override
    public TableDataInfo<CircleMemberVo> queryPageList(CircleMemberBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleMember> lqw = buildQueryWrapper(bo);
        Page<CircleMemberVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的用户-圈子关系列表
     *
     * @param bo 查询条件
     * @return 用户-圈子关系列表
     */
    @Override
    @Cacheable(value = "circleMembers", key = "#bo.groupId")
    public List<CircleMemberVo> queryList(CircleMemberBo bo) {
        LambdaQueryWrapper<CircleMember> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CircleMember> buildQueryWrapper(CircleMemberBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CircleMember> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CircleMember::getMemberId);
        lqw.eq(bo.getGroupId() != null, CircleMember::getGroupId, bo.getGroupId());
        lqw.eq(bo.getUserId() != null, CircleMember::getUserId, bo.getUserId());
        lqw.eq(bo.getRoleType() != null, CircleMember::getRoleType, bo.getRoleType());
        lqw.eq(bo.getMemberStatus() != null, CircleMember::getMemberStatus, bo.getMemberStatus());
        lqw.eq(bo.getJoinTime() != null, CircleMember::getJoinTime, bo.getJoinTime());
        lqw.eq(bo.getInviteUser() != null, CircleMember::getInviteUser, bo.getInviteUser());
        lqw.eq(bo.getLastActive() != null, CircleMember::getLastActive, bo.getLastActive());
        return lqw;
    }

    /**
     * 新增用户-圈子关系
     *
     * @param bo 用户-圈子关系
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CircleMemberBo bo) {
        CircleMember add = MapstructUtils.convert(bo, CircleMember.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMemberId(add.getMemberId());
        }
        return flag;
    }

    /**
     * 修改用户-圈子关系
     *
     * @param bo 用户-圈子关系
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CircleMemberBo bo) {
        CircleMember update = MapstructUtils.convert(bo, CircleMember.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CircleMember entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户-圈子关系信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
    @Override
    public Integer existInGroup(String groupId,Long userId){
        QueryWrapper<CircleMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", groupId);
        queryWrapper.eq("user_id", userId);
        List<CircleMember> circleMembers = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(circleMembers)){
            return 0;
        }
        return circleMembers.size();
    }
    @Override
    public CircleMember selectByGroupUser(String groupId,Long userId){
        QueryWrapper<CircleMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", groupId);
        queryWrapper.eq("user_id", userId);
        return baseMapper.selectVoOne(queryWrapper, CircleMember.class);
    }

    @Override
    public List<CircleMember> selectByUserID(Long userId) {
        QueryWrapper<CircleMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return baseMapper.selectList(queryWrapper);
    }

    public boolean checkManagePermission(String groupId, Long userId) {
        CircleMember member = selectByGroupUser(groupId, userId);
        if (member == null){
            return false;
        }
        // 角色权限规则：
        // 0-普通成员：仅基础操作（浏览、评论）
        // 1-管理员：可管理内容、处理申请
        // 2-拥有者：可转让圈子、删除圈子
        return member.getRoleType() >= CircleRoleTypeEnum.OWNER.getCode();
    }

}
