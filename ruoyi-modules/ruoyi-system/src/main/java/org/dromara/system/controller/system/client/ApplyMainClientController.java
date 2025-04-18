package org.dromara.system.controller.system.client;

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
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.common.web.core.BaseController;
import org.dromara.system.domain.bo.ApplyMainBo;
import org.dromara.system.domain.vo.ApplyMainSubmitVo;
import org.dromara.system.domain.vo.ApplyMainVo;
import org.dromara.system.service.IApplyMainService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * app端入驻申请
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
     * 查询入驻申请列表
     */
    @SaCheckPermission("client:main:list")
    @GetMapping("/list")
    public TableDataInfo<ApplyMainVo> list(ApplyMainBo bo, PageQuery pageQuery) {
        bo.setUserId(LoginHelper.getUserId());
        return applyMainService.queryPageList(bo, pageQuery);
    }
    /**
     * 获取入驻申请详细信息
     *
     * @param applyId 主键
     */
    @SaCheckPermission("client:main:query")
    @GetMapping("/{applyId}")
    public R<ApplyMainVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long applyId) {
        return R.ok(applyMainService.queryById(applyId));
    }
    /**
     * 查询用户的入驻申请结果
     */
    @SaCheckPermission("client:main:query")
    @GetMapping("/queryApplyResult")
    public R<ApplyMainSubmitVo> queryApplyResult() {
        return R.ok(applyMainService.queryApplyResult(LoginHelper.getUserId()));
    }
    /**
     * 新增入驻申请
     */
    @SaCheckPermission("client:main:add")
    @Log(title = "app端入驻申请", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ApplyMainBo bo) {
        return toAjax(applyMainService.insertByBo(bo));
    }

    /**
     * 修改入驻申请
     */
    @SaCheckPermission("client:main:edit")
    @Log(title = "app端入驻申请", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/update")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ApplyMainBo bo) {
        return toAjax(applyMainService.updateByBo(bo));
    }

    /**
     * 删除入驻申请
     *
     * @param applyIds 主键串
     */
    @SaCheckPermission("client:main:remove")
    @Log(title = "app端入驻申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{applyIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] applyIds) {
        return toAjax(applyMainService.deleteWithValidByIds(List.of(applyIds), true));
    }
}
