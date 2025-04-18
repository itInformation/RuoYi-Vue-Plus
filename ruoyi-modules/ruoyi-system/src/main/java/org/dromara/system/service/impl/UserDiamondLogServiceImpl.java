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
import org.dromara.system.domain.bo.UserDiamondLogBo;
import org.dromara.system.domain.vo.UserDiamondLogVo;
import org.dromara.system.domain.UserDiamondLog;
import org.dromara.system.mapper.UserDiamondLogMapper;
import org.dromara.system.service.IUserDiamondLogService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户钻石流水Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@RequiredArgsConstructor
@Service
public class UserDiamondLogServiceImpl implements IUserDiamondLogService {

    private final UserDiamondLogMapper baseMapper;

    /**
     * 查询用户钻石流水
     *
     * @param id 主键
     * @return 用户钻石流水
     */
    @Override
    public UserDiamondLogVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询用户钻石流水列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户钻石流水分页列表
     */
    @Override
    public TableDataInfo<UserDiamondLogVo> queryPageList(UserDiamondLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<UserDiamondLog> lqw = buildQueryWrapper(bo);
        Page<UserDiamondLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的用户钻石流水列表
     *
     * @param bo 查询条件
     * @return 用户钻石流水列表
     */
    @Override
    public List<UserDiamondLogVo> queryList(UserDiamondLogBo bo) {
        LambdaQueryWrapper<UserDiamondLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<UserDiamondLog> buildQueryWrapper(UserDiamondLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<UserDiamondLog> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(UserDiamondLog::getId);
        lqw.eq(bo.getUserId() != null, UserDiamondLog::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getOpType()), UserDiamondLog::getOpType, bo.getOpType());
        lqw.eq(bo.getAmount() != null, UserDiamondLog::getAmount, bo.getAmount());
        return lqw;
    }

    /**
     * 新增用户钻石流水
     *
     * @param bo 用户钻石流水
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(UserDiamondLogBo bo) {
        UserDiamondLog add = MapstructUtils.convert(bo, UserDiamondLog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户钻石流水
     *
     * @param bo 用户钻石流水
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(UserDiamondLogBo bo) {
        UserDiamondLog update = MapstructUtils.convert(bo, UserDiamondLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(UserDiamondLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户钻石流水信息
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
