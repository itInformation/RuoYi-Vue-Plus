package org.dromara.circle.repository;
import org.dromara.circle.es.document.CircleDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午9:38 2025/5/2
 * modified by
 */
public interface CircleESRepository extends ElasticsearchRepository<CircleDocument, Long> {
    // 1. 精确匹配 ID
    Optional<CircleDocument> findById(Long id);

    // 2. 关键词搜索（匹配 group_name 和 description）
//    @Query("""
//        {
//          "bool": {
//            "should": [
//              { "match": { "group_name": "?0" } },
//              { "match": { "description": "?0" } }
//            ]
//          }
//        }
//        """)

    @Query("""
    {
      "multi_match": {
        "query": "?0",
        "fields": ["group_name", "description"],
        "analyzer": "ik_max_word"
      }
    }
    """)
    Page<CircleDocument> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("""
        {
          "function_score": {
            "query": {
              "multi_match": {  // 关键字匹配 group_name 和 description
                "query": "?0",
                "fields": ["group_name", "description"],
                "analyzer": "ik_max_word"
              }
            },
            "functions": [
              {
                "filter": { "term": { "owner_id": "?1" } },  // 当前用户创建的圈子
                "weight": 2  // 权重加倍（提升优先级）
              }
            ],
            "boost_mode": "sum"  // 将权重分与原评分相加
          }
        }
        """)
    Page<CircleDocument> findAllWithCreatorPriority(String keyword, Long currentUserId, Pageable pageable);

    // 3. 精确匹配 group_name.keyword
    @Query("{\"term\": {\"groupName.keyword\": \"?0\"}}")
    List<CircleDocument> findByGroupNameExact(String groupName,Pageable pageable);

    // 4. 分页查询所有（按创建时间倒序）
    @Query("{\"match_all\": {}}")
    Page<CircleDocument> findAllByCreateTimeDesc(Pageable pageable);

}
