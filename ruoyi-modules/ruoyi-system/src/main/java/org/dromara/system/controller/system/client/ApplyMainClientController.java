package org.dromara.client.controller.client.client;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.web.core.BaseController;
import org.dromara.system.domain.bo.ApplyMainBo;
import org.dromara.system.domain.vo.ApplyMainVo;
import org.dromara.system.service.IApplyMainService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 入驻申请主
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/client/platform/apply")
public class ApplyMainClientController extends BaseController {

    private final IApplyMainService applyMainService;

    /**
     * 获取入驻申请主详细信息
     *
     * @param applyId 主键
     */
    @SaCheckPermission("system:main:query")
    @GetMapping("/{applyId}")
    public R<ApplyMainVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long applyId) {
        return R.ok(applyMainService.queryById(applyId));
    }

    /**
     * 新增入驻申请主
     */
    @SaCheckPermission("system:main:add")
    @Log(title = "入驻申请主", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ApplyMainBo bo) {
        return toAjax(applyMainService.insertByBo(bo));
    }

    /**
     * 修改入驻申请主
     */
    @SaCheckPermission("system:main:edit")
    @Log(title = "入驻申请主", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ApplyMainBo bo) {
        return toAjax(applyMainService.updateByBo(bo));
    }

    /**
     * 删除入驻申请主
     *
     * @param applyIds 主键串
     */
    @SaCheckPermission("system:main:remove")
    @Log(title = "入驻申请主", businessType = BusinessType.DELETE)
    @DeleteMapping("/{applyIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] applyIds) {
        return toAjax(applyMainService.deleteWithValidByIds(List.of(applyIds), true));
    }
}
