package org.dromara.circle.service.impl;

import lombok.RequiredArgsConstructor;
import org.dromara.circle.es.document.CircleDocument;
import org.dromara.circle.repository.CircleESRepository;
import org.dromara.circle.service.ICircleGroupESService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class CircleGroupESServiceImpl implements ICircleGroupESService {

    private final CircleESRepository circleESRepository;
    public CircleDocument getById(Long id) {
        return circleESRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("圈子不存在"));
    }
    // 关键词搜索（分页）
    @Override
    public Page<CircleDocument> searchByKeyword(String keyword, int pageNumber, int pageSize) {
        return circleESRepository.searchByKeyword(
            keyword,
            PageRequest.of(pageNumber, pageSize, Sort.by("createTime").descending())
        );
    }

    @Override
    public Page<CircleDocument> findAllWithCreatorPriority(String keyword, Long ownerId,int pageNumber, int pageSize) {
        return circleESRepository.findAllWithCreatorPriority(
            keyword,
            ownerId,
            PageRequest.of(pageNumber, pageSize)
        );
    }

    // 精确匹配名称
    @Override
    public List<CircleDocument> findCirclesByName(String name,int page, int size) {
        return circleESRepository.findByGroupNameExact(name,PageRequest.of(page, size));
    }

    // 获取所有圈子（分页）
    public Page<CircleDocument> listAllCircles(int page, int size) {
        return circleESRepository.findAllByCreateTimeDesc(
            PageRequest.of(page, size)
        );
    }



}
