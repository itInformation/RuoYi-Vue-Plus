package org.dromara.system.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.system.domain.vo.ApplyPersonalVo;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.ApplyGuildBo;
import org.dromara.system.domain.vo.ApplyGuildVo;
import org.dromara.system.domain.ApplyGuild;
import org.dromara.system.mapper.ApplyGuildMapper;
import org.dromara.system.service.IApplyGuildService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 公会入驻申请Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@RequiredArgsConstructor
@Service
public class ApplyGuildServiceImpl implements IApplyGuildService {

    private final ApplyGuildMapper baseMapper;

    /**
     * 查询公会入驻申请
     *
     * @param applyId 主键
     * @return 公会入驻申请
     */
    @Override
    public ApplyGuildVo queryById(Long applyId){
        return baseMapper.selectVoById(applyId);
    }

     /**
     * 根据applyIds查询申请
     */
     @Override
    public List<ApplyGuildVo> queryByIds(Collection<Long> applyIds){
        return baseMapper.selectVoByIds(applyIds);
    }

    /**
     * 分页查询公会入驻申请列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 公会入驻申请分页列表
     */
    @Override
    public TableDataInfo<ApplyGuildVo> queryPageList(ApplyGuildBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ApplyGuild> lqw = buildQueryWrapper(bo);
        Page<ApplyGuildVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的公会入驻申请列表
     *
     * @param bo 查询条件
     * @return 公会入驻申请列表
     */
    @Override
    public List<ApplyGuildVo> queryList(ApplyGuildBo bo) {
        LambdaQueryWrapper<ApplyGuild> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ApplyGuild> buildQueryWrapper(ApplyGuildBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ApplyGuild> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(ApplyGuild::getApplyId);
        lqw.like(StringUtils.isNotBlank(bo.getGuildName()), ApplyGuild::getGuildName, bo.getGuildName());
        lqw.eq(StringUtils.isNotBlank(bo.getContactPerson()), ApplyGuild::getContactPerson, bo.getContactPerson());
        lqw.eq(StringUtils.isNotBlank(bo.getContactInfo()), ApplyGuild::getContactInfo, bo.getContactInfo());
        lqw.eq(bo.getMemberCount() != null, ApplyGuild::getMemberCount, bo.getMemberCount());
        lqw.eq(StringUtils.isNotBlank(bo.getPlatforms()), ApplyGuild::getPlatforms, bo.getPlatforms());
        lqw.eq(StringUtils.isNotBlank(bo.getImgUrls()), ApplyGuild::getImgUrls, bo.getImgUrls());
        return lqw;
    }

    /**
     * 新增公会入驻申请
     *
     * @param bo 公会入驻申请
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(ApplyGuildBo bo) {
        ApplyGuild add = MapstructUtils.convert(bo, ApplyGuild.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setApplyId(add.getApplyId());
        }
        return flag;
    }

    /**
     * 修改公会入驻申请
     *
     * @param bo 公会入驻申请
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(ApplyGuildBo bo) {
        ApplyGuild update = MapstructUtils.convert(bo, ApplyGuild.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ApplyGuild entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除公会入驻申请信息
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
