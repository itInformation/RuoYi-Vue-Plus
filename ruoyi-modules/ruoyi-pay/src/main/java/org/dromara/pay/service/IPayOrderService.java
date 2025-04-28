package org.dromara.pay.service;

import org.dromara.pay.domain.vo.PayAddOrderVo;
import org.dromara.pay.domain.vo.PayOrderVo;
import org.dromara.pay.domain.bo.PayOrderBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * 支付订单Service接口
 *
 * @author Lion Li
 * @date 2025-03-10
 */
public interface IPayOrderService {

    /**
     * 查询支付订单
     *
     * @param orderId 主键
     * @return 支付订单
     */
    PayOrderVo queryById(Long orderId);
    PayOrderVo queryById(String orderId);

    /**
     * 分页查询支付订单列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 支付订单分页列表
     */
    TableDataInfo<PayOrderVo> queryPageList(PayOrderBo bo, PageQuery pageQuery);
    TableDataInfo<PayOrderVo> queryClientPageList(PayOrderBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的支付订单列表
     *
     * @param bo 查询条件
     * @return 支付订单列表
     */
    List<PayOrderVo> queryList(PayOrderBo bo);

    /**
     * 新增支付订单
     *
     * @param bo 支付订单
     * @return 是否新增成功
     */
    Boolean insertByBo(PayOrderBo bo);

    /**
     * 创建支付订单
     * @param orderBo
     * @return
     */
    PayAddOrderVo createOrder(PayOrderBo orderBo);
    /**
     * 修改支付订单
     *
     * @param bo 支付订单
     * @return 是否修改成功
     */
    Boolean updateByBo(PayOrderBo bo);

    /**
     * 校验并批量删除支付订单信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    void updateRefundAmount(String orderNo, BigDecimal amount);
}
