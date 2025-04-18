package org.dromara.system.service;

import org.dromara.system.domain.vo.UserAssetVo;
import org.dromara.system.domain.bo.UserAssetBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 普通用户资产Service接口
 *
 * @author Lion Li
 * @date 2025-04-18
 */
public interface IUserAssetService {

    /**
     * 查询普通用户资产
     *
     * @param userId 主键
     * @return 普通用户资产
     */
    UserAssetVo queryById(Long userId);

    /**
     * 分页查询普通用户资产列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 普通用户资产分页列表
     */
    TableDataInfo<UserAssetVo> queryPageList(UserAssetBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的普通用户资产列表
     *
     * @param bo 查询条件
     * @return 普通用户资产列表
     */
    List<UserAssetVo> queryList(UserAssetBo bo);

    /**
     * 新增普通用户资产
     *
     * @param bo 普通用户资产
     * @return 是否新增成功
     */
    Boolean insertByBo(UserAssetBo bo);

    /**
     * 修改普通用户资产
     *
     * @param bo 普通用户资产
     * @return 是否修改成功
     */
    Boolean updateByBo(UserAssetBo bo);

    /**
     * 校验并批量删除普通用户资产信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
