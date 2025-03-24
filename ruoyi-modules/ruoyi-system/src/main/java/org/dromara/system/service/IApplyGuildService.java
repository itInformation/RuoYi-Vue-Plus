package org.dromara.system.service;

import org.dromara.system.domain.vo.ApplyGuildVo;
import org.dromara.system.domain.bo.ApplyGuildBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 公会入驻申请Service接口
 *
 * @author Lion Li
 * @date 2025-03-24
 */
public interface IApplyGuildService {

    /**
     * 查询公会入驻申请
     *
     * @param applyId 主键
     * @return 公会入驻申请
     */
    ApplyGuildVo queryById(Long applyId);

    /**
     * 分页查询公会入驻申请列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 公会入驻申请分页列表
     */
    TableDataInfo<ApplyGuildVo> queryPageList(ApplyGuildBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的公会入驻申请列表
     *
     * @param bo 查询条件
     * @return 公会入驻申请列表
     */
    List<ApplyGuildVo> queryList(ApplyGuildBo bo);

    /**
     * 新增公会入驻申请
     *
     * @param bo 公会入驻申请
     * @return 是否新增成功
     */
    Boolean insertByBo(ApplyGuildBo bo);

    /**
     * 修改公会入驻申请
     *
     * @param bo 公会入驻申请
     * @return 是否修改成功
     */
    Boolean updateByBo(ApplyGuildBo bo);

    /**
     * 校验并批量删除公会入驻申请信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
