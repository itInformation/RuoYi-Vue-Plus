package org.dromara.circle.service;

import org.dromara.circle.domain.CircleContentPerm;
import org.dromara.circle.domain.vo.CircleContentPermVo;
import org.dromara.circle.domain.bo.CircleContentPermBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 内容权限关联Service接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
public interface ICircleContentPermService {

    /**
     * 查询内容权限关联
     *
     * @param contentId 主键
     * @return 内容权限关联
     */
    CircleContentPermVo queryById(String contentId);

    /**
     * 分页查询内容权限关联列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 内容权限关联分页列表
     */
    TableDataInfo<CircleContentPermVo> queryPageList(CircleContentPermBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的内容权限关联列表
     *
     * @param bo 查询条件
     * @return 内容权限关联列表
     */
    List<CircleContentPermVo> queryList(CircleContentPermBo bo);

    /**
     * 新增内容权限关联
     *
     * @param bo 内容权限关联
     * @return 是否新增成功
     */
    Boolean insertByBo(CircleContentPermBo bo);

    /**
     * 修改内容权限关联
     *
     * @param bo 内容权限关联
     * @return 是否修改成功
     */
    Boolean updateByBo(CircleContentPermBo bo);

    /**
     * 校验并批量删除内容权限关联信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 校验用户是否有权限访问该内容
     * @param contentId
     * @param userId
     * @return
     */
    List<CircleContentPerm> checkContentPermission(String contentId, Long userId);
}
