package org.dromara.system.mapper;

import org.dromara.system.domain.vo.CreatorIncomeLogVo;
import org.dromara.system.domain.bo.CreatorIncomeLogBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 创作者收入明细Service接口
 *
 * @author Lion Li
 * @date 2025-04-18
 */
public interface ICreatorIncomeLogService {

    /**
     * 查询创作者收入明细
     *
     * @param id 主键
     * @return 创作者收入明细
     */
    CreatorIncomeLogVo queryById(Long id);

    /**
     * 分页查询创作者收入明细列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 创作者收入明细分页列表
     */
    TableDataInfo<CreatorIncomeLogVo> queryPageList(CreatorIncomeLogBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的创作者收入明细列表
     *
     * @param bo 查询条件
     * @return 创作者收入明细列表
     */
    List<CreatorIncomeLogVo> queryList(CreatorIncomeLogBo bo);

    /**
     * 新增创作者收入明细
     *
     * @param bo 创作者收入明细
     * @return 是否新增成功
     */
    Boolean insertByBo(CreatorIncomeLogBo bo);

    /**
     * 修改创作者收入明细
     *
     * @param bo 创作者收入明细
     * @return 是否修改成功
     */
    Boolean updateByBo(CreatorIncomeLogBo bo);

    /**
     * 校验并批量删除创作者收入明细信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
