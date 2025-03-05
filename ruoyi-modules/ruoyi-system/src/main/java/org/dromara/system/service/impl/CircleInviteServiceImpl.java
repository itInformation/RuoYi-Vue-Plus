package org.dromara.system.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.CircleInviteBo;
import org.dromara.system.domain.vo.CircleInviteVo;
import org.dromara.system.domain.CircleInvite;
import org.dromara.system.mapper.CircleInviteMapper;
import org.dromara.system.service.ICircleInviteService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 邀请记录Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class CircleInviteServiceImpl implements ICircleInviteService {

    private final CircleInviteMapper baseMapper;

    /**
     * 查询邀请记录
     *
     * @param inviteId 主键
     * @return 邀请记录
     */
    @Override
    public CircleInviteVo queryById(Long inviteId){
        return baseMapper.selectVoById(inviteId);
    }

    /**
     * 分页查询邀请记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 邀请记录分页列表
     */
    @Override
    public TableDataInfo<CircleInviteVo> queryPageList(CircleInviteBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleInvite> lqw = buildQueryWrapper(bo);
        Page<CircleInviteVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的邀请记录列表
     *
     * @param bo 查询条件
     * @return 邀请记录列表
     */
    @Override
    public List<CircleInviteVo> queryList(CircleInviteBo bo) {
        LambdaQueryWrapper<CircleInvite> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CircleInvite> buildQueryWrapper(CircleInviteBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CircleInvite> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CircleInvite::getInviteId);
        lqw.eq(bo.getGroupId() != null, CircleInvite::getGroupId, bo.getGroupId());
        lqw.eq(bo.getInviterId() != null, CircleInvite::getInviterId, bo.getInviterId());
        lqw.eq(bo.getInviteeId() != null, CircleInvite::getInviteeId, bo.getInviteeId());
        lqw.eq(bo.getInviteStatus() != null, CircleInvite::getInviteStatus, bo.getInviteStatus());
        return lqw;
    }

    /**
     * 新增邀请记录
     *
     * @param bo 邀请记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CircleInviteBo bo) {
        CircleInvite add = MapstructUtils.convert(bo, CircleInvite.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setInviteId(add.getInviteId());
        }
        return flag;
    }

    /**
     * 修改邀请记录
     *
     * @param bo 邀请记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CircleInviteBo bo) {
        CircleInvite update = MapstructUtils.convert(bo, CircleInvite.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CircleInvite entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除邀请记录信息
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
}
