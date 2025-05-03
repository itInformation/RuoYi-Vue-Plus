package org.dromara.circle.service;

import org.dromara.circle.es.document.CircleDocument;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 圈子主体Service接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
public interface ICircleGroupESService {
    /**
     * 关键词搜索圈子
     */
    Page<CircleDocument> searchByKeyword(String keyword, int page, int size);
    Page<CircleDocument> findAllWithCreatorPriority(String keyword, Long ownerId,int page, int size);

    List<CircleDocument> findCirclesByName(String name, int page, int size);

    Page<CircleDocument> listAllCircles(int page, int size);

    CircleDocument getById(Long id);

}
