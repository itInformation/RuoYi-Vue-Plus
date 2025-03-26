package org.dromara.common.core.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 圈子和圈子内容状态枚举
 *
 * @author may
 */
@Getter
@AllArgsConstructor
public enum CircleGroupAndContentStatusEnum {

    /**
     * 审核失败
     */
    FAILURE(2, "审核失败"),

    /**
     * 审核成功
     */
    SUCCESS(1, "审核成功"),

    /**
     * 待审核
     */
    WAITING(0, "待审核"),

    ;


    /**
     * 状态
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String desc;

    private static final Map<Integer, CircleGroupAndContentStatusEnum> STATUS_MAP = Arrays.stream(CircleGroupAndContentStatusEnum.values())
        .collect(Collectors.toConcurrentMap(CircleGroupAndContentStatusEnum::getStatus, Function.identity()));

    /**
     * 根据状态获取对应的 BusinessStatusEnum 枚举
     *
     * @param status 业务状态码
     * @return 对应的 BusinessStatusEnum 枚举，如果找不到则返回 null
     */
    public static CircleGroupAndContentStatusEnum getByStatus(Integer status) {
        // 使用 STATUS_MAP 获取对应的枚举，若找不到则返回 null
        return STATUS_MAP.get(status);
    }

    /**
     * 根据状态获取对应的业务状态描述信息
     *
     * @param status 业务状态码
     * @return 返回业务状态描述，若状态码为空或未找到对应的枚举，返回空字符串
     */
    public static String findByStatus(Integer status) {
        if (ObjectUtils.isEmpty(status)) {
            return StrUtil.EMPTY;
        }
        CircleGroupAndContentStatusEnum statusEnum = STATUS_MAP.get(status);
        return (statusEnum != null) ? statusEnum.getDesc() : StrUtil.EMPTY;
    }
}
