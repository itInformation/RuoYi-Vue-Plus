package org.dromara.circle.algrom;

import org.dromara.circle.service.IUserBehaviorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/20 15:30
 */
@Component
public class CategoryRecommender {
    @Autowired
    private IUserBehaviorLogService behaviorLogService;
    /**
     * 基于用户行为的推荐算法
     * @param userId 当前用户ID
     * @return 推荐分类ID列表
     */
    public List<Long> recommendCategories(Long userId) {
        // 1. 获取用户最近浏览的圈子分类
        List<Long> viewedCats = behaviorLogService.getViewedCategories(userId);

        // 2. 获取相似用户的偏好分类
//        List<Long> similarUserCats = getSimilarUserPreferences(userId);
        List<Long> similarUserCats = null;

        // 3. 合并结果并排序
        return mergeAndRank(viewedCats, similarUserCats);
    }

    private List<Long> mergeAndRank(List<Long> list1, List<Long> list2) {
        // 使用TF-IDF算法计算权重
        Map<Long, Double> weights = new HashMap<>();
        list1.forEach(cat -> weights.put(cat, weights.getOrDefault(cat, 0.0) + 1.0));
        list2.forEach(cat -> weights.put(cat, weights.getOrDefault(cat, 0.0) + 0.8));

        return weights.entrySet().stream()
            .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
            .map(Map.Entry::getKey)
            .limit(10)
            .collect(Collectors.toList());
    }

    // 余弦相似度计算
    public double cosineSimilarity(Map<Long, Double> v1, Map<Long, Double> v2) {
        Set<Long> common = new HashSet<>(v1.keySet());
        common.retainAll(v2.keySet());

        double dotProduct = common.stream()
            .mapToDouble(k -> v1.get(k) * v2.get(k))
            .sum();

        double norm1 = Math.sqrt(v1.values().stream()
            .mapToDouble(v -> v * v).sum());
        double norm2 = Math.sqrt(v2.values().stream()
            .mapToDouble(v -> v * v).sum());

        return dotProduct / (norm1 * norm2 + 1e-8);
    }

    @Cacheable(value = "userSimilarity", key = "#userId")
    public List<SimilarUser> findSimilarUsers(Long userId, int limit) {
        Map<Long, Double> targetVector = getUserCategoryVector(userId);

        return userRepository.findAllActiveUsers().parallelStream()
            .filter(u -> !u.getId().equals(userId))
            .map(u -> {
                double similarity = cosineSimilarity(
                    targetVector,
                    getUserCategoryVector(u.getId())
                );
                return new SimilarUser(u.getId(), similarity);
            })
            .sorted(Comparator.comparingDouble(SimilarUser::getSimilarity).reversed())
            .limit(limit)
            .collect(Collectors.toList());
    }
}
