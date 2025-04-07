package org.dromara.circle.service;

import org.dromara.circle.domain.bo.CircleReviewBo;

/**
 * 圈子主体Service接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
public interface ICircleReviewService {

    Boolean reviewCircle(CircleReviewBo bo);
}
