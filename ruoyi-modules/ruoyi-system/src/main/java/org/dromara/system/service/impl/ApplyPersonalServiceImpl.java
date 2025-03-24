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
import org.dromara.system.domain.bo.ApplyPersonalBo;
import org.dromara.system.domain.vo.ApplyPersonalVo;
import org.dromara.system.domain.ApplyPersonal;
import org.dromara.system.mapper.ApplyPersonalMapper;
import org.dromara.system.service.IApplyPersonalService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 个人入驻申请Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@RequiredArgsConstructor
@Service
public class ApplyPersonalServiceImpl implements IApplyPersonalService {

    private final ApplyPersonalMapper baseMapper;

    /**
     * 查询个人入驻申请
     *
     * @param applyId 主键
     * @return 个人入驻申请
     */
    @Override
    public ApplyPersonalVo queryById(Long applyId){
        return baseMapper.selectVoById(applyId);
    }

    /**
     * 分页查询个人入驻申请列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 个人入驻申请分页列表
     */
    @Override
    public TableDataInfo<ApplyPersonalVo> queryPageList(ApplyPersonalBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ApplyPersonal> lqw = buildQueryWrapper(bo);
        Page<ApplyPersonalVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的个人入驻申请列表
     *
     * @param bo 查询条件
     * @return 个人入驻申请列表
     */
    @Override
    public List<ApplyPersonalVo> queryList(ApplyPersonalBo bo) {
        LambdaQueryWrapper<ApplyPersonal> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ApplyPersonal> buildQueryWrapper(ApplyPersonalBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ApplyPersonal> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(ApplyPersonal::getApplyId);
        lqw.like(StringUtils.isNotBlank(bo.getNickname()), ApplyPersonal::getNickname, bo.getNickname());
        lqw.eq(StringUtils.isNotBlank(bo.getMainPlatforms()), ApplyPersonal::getMainPlatforms, bo.getMainPlatforms());
        lqw.eq(StringUtils.isNotBlank(bo.getContactInfo()), ApplyPersonal::getContactInfo, bo.getContactInfo());
        lqw.eq(StringUtils.isNotBlank(bo.getImgUrls()), ApplyPersonal::getImgUrls, bo.getImgUrls());
        return lqw;
    }

    /**
     * 新增个人入驻申请
     *
     * @param bo 个人入驻申请
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(ApplyPersonalBo bo) {
        ApplyPersonal add = MapstructUtils.convert(bo, ApplyPersonal.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setApplyId(add.getApplyId());
        }
        return flag;
    }

    /**
     * 修改个人入驻申请
     *
     * @param bo 个人入驻申请
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(ApplyPersonalBo bo) {
        ApplyPersonal update = MapstructUtils.convert(bo, ApplyPersonal.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ApplyPersonal entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除个人入驻申请信息
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
