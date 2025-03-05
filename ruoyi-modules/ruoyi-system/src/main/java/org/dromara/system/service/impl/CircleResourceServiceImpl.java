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
import org.dromara.system.domain.bo.CircleResourceBo;
import org.dromara.system.domain.vo.CircleResourceVo;
import org.dromara.system.domain.CircleResource;
import org.dromara.system.mapper.CircleResourceMapper;
import org.dromara.system.service.ICircleResourceService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 资源文件Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class CircleResourceServiceImpl implements ICircleResourceService {

    private final CircleResourceMapper baseMapper;

    /**
     * 查询资源文件
     *
     * @param resourceId 主键
     * @return 资源文件
     */
    @Override
    public CircleResourceVo queryById(String resourceId){
        return baseMapper.selectVoById(resourceId);
    }

    /**
     * 分页查询资源文件列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 资源文件分页列表
     */
    @Override
    public TableDataInfo<CircleResourceVo> queryPageList(CircleResourceBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleResource> lqw = buildQueryWrapper(bo);
        Page<CircleResourceVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的资源文件列表
     *
     * @param bo 查询条件
     * @return 资源文件列表
     */
    @Override
    public List<CircleResourceVo> queryList(CircleResourceBo bo) {
        LambdaQueryWrapper<CircleResource> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CircleResource> buildQueryWrapper(CircleResourceBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CircleResource> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CircleResource::getResourceId);
        lqw.eq(bo.getContentId() != null, CircleResource::getContentId, bo.getContentId());
        lqw.eq(StringUtils.isNotBlank(bo.getFileType()), CircleResource::getFileType, bo.getFileType());
        lqw.eq(StringUtils.isNotBlank(bo.getFilePath()), CircleResource::getFilePath, bo.getFilePath());
        return lqw;
    }

    /**
     * 新增资源文件
     *
     * @param bo 资源文件
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CircleResourceBo bo) {
        CircleResource add = MapstructUtils.convert(bo, CircleResource.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setResourceId(add.getResourceId());
        }
        return flag;
    }

    /**
     * 修改资源文件
     *
     * @param bo 资源文件
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CircleResourceBo bo) {
        CircleResource update = MapstructUtils.convert(bo, CircleResource.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CircleResource entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除资源文件信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
