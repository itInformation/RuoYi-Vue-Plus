package org.dromara.circle.domain;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/21 17:18
 */
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 相似用户实体
 */
@Data
@AllArgsConstructor
public class SimilarUser {
    /**
     * 相似用户ID
     */
    private Long userId;

    /**
     * 相似度分数（范围：0.0~1.0）
     */
    private Double similarity;

    /**
     * 无参构造方法（序列化需要）
     */
    public SimilarUser() {}
}
