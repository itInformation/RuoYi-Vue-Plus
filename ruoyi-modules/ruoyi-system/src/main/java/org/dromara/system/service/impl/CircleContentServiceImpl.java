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
import org.dromara.system.domain.bo.CircleContentBo;
import org.dromara.system.domain.vo.CircleContentVo;
import org.dromara.system.domain.CircleContent;
import org.dromara.system.mapper.CircleContentMapper;
import org.dromara.system.service.ICircleContentService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 圈子内容Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class CircleContentServiceImpl implements ICircleContentService {

    private final CircleContentMapper baseMapper;

    /**
     * 查询圈子内容
     *
     * @param contentId 主键
     * @return 圈子内容
     */
    @Override
    public CircleContentVo queryById(Long contentId){
        return baseMapper.selectVoById(contentId);
    }

    /**
     * 分页查询圈子内容列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子内容分页列表
     */
    @Override
    public TableDataInfo<CircleContentVo> queryPageList(CircleContentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleContent> lqw = buildQueryWrapper(bo);
        Page<CircleContentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的圈子内容列表
     *
     * @param bo 查询条件
     * @return 圈子内容列表
     */
    @Override
    public List<CircleContentVo> queryList(CircleContentBo bo) {
        LambdaQueryWrapper<CircleContent> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CircleContent> buildQueryWrapper(CircleContentBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CircleContent> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CircleContent::getContentId);
        lqw.eq(bo.getGroupId() != null, CircleContent::getGroupId, bo.getGroupId());
        lqw.eq(bo.getUserId() != null, CircleContent::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), CircleContent::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getContentType()), CircleContent::getContentType, bo.getContentType());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), CircleContent::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getPermType()), CircleContent::getPermType, bo.getPermType());
        return lqw;
    }

    /**
     * 新增圈子内容
     *
     * @param bo 圈子内容
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CircleContentBo bo) {
        CircleContent add = MapstructUtils.convert(bo, CircleContent.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setContentId(add.getContentId());
        }
        return flag;
    }

    /**
     * 修改圈子内容
     *
     * @param bo 圈子内容
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CircleContentBo bo) {
        CircleContent update = MapstructUtils.convert(bo, CircleContent.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CircleContent entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除圈子内容信息
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
