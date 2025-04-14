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
import org.dromara.system.domain.bo.SysVipLevelBo;
import org.dromara.system.domain.vo.SysVipLevelVo;
import org.dromara.system.domain.SysVipLevel;
import org.dromara.system.mapper.SysVipLevelMapper;
import org.dromara.system.service.ISysVipLevelService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 会员等级Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@RequiredArgsConstructor
@Service
public class SysVipLevelServiceImpl implements ISysVipLevelService {

    private final SysVipLevelMapper baseMapper;

    /**
     * 查询会员等级
     *
     * @param levelId 主键
     * @return 会员等级
     */
    @Override
    public SysVipLevelVo queryById(Long levelId){
        return baseMapper.selectVoById(levelId);
    }

    /**
     * 分页查询会员等级列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员等级分页列表
     */
    @Override
    public TableDataInfo<SysVipLevelVo> queryPageList(SysVipLevelBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysVipLevel> lqw = buildQueryWrapper(bo);
        Page<SysVipLevelVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的会员等级列表
     *
     * @param bo 查询条件
     * @return 会员等级列表
     */
    @Override
    public List<SysVipLevelVo> queryList(SysVipLevelBo bo) {
        LambdaQueryWrapper<SysVipLevel> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysVipLevel> buildQueryWrapper(SysVipLevelBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysVipLevel> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(SysVipLevel::getLevelId);
        lqw.like(StringUtils.isNotBlank(bo.getLevelName()), SysVipLevel::getLevelName, bo.getLevelName());
        lqw.eq(bo.getMinExp() != null, SysVipLevel::getMinExp, bo.getMinExp());
        lqw.eq(bo.getMaxExp() != null, SysVipLevel::getMaxExp, bo.getMaxExp());
        lqw.eq(StringUtils.isNotBlank(bo.getIcon()), SysVipLevel::getIcon, bo.getIcon());
        lqw.eq(StringUtils.isNotBlank(bo.getColorCode()), SysVipLevel::getColorCode, bo.getColorCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPrivileges()), SysVipLevel::getPrivileges, bo.getPrivileges());
        return lqw;
    }

    /**
     * 新增会员等级
     *
     * @param bo 会员等级
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysVipLevelBo bo) {
        SysVipLevel add = MapstructUtils.convert(bo, SysVipLevel.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setLevelId(add.getLevelId());
        }
        return flag;
    }

    /**
     * 修改会员等级
     *
     * @param bo 会员等级
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(SysVipLevelBo bo) {
        SysVipLevel update = MapstructUtils.convert(bo, SysVipLevel.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysVipLevel entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除会员等级信息
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
