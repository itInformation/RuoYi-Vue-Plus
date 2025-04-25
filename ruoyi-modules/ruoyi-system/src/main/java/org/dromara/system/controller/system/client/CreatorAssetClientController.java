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
import org.dromara.common.web.core.BaseController;
import org.dromara.system.domain.bo.CreatorAssetBo;
import org.dromara.system.domain.bo.CreatorAssetWithdrawBo;
import org.dromara.system.domain.vo.CreatorAssetVo;
import org.dromara.system.service.ICreatorAssetService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 创作者资产 app端
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/client/creator/asset")
public class CreatorAssetClientController extends BaseController {

    private final ICreatorAssetService creatorAssetService;

    /**
     * 查询创作者资产列表
     */
    @SaCheckPermission("client:asset:list")
    @GetMapping("/list")
    public TableDataInfo<CreatorAssetVo> list(CreatorAssetBo bo, PageQuery pageQuery) {
        return creatorAssetService.queryPageList(bo, pageQuery);
    }
    /**
     * 获取创作者资产详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("client:asset:query")
    @GetMapping("/{userId}")
    public R<CreatorAssetVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(creatorAssetService.queryById(userId));
    }

    /**
     * 新增创作者资产
     */
    @SaCheckPermission("client:asset:add")
    @Log(title = "创作者资产", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CreatorAssetBo bo) {
        return toAjax(creatorAssetService.insertByBo(bo));
    }

    /**
     * 修改创作者资产
     */
    @SaCheckPermission("client:asset:edit")
    @Log(title = "创作者资产", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/update")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CreatorAssetBo bo) {
        return toAjax(creatorAssetService.updateByBo(bo));
    }

    /**
     * 删除创作者资产
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("client:asset:remove")
    @Log(title = "创作者资产", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(creatorAssetService.deleteWithValidByIds(List.of(userIds), true));
    }

    /**
     * 申请提现
     */
    @SaCheckPermission("client:asset:apply")
    @Log(title = "普通用户资产-申请体现", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/applyWithdraw")
    public R<Void> applyWithdraw(@Validated(EditGroup.class) @RequestBody CreatorAssetWithdrawBo bo) {
        creatorAssetService.applyWithdraw(bo.getUserId(),bo.getAmount());
        return R.ok();
    }
}
