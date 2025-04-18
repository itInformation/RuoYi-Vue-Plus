package org.dromara.system.domain.vo;

import org.dromara.system.domain.UserAsset;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 普通用户资产视图对象 user_asset
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = UserAsset.class)
public class UserAssetVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 钻石余额
     */
    @ExcelProperty(value = "钻石余额")
    private Long diamondBalance;

    /**
     * 冻结钻石
     */
    @ExcelProperty(value = "冻结钻石")
    private Long frozenDiamond;

    /**
     * 累计消费钻石
     */
    @ExcelProperty(value = "累计消费钻石")
    private Long totalConsumedDiamond;

    /**
     * 累计消费金额
     */
    @ExcelProperty(value = "累计消费金额")
    private Long totalConsumedAmount;

    /**
     * 累计退款金额
     */
    @ExcelProperty(value = "累计退款金额")
    private Long totalRefundAmount;


}
