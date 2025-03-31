package org.dromara.circle.controller.client;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.circle.domain.bo.CircleContentPermBo;
import org.dromara.circle.domain.vo.CircleContentPermVo;
import org.dromara.circle.service.ICircleContentPermService;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * app端 内容权限关联
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/client/contentPerm")
public class CircleContentPermClientController extends BaseController {

    private final ICircleContentPermService circleContentPermService;

    /**
     * 查询内容权限关联列表
     */
    @SaCheckPermission("client:contentPerm:list")
    @GetMapping("/list")
    public TableDataInfo<CircleContentPermVo> list(CircleContentPermBo bo, PageQuery pageQuery) {
        return circleContentPermService.queryPageList(bo, pageQuery);
    }
    /**
     * 获取内容权限关联详细信息
     *
     * @param contentId 主键
     */
    @SaCheckPermission("client:contentPerm:query")
    @GetMapping("/{contentId}")
    public R<CircleContentPermVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long contentId) {
        return R.ok(circleContentPermService.queryById(contentId));
    }

    /**
     * 新增内容权限关联
     */
    @SaCheckPermission("client:contentPerm:add")
    @Log(title = "内容权限关联", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CircleContentPermBo bo) {
        return toAjax(circleContentPermService.insertByBo(bo));
    }

    /**
     * 修改内容权限关联
     */
    @SaCheckPermission("client:contentPerm:edit")
    @Log(title = "内容权限关联", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CircleContentPermBo bo) {
        return toAjax(circleContentPermService.updateByBo(bo));
    }

    /**
     * 删除内容权限关联
     *
     * @param contentIds 主键串
     */
    @SaCheckPermission("client:contentPerm:remove")
    @Log(title = "内容权限关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{contentIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] contentIds) {
        return toAjax(circleContentPermService.deleteWithValidByIds(List.of(contentIds), true));
    }
}
