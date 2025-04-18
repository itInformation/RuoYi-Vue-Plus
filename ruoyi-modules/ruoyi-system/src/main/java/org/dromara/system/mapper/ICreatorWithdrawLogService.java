package org.dromara.system.mapper;

import org.dromara.system.domain.vo.CreatorWithdrawLogVo;
import org.dromara.system.domain.bo.CreatorWithdrawLogBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 创作者提现记录Service接口
 *
 * @author Lion Li
 * @date 2025-04-18
 */
public interface ICreatorWithdrawLogService {

    /**
     * 查询创作者提现记录
     *
     * @param id 主键
     * @return 创作者提现记录
     */
    CreatorWithdrawLogVo queryById(Long id);

    /**
     * 分页查询创作者提现记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 创作者提现记录分页列表
     */
    TableDataInfo<CreatorWithdrawLogVo> queryPageList(CreatorWithdrawLogBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的创作者提现记录列表
     *
     * @param bo 查询条件
     * @return 创作者提现记录列表
     */
    List<CreatorWithdrawLogVo> queryList(CreatorWithdrawLogBo bo);

    /**
     * 新增创作者提现记录
     *
     * @param bo 创作者提现记录
     * @return 是否新增成功
     */
    Boolean insertByBo(CreatorWithdrawLogBo bo);

    /**
     * 修改创作者提现记录
     *
     * @param bo 创作者提现记录
     * @return 是否修改成功
     */
    Boolean updateByBo(CreatorWithdrawLogBo bo);

    /**
     * 校验并批量删除创作者提现记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
