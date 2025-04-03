package org.dromara.circle.controller.client;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.circle.anno.ContentPermission;
import org.dromara.circle.domain.bo.CircleContentBo;
import org.dromara.circle.domain.vo.CircleContentVo;
import org.dromara.circle.service.ICircleContentService;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
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
 * app端圈子内容
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/client/content")
public class CircleContentClientController extends BaseController {

    private final ICircleContentService circleContentService;

    /**
     * 查询圈子内容列表
     */
    @SaCheckPermission("client:content:list")
    @GetMapping("/list")
    public TableDataInfo<CircleContentVo> list(CircleContentBo bo, PageQuery pageQuery) {
        return circleContentService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取圈子内容详细信息
     *
     * @param contentId 主键
     */
    @SaCheckPermission("client:content:query")
    @GetMapping("/{contentId}")
    @ContentPermission
    public R<CircleContentVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long contentId) {
        return R.ok(circleContentService.queryById(contentId));
    }

    /**
     * 新增圈子内容
     */
    @SaCheckPermission("client:content:add")
    @Log(title = "圈子内容", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CircleContentBo bo) {
        return toAjax(circleContentService.insertByBo(bo));
    }

    /**
     * 修改圈子内容
     */
    @SaCheckPermission("client:content:edit")
    @Log(title = "圈子内容", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CircleContentBo bo) {
        return toAjax(circleContentService.updateByBo(bo));
    }

    /**
     * 删除圈子内容
     *
     * @param contentIds 主键串
     */
    @SaCheckPermission("client:content:remove")
    @Log(title = "圈子内容", businessType = BusinessType.DELETE)
    @DeleteMapping("/{contentIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] contentIds) {
        return toAjax(circleContentService.deleteWithValidByIds(List.of(contentIds), true));
    }
}
