package org.dromara.pay.controller.client;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.common.web.core.BaseController;
import org.dromara.pay.domain.bo.PayOrderBo;
import org.dromara.pay.domain.vo.PayOrderVo;
import org.dromara.pay.service.IPayOrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付订单 app端
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/client/pay/order")
public class PayOrderClientController extends BaseController {

    private final IPayOrderService payOrderService;

    /**
     * 查询支付订单列表 只查用户自己的支付订单
     */
    @SaCheckPermission("client:order:list")
    @GetMapping("/list")
    public TableDataInfo<PayOrderVo> list(PayOrderBo bo, PageQuery pageQuery) {
        bo.setUserId(LoginHelper.getUserId());
        return payOrderService.queryClientPageList(bo, pageQuery);
    }

    /**
     * 获取支付订单详细信息
     *
     * @param orderId 主键
     */
    @SaCheckPermission("client:order:query")
    @GetMapping("/{orderId}")
    public R<PayOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long orderId) {
        return R.ok(payOrderService.queryById(orderId));
    }

    /**
     * 新增支付订单
     */
    @SaCheckPermission("client:order:add")
    @Log(title = "支付订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody PayOrderBo bo) {
        return toAjax(payOrderService.insertByBo(bo));
    }

    /**
     * 修改支付订单
     */
    @SaCheckPermission("client:order:edit")
    @Log(title = "支付订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/update")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody PayOrderBo bo) {
        return toAjax(payOrderService.updateByBo(bo));
    }

    /**
     * 删除支付订单
     *
     * @param orderIds 主键串
     */
    @SaCheckPermission("client:order:remove")
    @Log(title = "支付订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] orderIds) {
        return toAjax(payOrderService.deleteWithValidByIds(List.of(orderIds), true));
    }
}
