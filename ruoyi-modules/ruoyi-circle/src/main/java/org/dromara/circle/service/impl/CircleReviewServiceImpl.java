package org.dromara.circle.service.impl;

import lombok.RequiredArgsConstructor;
import org.dromara.circle.domain.bo.CircleReviewBo;
import org.dromara.circle.enums.CircleReviewTypeEnum;
import org.dromara.circle.service.ICircleContentService;
import org.dromara.circle.service.ICircleGroupService;
import org.dromara.circle.service.ICircleReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 圈子主体Service业务层处理
 * 圈子列表的查询等级
 * 1.查询所有非删除的圈子   queryPageList
 * 2. 查询所有不在回收站的圈子  queryListWithRecycleBin
 * 3. 查询状态可用的圈子   queryStatusList 作为app端查询的一个条件
 * 4.查询所有需要审核的圈子  queryReviewPageList
 * @author Lion Li
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class CircleReviewServiceImpl implements ICircleReviewService {
    private final ICircleContentService circleContentService;
    private final ICircleGroupService circleGroupService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean reviewCircle(CircleReviewBo bo) {
        if (CircleReviewTypeEnum.CIRCLE_GROUP.equals(bo.getType())) {
           return circleGroupService.reviewCircleGroup(bo);
        }else {
           return circleContentService.reviewCircleContent(bo);
        }

    }


}
