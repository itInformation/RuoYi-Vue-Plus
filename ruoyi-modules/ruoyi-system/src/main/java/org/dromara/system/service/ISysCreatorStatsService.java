package org.dromara.system.service;

import org.dromara.system.domain.vo.SysCreatorStatsVo;
import org.dromara.system.domain.bo.SysCreatorStatsBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 创作者统计Service接口
 *
 * @author Lion Li
 * @date 2025-04-08
 */
public interface ISysCreatorStatsService {

    /**
     * 查询创作者统计
     *
     * @param userId 主键
     * @return 创作者统计
     */
    SysCreatorStatsVo queryById(Long userId);

    /**
     * 分页查询创作者统计列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 创作者统计分页列表
     */
    TableDataInfo<SysCreatorStatsVo> queryPageList(SysCreatorStatsBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的创作者统计列表
     *
     * @param bo 查询条件
     * @return 创作者统计列表
     */
    List<SysCreatorStatsVo> queryList(SysCreatorStatsBo bo);

    /**
     * 新增创作者统计
     *
     * @param bo 创作者统计
     * @return 是否新增成功
     */
    Boolean insertByBo(SysCreatorStatsBo bo);

    /**
     * 修改创作者统计
     *
     * @param bo 创作者统计
     * @return 是否修改成功
     */
    Boolean updateByBo(SysCreatorStatsBo bo);

    /**
     * 校验并批量删除创作者统计信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
