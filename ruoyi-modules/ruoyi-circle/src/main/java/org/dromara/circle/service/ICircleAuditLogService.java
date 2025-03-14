package org.dromara.circle.service;

import org.dromara.circle.domain.vo.CircleAuditLogVo;
import org.dromara.circle.domain.bo.CircleAuditLogBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
public interface ICircleAuditLogService {

    /**
     * 查询【请填写功能名称】
     *
     * @param logId 主键
     * @return 【请填写功能名称】
     */
    CircleAuditLogVo queryById(Long logId);

    /**
     * 分页查询【请填写功能名称】列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 【请填写功能名称】分页列表
     */
    TableDataInfo<CircleAuditLogVo> queryPageList(CircleAuditLogBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的【请填写功能名称】列表
     *
     * @param bo 查询条件
     * @return 【请填写功能名称】列表
     */
    List<CircleAuditLogVo> queryList(CircleAuditLogBo bo);

    /**
     * 新增【请填写功能名称】
     *
     * @param bo 【请填写功能名称】
     * @return 是否新增成功
     */
    Boolean insertByBo(CircleAuditLogBo bo);

    /**
     * 修改【请填写功能名称】
     *
     * @param bo 【请填写功能名称】
     * @return 是否修改成功
     */
    Boolean updateByBo(CircleAuditLogBo bo);

    /**
     * 校验并批量删除【请填写功能名称】信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
