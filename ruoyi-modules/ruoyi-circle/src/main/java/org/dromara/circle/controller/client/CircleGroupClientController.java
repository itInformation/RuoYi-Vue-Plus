package org.dromara.circle.controller.client;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.circle.domain.bo.CircleGroupBo;
import org.dromara.circle.domain.vo.CircleGroupVo;
import org.dromara.circle.service.ICircleGroupService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * app端 圈子主体
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/client/group")
public class CircleGroupClientController extends BaseController {

    private final ICircleGroupService circleGroupService;

    /**
     * 查询圈子主体列表，用户只能查询自己新增的圈子
     */
    @SaCheckPermission("client:group:list")
    @GetMapping("/list")
    public TableDataInfo<CircleGroupVo> list(CircleGroupBo bo, PageQuery pageQuery) {
        bo.setOwnerId(LoginHelper.getUserId());
        return circleGroupService.queryPageList(bo, pageQuery);
    }


    /**
     * 获取圈子主体详细信息
     *
     * @param groupId 主键
     */
    @SaCheckPermission("client:group:query")
    @GetMapping("/{groupId}")
    public R<CircleGroupVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long groupId) {
        return R.ok(circleGroupService.queryById(groupId));
    }

    /**
     * 新增圈子主体
     */
    @SaCheckPermission("client:group:add")
    @Log(title = "圈子主体", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CircleGroupBo bo) {
        return toAjax(circleGroupService.insertByBo(bo));
    }

    /**
     * 修改圈子主体
     */
    @SaCheckPermission("client:group:edit")
    @Log(title = "圈子主体", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CircleGroupBo bo) {
        return toAjax(circleGroupService.updateByBo(bo));
    }

    /**
     * 删除圈子主体 圈子放入回收站中
     *
     * @param groupId 主键串
     */
    @SaCheckPermission("client:group:remove")
    @Log(title = "圈子主体", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteById/{groupId}")
    public R<Void> deleteById(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long groupId) {
        return toAjax(circleGroupService.deleteWithValidById(groupId, true));
    }
}
