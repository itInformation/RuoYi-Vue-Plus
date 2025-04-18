package org.dromara.system.domain.vo;

import org.dromara.system.domain.CreatorAsset;
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
 * 创作者资产视图对象 creator_asset
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CreatorAsset.class)
public class CreatorAssetVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 可提现金额
     */
    @ExcelProperty(value = "可提现金额")
    private Long withdrawable;

    /**
     * 冻结可提现
     */
    @ExcelProperty(value = "冻结可提现")
    private Long frozenWithdrawable;

    /**
     * 待入账金额
     */
    @ExcelProperty(value = "待入账金额")
    private Long pendingAmount;

    /**
     * 冻结待入账
     */
    @ExcelProperty(value = "冻结待入账")
    private Long frozenPending;

    /**
     * 累计提现金额
     */
    @ExcelProperty(value = "累计提现金额")
    private Long totalWithdrawn;

    /**
     * 累计收益金额
     */
    @ExcelProperty(value = "累计收益金额")
    private Long totalIncome;

    /**
     * 累计退款金额
     */
    @ExcelProperty(value = "累计退款金额")
    private Long totalRefund;


}
