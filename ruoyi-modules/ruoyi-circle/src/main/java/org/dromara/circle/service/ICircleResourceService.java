package org.dromara.circle.service;

import org.dromara.circle.domain.vo.CircleResourceVo;
import org.dromara.circle.domain.bo.CircleResourceBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 资源文件Service接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
public interface ICircleResourceService {

    /**
     * 查询资源文件
     *
     * @param resourceId 主键
     * @return 资源文件
     */
    CircleResourceVo queryById(String resourceId);

    /**
     * 分页查询资源文件列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 资源文件分页列表
     */
    TableDataInfo<CircleResourceVo> queryPageList(CircleResourceBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的资源文件列表
     *
     * @param bo 查询条件
     * @return 资源文件列表
     */
    List<CircleResourceVo> queryList(CircleResourceBo bo);

    /**
     * 新增资源文件
     *
     * @param bo 资源文件
     * @return 是否新增成功
     */
    Boolean insertByBo(CircleResourceBo bo);

    /**
     * 修改资源文件
     *
     * @param bo 资源文件
     * @return 是否修改成功
     */
    Boolean updateByBo(CircleResourceBo bo);

    /**
     * 校验并批量删除资源文件信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
