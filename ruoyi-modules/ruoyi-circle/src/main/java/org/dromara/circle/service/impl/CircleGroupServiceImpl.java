package org.dromara.circle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.common.core.constant.CircleReviewStatusConstants;
import org.dromara.common.core.constant.DataDeleteStatusConstants;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.enums.CircleRoleTypeEnum;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.circle.domain.CircleMember;
import org.dromara.circle.mapper.CircleMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.dromara.circle.domain.bo.CircleGroupBo;
import org.dromara.circle.domain.vo.CircleGroupVo;
import org.dromara.circle.domain.CircleGroup;
import org.dromara.circle.mapper.CircleGroupMapper;
import org.dromara.circle.service.ICircleGroupService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 圈子主体Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class CircleGroupServiceImpl implements ICircleGroupService {
    @Autowired
    private final CircleGroupMapper baseMapper;
    @Autowired
    private CircleMemberMapper circleMemberMapper;

    /**
     * 查询圈子主体
     *
     * @param groupId 主键
     * @return 圈子主体
     */
    @Override
    public CircleGroupVo queryById(Long groupId){
        CircleGroupVo circleGroupVo = baseMapper.selectVoById(groupId);
        if (circleGroupVo != null && DataDeleteStatusConstants.NOT_RECYCLE_BIN.equals(circleGroupVo.getRecycleBin())){
            return null;
        }
        return circleGroupVo;
    }
    @Override
    public List<CircleGroupVo> queryByUserId(Long userId){
        CircleGroupBo groupBo = new CircleGroupBo();
        groupBo.setOwnerId(userId);
        return queryList(groupBo);
    }
    @Override
    public CircleGroupVo queryByIdWithRecycleBin(Long groupId){
        CircleGroupVo circleGroupVo = baseMapper.selectVoById(groupId);
        if (circleGroupVo != null && DataDeleteStatusConstants.RECYCLE_BIN.equals(circleGroupVo.getRecycleBin())){
            return circleGroupVo;
        }
        return null;
    }

    /**
     * 分页查询圈子主体列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子主体分页列表
     */
    @Override
    public TableDataInfo<CircleGroupVo> queryPageList(CircleGroupBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleGroup> lqw = buildQueryWrapper(bo);
        lqw.eq(CircleGroup::getRecycleBin, DataDeleteStatusConstants.NOT_RECYCLE_BIN);
        Page<CircleGroupVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public TableDataInfo<CircleGroupVo> queryReviewPageList(CircleGroupBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleGroup> lqw = buildQueryWrapper(bo);
        lqw.eq(CircleGroup::getRecycleBin, DataDeleteStatusConstants.NOT_RECYCLE_BIN);
        lqw.eq(CircleGroup::getReview, CircleReviewStatusConstants.NOT_REVIEW);
        Page<CircleGroupVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }
    /**
     * 分页查询回收站圈子主体列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子主体分页列表
     */
    @Override
    public TableDataInfo<CircleGroupVo> queryPageListWithRecycleBin(CircleGroupBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleGroup> lqw = buildQueryWrapper(bo);
        lqw.eq(CircleGroup::getRecycleBin, DataDeleteStatusConstants.RECYCLE_BIN);
        Page<CircleGroupVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }
    /**
     * 查询符合条件的圈子主体列表
     *
     * @param bo 查询条件
     * @return 圈子主体列表
     */
    @Override
    public List<CircleGroupVo> queryList(CircleGroupBo bo) {
        LambdaQueryWrapper<CircleGroup> lqw = buildQueryWrapper(bo);
        lqw.eq(CircleGroup::getRecycleBin, DataDeleteStatusConstants.NOT_RECYCLE_BIN);
        return baseMapper.selectVoList(lqw);
    }

    /**
     * 查询回收站列表
     * @param bo
     * @return
     */
    @Override
    public List<CircleGroupVo> queryListWithRecycleBin(CircleGroupBo bo) {
        LambdaQueryWrapper<CircleGroup> lqw = buildQueryWrapper(bo);
        lqw.eq(CircleGroup::getRecycleBin, DataDeleteStatusConstants.RECYCLE_BIN);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CircleGroup> buildQueryWrapper(CircleGroupBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CircleGroup> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CircleGroup::getGroupId);
        lqw.like(StringUtils.isNotBlank(bo.getGroupName()), CircleGroup::getGroupName, bo.getGroupName());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), CircleGroup::getDescription, bo.getDescription());
        lqw.eq(bo.getOwnerId() != null, CircleGroup::getOwnerId, bo.getOwnerId());
        lqw.eq(StringUtils.isNotBlank(bo.getCoverImg()), CircleGroup::getCoverImg, bo.getCoverImg());
        lqw.eq(bo.getJoinMode() != null, CircleGroup::getJoinMode, bo.getJoinMode());

        return lqw;
    }

    /**
     * 新增圈子主体
     *
     * @param bo 圈子主体
     * @return 是否新增成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(CircleGroupBo bo) {
        CircleGroup circleGroup = MapstructUtils.convert(bo, CircleGroup.class);

        validEntityBeforeSave(circleGroup);
        boolean flag = baseMapper.insert(circleGroup) > 0;
        if (flag) {
            bo.setGroupId(circleGroup.getGroupId());
        }
        CircleMember circleMember = new CircleMember();
        circleMember.setUserId(LoginHelper.getUserId());
        circleMember.setGroupId(circleGroup.getGroupId());
        circleMember.setRoleType(CircleRoleTypeEnum.OWNER.getCode());
        circleMemberMapper.insert(circleMember);
        return flag;
    }

    /**
     * 修改圈子主体
     *
     * @param bo 圈子主体
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CircleGroupBo bo) {
        CircleGroup update = MapstructUtils.convert(bo, CircleGroup.class);
        validEntityBeforeSave(update);
        update.setReview(CircleReviewStatusConstants.NOT_REVIEW);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CircleGroup entity){
        //TODO 做一些数据校验,如唯一约束
        LoginHelper.getUserId();
        List<CircleGroupVo> circleGroupVoList = queryByUserId(LoginHelper.getUserId());
        if (CollectionUtils.isNotEmpty(circleGroupVoList) && circleGroupVoList.size() >= LoginHelper.getCircleNumKey()){
            throw new ServiceException("超过最大圈子创建量");
        }

    }

    /**
     * 校验并批量删除圈子主体信息
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
        List<CircleGroup> circleGroups = baseMapper.selectByIds(ids);
        if(CollectionUtils.isEmpty(circleGroups)){
            return false;
        }
        List<CircleGroup> noDeleteList = circleGroups.stream().filter(circleGroup -> DataDeleteStatusConstants.NOT_RECYCLE_BIN.equals(circleGroup.getRecycleBin())).toList();
        noDeleteList.forEach(circleGroup -> {
            circleGroup.setRecycleBin(DataDeleteStatusConstants.RECYCLE_BIN);
        });
        return baseMapper.updateBatchById(noDeleteList);
    }

    @Override
    public Boolean deleteRecycleBinByIds(Collection<Long> ids, Boolean isValid) {
        List<CircleGroup> circleGroups = baseMapper.selectByIds(ids);
        if(CollectionUtils.isEmpty(circleGroups)){
            return false;
        }
        //筛选出不在回收站的数据
        List<CircleGroup> noDeleteList = circleGroups.stream().filter(circleGroup -> DataDeleteStatusConstants.RECYCLE_BIN.equals(circleGroup.getDeleted())).toList();
        noDeleteList.forEach(circleGroup -> {
            circleGroup.setDeleted(DataDeleteStatusConstants.DELETED);
        });
        return baseMapper.updateBatchById(noDeleteList);
    }

    @Override
    public Boolean deleteWithValidById(Long id, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        CircleGroup circleGroup = baseMapper.selectById(id);
        if(ObjectUtils.isEmpty(circleGroup)){
            return false;
        }
        circleGroup.setRecycleBin(DataDeleteStatusConstants.RECYCLE_BIN);

        return baseMapper.updateById(circleGroup) > 0;
    }

    @Override
    public Boolean deleteRecycleBinById(Long id, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        CircleGroup circleGroup = baseMapper.selectById(id);
        if(ObjectUtils.isEmpty(circleGroup)){
            return false;
        }
        circleGroup.setDeleted(DataDeleteStatusConstants.DELETED);

        return baseMapper.updateById(circleGroup) > 0;
    }

}
