package org.dromara.common.sms.service;

import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.sms.domain.bo.SysSmsConfigBo;
import org.dromara.common.sms.domain.vo.SysSmsConfigVo;

import java.util.Collection;
import java.util.List;

/**
 * 短信配置Service接口
 *
 * @author Lion Li
 * @date 2025-03-10
 */
public interface ISysSmsConfigService {

    /**
     * 查询短信配置
     *
     * @param configId 主键
     * @return 短信配置
     */
    SysSmsConfigVo queryById(Long configId);
    SysSmsConfigVo queryBySupplier(String supplier);

    /**
     * 分页查询短信配置列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 短信配置分页列表
     */
    TableDataInfo<SysSmsConfigVo> queryPageList(SysSmsConfigBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的短信配置列表
     *
     * @param bo 查询条件
     * @return 短信配置列表
     */
    List<SysSmsConfigVo> queryList(SysSmsConfigBo bo);

    /**
     * 新增短信配置
     *
     * @param bo 短信配置
     * @return 是否新增成功
     */
    Boolean insertByBo(SysSmsConfigBo bo);

    /**
     * 修改短信配置
     *
     * @param bo 短信配置
     * @return 是否修改成功
     */
    Boolean updateByBo(SysSmsConfigBo bo);

    /**
     * 校验并批量删除短信配置信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
