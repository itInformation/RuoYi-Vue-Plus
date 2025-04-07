package org.dromara.circle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.circle.domain.CircleContent;
import org.dromara.circle.domain.CircleGroup;
import org.dromara.circle.domain.bo.CircleContentBo;
import org.dromara.circle.domain.bo.CircleContentTopBo;
import org.dromara.circle.domain.vo.CircleContentVo;
import org.dromara.circle.enums.CircleContentReviewEnum;
import org.dromara.circle.mapper.CircleContentMapper;
import org.dromara.circle.mapper.CircleGroupMapper;
import org.dromara.circle.service.ICircleContentPermService;
import org.dromara.circle.service.ICircleContentService;
import org.dromara.circle.service.ICircleMemberService;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.enums.CircleContentPermTypeEnum;
import org.dromara.common.core.enums.CircleGroupJoinModeEnum;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 圈子内容Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class CircleContentServiceImpl implements ICircleContentService {
    @Resource
    private CircleContentMapper baseMapper;

    @Resource
    private CircleGroupMapper circleGroupMapper;

    @Resource
    private ICircleContentPermService circleContentPermService;
    @Resource
    private ICircleMemberService circleMemberService;

    /**
     * 查询圈子内容
     *
     * @param contentId 主键
     * @return 圈子内容
     */
    @Override
    public CircleContentVo queryById(String contentId) {
        return baseMapper.selectVoById(contentId);
    }
    /**
     * 查询置顶圈子内容
     *
     * @param contentId 主键
     * @return 圈子内容
     */
    private CircleContentVo queryTopContent(Long userId,String groupId) {
        QueryWrapper<CircleContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", groupId);
        queryWrapper.eq("user_Id",userId);
        return baseMapper.selectVoOne(queryWrapper);
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

    @Override
    public TableDataInfo<CircleContentVo> queryPageFailList(CircleContentBo bo, PageQuery pageQuery) {
        return null;
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
        if (add == null){
            throw new ServiceException("圈子内容不能为空");
        }
        add.setReview(CircleContentReviewEnum.NO_REVIEW.getType());
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean topCircleContent(CircleContentTopBo bo) {
        CircleContentVo circleContentVo = queryById(bo.getContentId());
        if (circleContentVo == null){
            throw new ServiceException("圈子内容不存在 contentId:" + bo.getContentId());
        }
        if (!circleContentVo.getUserId().equals(bo.getUserId())) {
            throw new ServiceException("无权操作此动态");
        }
        List<CircleContent> updateList = new ArrayList<>();
        //1. clear 置顶
        CircleContentVo topContent = queryTopContent(bo.getUserId(), circleContentVo.getGroupId());

        if (topContent != null){
            CircleContent content = new CircleContent();
            content.setIsTop(false);
            content.setTopTime(null);
            content.setContentId(topContent.getContentId());
            updateList.add(content);
        }
        //2.设置置顶
        CircleContent content = new CircleContent();
        content.setIsTop(true);
        content.setTopTime(LocalDateTime.now());
        content.setContentId(bo.getContentId());
        updateList.add(content);
        return baseMapper.updateBatchById(updateList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean publishCircleContent(CircleContentTopBo bo) {
        CircleContentVo circleContentVo = queryById(bo.getContentId());
        if (circleContentVo == null){
            throw new ServiceException("圈子内容不存在 contentId:" + bo.getContentId());
        }
        if (!circleContentVo.getUserId().equals(bo.getUserId())) {
            throw new ServiceException("无权操作此动态");
        }
        //2.发布
        CircleContent content = new CircleContent();
        content.setReview(CircleContentReviewEnum.NO_REVIEW.getType());
        content.setContentId(bo.getContentId());
        return baseMapper.updateById(content) > 0 ;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CircleContent entity) {
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
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }


    @Override
    @Cacheable(value = "contentPerm", key = "#contentId+':'+#userId")
    public Boolean checkAccessPermission(String contentId, Long userId) {
        CircleContent content = baseMapper.selectById(contentId);

        if (ObjectUtils.isEmpty(content)) {
            log.info("checkAccessPermission content isn't exist,contentId:{}", contentId);
            return false;
        }
        // 2.校验圈子基础权限
        if (!checkGroupAccess(content.getGroupId(), userId)) {
            return false;
        }
        // 免费内容直接放行
        if (CircleContentPermTypeEnum.FREE.getCode().equals(content.getPermType())) {
            log.info("checkAccessPermission content is free,contentId:{}", contentId);
            return true;
        }

        // 会员内容校验
        if (CircleContentPermTypeEnum.MEMBER.getCode().equals(content.getPermType())) {
            log.info("checkAccessPermission content is member allow to scan,contentId:{}", contentId);
            LoginUser loginUser = LoginHelper.getLoginUser();
            //todo 会员如何观看内容校验，待实现
            return false;

        }

        // 指定用户校验
        if (CircleContentPermTypeEnum.REFER.getCode().equals(content.getPermType())) {
            log.info("checkAccessPermission content is REFER allow to scan,contentId:{}", contentId);
            return circleContentPermService.checkContentPermission(contentId, userId).size() > 0;
        }

        return false;
    }

    private boolean checkGroupAccess(String groupId, Long userId) {
        // 检查用户是否在圈子成员中
        Integer count = circleMemberService.existInGroup(groupId, userId);
        if (count > 0) {
            return true;
        }

        // 检查圈子是否允许游客访问（根据join_mode配置）
        CircleGroup group = circleGroupMapper.selectById(groupId);
        if (ObjectUtils.isEmpty(group)) {
            throw new ServiceException("圈子不存在,groupId: " + groupId);
        }
        return CircleGroupJoinModeEnum.FREE.getCode().equals(group.getJoinMode());
    }




}
