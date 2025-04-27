package org.dromara.system.controller.system.admin;

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
import org.dromara.system.domain.vo.MemberTypeVo;
import org.dromara.system.domain.bo.MemberTypeBo;
import org.dromara.system.service.IMemberTypeService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 会员类型;
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/type")
public class MemberTypeController extends BaseController {

    private final IMemberTypeService memberTypeService;

    /**
     * 查询会员类型;列表
     */
    @SaCheckPermission("system:type:list")
    @GetMapping("/list")
    public TableDataInfo<MemberTypeVo> list(MemberTypeBo bo, PageQuery pageQuery) {
        return memberTypeService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出会员类型;列表
     */
    @SaCheckPermission("system:type:export")
    @Log(title = "会员类型;", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MemberTypeBo bo, HttpServletResponse response) {
        List<MemberTypeVo> list = memberTypeService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员类型;", MemberTypeVo.class, response);
    }

    /**
     * 获取会员类型;详细信息
     *
     * @param typeId 主键
     */
    @SaCheckPermission("system:type:query")
    @GetMapping("/{typeId}")
    public R<MemberTypeVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long typeId) {
        return R.ok(memberTypeService.queryById(typeId));
    }

    /**
     * 新增会员类型;
     */
    @SaCheckPermission("system:type:add")
    @Log(title = "会员类型;", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MemberTypeBo bo) {
        return toAjax(memberTypeService.insertByBo(bo));
    }

    /**
     * 修改会员类型;
     */
    @SaCheckPermission("system:type:edit")
    @Log(title = "会员类型;", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MemberTypeBo bo) {
        return toAjax(memberTypeService.updateByBo(bo));
    }

    /**
     * 删除会员类型;
     *
     * @param typeIds 主键串
     */
    @SaCheckPermission("system:type:remove")
    @Log(title = "会员类型;", businessType = BusinessType.DELETE)
    @DeleteMapping("/{typeIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] typeIds) {
        return toAjax(memberTypeService.deleteWithValidByIds(List.of(typeIds), true));
    }
}
