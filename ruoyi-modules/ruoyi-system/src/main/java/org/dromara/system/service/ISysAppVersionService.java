package org.dromara.system.service;

import org.dromara.system.domain.vo.SysAppVersionVo;
import org.dromara.system.domain.bo.SysAppVersionBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * App版本信息Service接口
 *
 * @author Lion Li
 * @date 2025-03-20
 */
public interface ISysAppVersionService {

    /**
     * 查询App版本信息
     *
     * @param id 主键
     * @return App版本信息
     */
    SysAppVersionVo queryById(Long id);

    /**
     * 分页查询App版本信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return App版本信息分页列表
     */
    TableDataInfo<SysAppVersionVo> queryPageList(SysAppVersionBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的App版本信息列表
     *
     * @param bo 查询条件
     * @return App版本信息列表
     */
    List<SysAppVersionVo> queryList(SysAppVersionBo bo);

    /**
     * 新增App版本信息
     *
     * @param bo App版本信息
     * @return 是否新增成功
     */
    Boolean insertByBo(SysAppVersionBo bo);

    /**
     * 修改App版本信息
     *
     * @param bo App版本信息
     * @return 是否修改成功
     */
    Boolean updateByBo(SysAppVersionBo bo);

    /**
     * 校验并批量删除App版本信息信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
