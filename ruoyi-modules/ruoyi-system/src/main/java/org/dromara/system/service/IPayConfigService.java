package org.dromara.system.service;

import org.dromara.system.domain.vo.PayConfigVo;
import org.dromara.system.domain.bo.PayConfigBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 支付配置Service接口
 *
 * @author Lion Li
 * @date 2025-03-10
 */
public interface IPayConfigService {

    /**
     * 查询支付配置
     *
     * @param configId 主键
     * @return 支付配置
     */
    PayConfigVo queryById(Long configId);

    /**
     * 分页查询支付配置列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 支付配置分页列表
     */
    TableDataInfo<PayConfigVo> queryPageList(PayConfigBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的支付配置列表
     *
     * @param bo 查询条件
     * @return 支付配置列表
     */
    List<PayConfigVo> queryList(PayConfigBo bo);

    /**
     * 新增支付配置
     *
     * @param bo 支付配置
     * @return 是否新增成功
     */
    Boolean insertByBo(PayConfigBo bo);

    /**
     * 修改支付配置
     *
     * @param bo 支付配置
     * @return 是否修改成功
     */
    Boolean updateByBo(PayConfigBo bo);

    /**
     * 校验并批量删除支付配置信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
