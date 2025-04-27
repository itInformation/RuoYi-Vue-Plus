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
import org.dromara.system.domain.bo.MemberTypeBo;
import org.dromara.system.domain.vo.MemberTypeVo;
import org.dromara.system.domain.MemberType;
import org.dromara.system.mapper.MemberTypeMapper;
import org.dromara.system.service.IMemberTypeService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 会员类型;Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@RequiredArgsConstructor
@Service
public class MemberTypeServiceImpl implements IMemberTypeService {

    private final MemberTypeMapper baseMapper;

    /**
     * 查询会员类型;
     *
     * @param typeId 主键
     * @return 会员类型;
     */
    @Override
    public MemberTypeVo queryById(Long typeId){
        return baseMapper.selectVoById(typeId);
    }

    /**
     * 分页查询会员类型;列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员类型;分页列表
     */
    @Override
    public TableDataInfo<MemberTypeVo> queryPageList(MemberTypeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MemberType> lqw = buildQueryWrapper(bo);
        Page<MemberTypeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的会员类型;列表
     *
     * @param bo 查询条件
     * @return 会员类型;列表
     */
    @Override
    public List<MemberTypeVo> queryList(MemberTypeBo bo) {
        LambdaQueryWrapper<MemberType> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MemberType> buildQueryWrapper(MemberTypeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MemberType> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(MemberType::getTypeId);
        lqw.like(StringUtils.isNotBlank(bo.getTypeName()), MemberType::getTypeName, bo.getTypeName());
        lqw.eq(StringUtils.isNotBlank(bo.getDesc()), MemberType::getDesc, bo.getDesc());
        lqw.eq(bo.getRevision() != null, MemberType::getRevision, bo.getRevision());
        lqw.eq(bo.getCreatedBy() != null, MemberType::getCreatedBy, bo.getCreatedBy());
        lqw.eq(bo.getCreatedTime() != null, MemberType::getCreatedTime, bo.getCreatedTime());
        lqw.eq(bo.getUpdatedBy() != null, MemberType::getUpdatedBy, bo.getUpdatedBy());
        lqw.eq(bo.getUpdatedTime() != null, MemberType::getUpdatedTime, bo.getUpdatedTime());
        return lqw;
    }

    /**
     * 新增会员类型;
     *
     * @param bo 会员类型;
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MemberTypeBo bo) {
        MemberType add = MapstructUtils.convert(bo, MemberType.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setTypeId(add.getTypeId());
        }
        return flag;
    }

    /**
     * 修改会员类型;
     *
     * @param bo 会员类型;
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MemberTypeBo bo) {
        MemberType update = MapstructUtils.convert(bo, MemberType.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MemberType entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除会员类型;信息
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
