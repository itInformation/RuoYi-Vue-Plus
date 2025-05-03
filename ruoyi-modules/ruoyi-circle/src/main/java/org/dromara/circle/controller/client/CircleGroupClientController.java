package org.dromara.circle.controller.client;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.circle.domain.bo.CircleGroupBo;
import org.dromara.circle.domain.bo.CircleGroupClientPageBo;
import org.dromara.circle.domain.vo.CircleGroupVo;
import org.dromara.circle.es.document.CircleDocument;
import org.dromara.circle.service.ICircleGroupESService;
import org.dromara.circle.service.ICircleGroupService;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.common.web.core.BaseController;
import org.springframework.data.domain.Page;
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
    private final ICircleGroupESService circleGroupESService;
    /**
     * 查询圈子主体列表，达人用户只能查询自己新增的圈子
     */
    @SaCheckPermission("client:group:ownerList")
    @GetMapping("/list")
    public TableDataInfo<CircleGroupVo> list(CircleGroupClientPageBo bo, PageQuery pageQuery) {
        CircleGroupBo circleGroupBo = MapstructUtils.convert(bo, CircleGroupBo.class);
        circleGroupBo.setOwnerId(LoginHelper.getUserId());
        return circleGroupService.queryOwnerPageListWithClient(circleGroupBo, pageQuery);
    }

    /**
     * 普通用查询查询可以查看的圈子列表
     */
    @SaCheckPermission("client:group:userList")
    @GetMapping("/userList")
    public TableDataInfo<CircleGroupVo> userList(CircleGroupBo bo, PageQuery pageQuery) {

        return circleGroupService.queryPageListWithClient(bo, pageQuery);
    }


    /**
     * 查询审核失败的圈子，供达人自己查询自己的
     */
    @SaCheckPermission("client:group:ownerFailureList")
    @GetMapping("/ownerFailureList")
    public TableDataInfo<CircleGroupVo> ownerFailureList(CircleGroupBo bo, PageQuery pageQuery) {
        bo.setOwnerId(LoginHelper.getUserId());
        return circleGroupService.queryOwnerReviewFailurePageListWithClient(bo, pageQuery);
    }



    /**
     * 获取圈子主体详细信息
     *
     * @param groupId 主键
     */
    @SaCheckPermission("client:group:query")
    @GetMapping("/{groupId}")
    public R<CircleGroupVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String groupId) {
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
    @PostMapping("/update")
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
                          @PathVariable String groupId) {
        return toAjax(circleGroupService.deleteWithValidById(groupId, true));
    }

    /**
     * 关键字查询圈子
     */
    @SaCheckPermission("client:group:query")
    @GetMapping("/queryByKeyword")
    public R<Page<CircleDocument>> queryByKeyword(
        @RequestParam String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return R.ok(circleGroupESService.searchByKeyword(keyword, page, size));
    }

    /**
     * 查询所有圈子，自己创建的优先输出
     */
    @SaCheckPermission("client:group:query")
    @GetMapping("/queryByKeywordOutCreate")
    public R<Page<CircleDocument>> queryByKeywordOutCreate(
        @RequestParam String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return R.ok(circleGroupESService.findAllWithCreatorPriority(keyword,LoginHelper.getUserId(), page, size));
    }

    /**
     * 圈子名称查询圈子
     */
    @SaCheckPermission("client:group:query")
    @GetMapping("/queryByName")
    public R<List<CircleDocument>> queryByName(
        @RequestParam String name,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        return R.ok(circleGroupESService.findCirclesByName(name,page,size));
    }
    /**
     * 查询所有圈子
     */
    @SaCheckPermission("client:group:query")
    @GetMapping("/listAllCircles")
    public R<Page<CircleDocument>> listAllCircles(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return R.ok(circleGroupESService.listAllCircles(page, size));
    }
    /**
     * 圈子精确ID查询
      */
    @SaCheckPermission("client:group:query")
    @GetMapping("/getById")
    public R<CircleDocument> getById(@RequestParam Long id) {
        return R.ok(circleGroupESService.getById(id));
    }
}
