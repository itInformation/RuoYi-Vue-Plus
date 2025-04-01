package org.dromara.system.controller.system.admin;

import java.util.List;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.domain.bo.SysIdentityBo;
import org.dromara.system.domain.bo.SysIdentityCallbackBo;
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
import org.dromara.system.domain.vo.SysRealnameAuthVo;
import org.dromara.system.domain.bo.SysRealnameAuthBo;
import org.dromara.system.service.ISysRealnameAuthService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 实名认证信息 Admin
 *
 * @author Lion Li
 * @date 2025-04-01
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/realnameAuth")
public class SysRealnameAuthController extends BaseController {

    private final ISysRealnameAuthService sysRealnameAuthService;

    /**
     * 查询实名认证信息 Admin列表
     */
    @SaCheckPermission("system:realnameAuth:list")
    @GetMapping("/list")
    public TableDataInfo<SysRealnameAuthVo> list(SysRealnameAuthBo bo, PageQuery pageQuery) {
        return sysRealnameAuthService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出实名认证信息 Admin列表
     */
    @SaCheckPermission("system:realnameAuth:export")
    @Log(title = "实名认证信息 Admin", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysRealnameAuthBo bo, HttpServletResponse response) {
        List<SysRealnameAuthVo> list = sysRealnameAuthService.queryList(bo);
        ExcelUtil.exportExcel(list, "实名认证信息 Admin", SysRealnameAuthVo.class, response);
    }

    /**
     * 获取实名认证信息 Admin详细信息
     *
     * @param authId 主键
     */
    @SaCheckPermission("system:realnameAuth:query")
    @GetMapping("/{authId}")
    public R<SysRealnameAuthVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long authId) {
        return R.ok(sysRealnameAuthService.queryById(authId));
    }

    /**
     * 新增实名认证信息 Admin
     */
    @SaCheckPermission("system:realnameAuth:add")
    @Log(title = "实名认证信息 Admin", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysRealnameAuthBo bo) {
        return toAjax(sysRealnameAuthService.insertByBo(bo));
    }

    /**
     * 修改实名认证信息 Admin
     */
    @SaCheckPermission("system:realnameAuth:edit")
    @Log(title = "实名认证信息 Admin", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysRealnameAuthBo bo) {
        return toAjax(sysRealnameAuthService.updateByBo(bo));
    }

    /**
     * 删除实名认证信息 Admin
     *
     * @param authIds 主键串
     */
    @SaCheckPermission("system:realnameAuth:remove")
    @Log(title = "实名认证信息 Admin", businessType = BusinessType.DELETE)
    @DeleteMapping("/{authIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] authIds) {
        return toAjax(sysRealnameAuthService.deleteWithValidByIds(List.of(authIds), true));
    }

    /**
     * 上传身份信息
     *
     */
    @GetMapping("/uploadIdentity")
    @SaIgnore
    public String get() {
        return sysRealnameAuthService.uploadIdentityInfo(LoginHelper.getUserId());
    }

    /**
     * 开始认证
     *
     */

    @PostMapping("/startIdentity")
    @SaIgnore
    public String startIdentity(@RequestBody SysIdentityBo bo) {
        return sysRealnameAuthService.startIdentity(LoginHelper.getUserId(),bo.getOrderNo(),bo.getOptimalDomain(),"api.omuu.cn/prod-api/",bo.getH5FaceId());
    }
    /**
     * 认证成功回调
     *
     */
    @PostMapping("/identityCallback")
    @SaIgnore
    public R<Void> identityCallback(@RequestBody SysIdentityCallbackBo bo) {
        sysRealnameAuthService.identityCallback(bo.getCode(),bo.getOrderNo(),bo.getH5FaceId(),bo.getNewSign());

        return R.ok();
    }
    /**
     * 认证成功回调
     *
     */
    @GetMapping("/queryIdentityResult")
    @SaIgnore
    public R<JSONObject> queryIdentityResult(String orderNo) {

        return R.ok(sysRealnameAuthService.queryIdentityResult(orderNo));
    }


}
