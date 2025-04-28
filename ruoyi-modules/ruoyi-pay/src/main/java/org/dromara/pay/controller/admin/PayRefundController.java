package org.dromara.pay.controller.admin;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.pay.domain.vo.PayRefundVo;
import org.dromara.pay.domain.bo.PayRefundBo;
import org.dromara.pay.service.IPayRefundService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 退款记录
 *
 * @author Lion Li
 * @date 2025-04-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/refund")
public class PayRefundController extends BaseController {

    private final IPayRefundService payRefundService;

    /**
     * 查询退款记录列表
     */
    @SaCheckPermission("system:refund:list")
    @GetMapping("/list")
    public TableDataInfo<PayRefundVo> list(PayRefundBo bo, PageQuery pageQuery) {
        return payRefundService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出退款记录列表
     */
    @SaCheckPermission("system:refund:export")
    @Log(title = "退款记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(PayRefundBo bo, HttpServletResponse response) {
        List<PayRefundVo> list = payRefundService.queryList(bo);
        ExcelUtil.exportExcel(list, "退款记录", PayRefundVo.class, response);
    }

    /**
     * 获取退款记录详细信息
     *
     * @param refundId 主键
     */
    @SaCheckPermission("system:refund:query")
    @GetMapping("/{refundId}")
    public R<PayRefundVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long refundId) {
        return R.ok(payRefundService.queryById(refundId));
    }

    /**
     * 新增退款记录
     */
    @SaCheckPermission("system:refund:add")
    @Log(title = "退款记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody PayRefundBo bo) {
        return toAjax(payRefundService.insertByBo(bo));
    }

    /**
     * 修改退款记录
     */
    @SaCheckPermission("system:refund:edit")
    @Log(title = "退款记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody PayRefundBo bo) {
        return toAjax(payRefundService.updateByBo(bo));
    }

    /**
     * 删除退款记录
     *
     * @param refundIds 主键串
     */
    @SaCheckPermission("system:refund:remove")
    @Log(title = "退款记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{refundIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] refundIds) {
        return toAjax(payRefundService.deleteWithValidByIds(List.of(refundIds), true));
    }
}
