package org.dromara.system.service.impl;

import org.dromara.system.domain.vo.CreatorAssetVo;
import org.dromara.system.domain.bo.CreatorAssetBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 创作者资产Service接口
 *
 * @author Lion Li
 * @date 2025-04-18
 */
public interface ICreatorAssetService {

    /**
     * 查询创作者资产
     *
     * @param userId 主键
     * @return 创作者资产
     */
    CreatorAssetVo queryById(Long userId);

    /**
     * 分页查询创作者资产列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 创作者资产分页列表
     */
    TableDataInfo<CreatorAssetVo> queryPageList(CreatorAssetBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的创作者资产列表
     *
     * @param bo 查询条件
     * @return 创作者资产列表
     */
    List<CreatorAssetVo> queryList(CreatorAssetBo bo);

    /**
     * 新增创作者资产
     *
     * @param bo 创作者资产
     * @return 是否新增成功
     */
    Boolean insertByBo(CreatorAssetBo bo);

    /**
     * 修改创作者资产
     *
     * @param bo 创作者资产
     * @return 是否修改成功
     */
    Boolean updateByBo(CreatorAssetBo bo);

    /**
     * 校验并批量删除创作者资产信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
