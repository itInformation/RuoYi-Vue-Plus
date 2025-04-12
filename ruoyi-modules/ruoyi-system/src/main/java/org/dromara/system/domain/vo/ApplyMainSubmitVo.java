package org.dromara.system.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 入驻申请可以重复提交
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@Data
public class ApplyMainSubmitVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    private Long userId;


    /**
     * 审核状态 0 待审核 1 审核通过 2 审核失败
     */
    private String status;

    /**
     * 是否可以重复提交
     */
    private Boolean submit;

}
