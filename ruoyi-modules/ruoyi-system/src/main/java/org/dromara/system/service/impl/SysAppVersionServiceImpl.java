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
import org.dromara.system.domain.bo.SysAppVersionBo;
import org.dromara.system.domain.vo.SysAppVersionVo;
import org.dromara.system.domain.SysAppVersion;
import org.dromara.system.mapper.SysAppVersionMapper;
import org.dromara.system.service.ISysAppVersionService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * App版本信息Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@RequiredArgsConstructor
@Service
public class SysAppVersionServiceImpl implements ISysAppVersionService {

    private final SysAppVersionMapper baseMapper;

    /**
     * 查询App版本信息
     *
     * @param id 主键
     * @return App版本信息
     */
    @Override
    public SysAppVersionVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询App版本信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return App版本信息分页列表
     */
    @Override
    public TableDataInfo<SysAppVersionVo> queryPageList(SysAppVersionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysAppVersion> lqw = buildQueryWrapper(bo);
        Page<SysAppVersionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的App版本信息列表
     *
     * @param bo 查询条件
     * @return App版本信息列表
     */
    @Override
    public List<SysAppVersionVo> queryList(SysAppVersionBo bo) {
        LambdaQueryWrapper<SysAppVersion> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysAppVersion> buildQueryWrapper(SysAppVersionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysAppVersion> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(SysAppVersion::getId);
        lqw.eq(StringUtils.isNotBlank(bo.getVersionNumber()), SysAppVersion::getVersionNumber, bo.getVersionNumber());
        lqw.eq(bo.getReleaseDate() != null, SysAppVersion::getReleaseDate, bo.getReleaseDate());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), SysAppVersion::getDescription, bo.getDescription());
        lqw.eq(StringUtils.isNotBlank(bo.getDownloadUrl()), SysAppVersion::getDownloadUrl, bo.getDownloadUrl());
        return lqw;
    }

    /**
     * 新增App版本信息
     *
     * @param bo App版本信息
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysAppVersionBo bo) {
        SysAppVersion add = MapstructUtils.convert(bo, SysAppVersion.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改App版本信息
     *
     * @param bo App版本信息
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(SysAppVersionBo bo) {
        SysAppVersion update = MapstructUtils.convert(bo, SysAppVersion.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysAppVersion entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除App版本信息信息
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
