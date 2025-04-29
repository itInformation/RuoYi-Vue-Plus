package org.dromara.pay.service;

import jakarta.servlet.http.HttpServletRequest;
import org.dromara.pay.domain.bo.RefundBo;
import org.dromara.pay.domain.vo.PayRefundVo;
import org.dromara.pay.domain.bo.PayRefundBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.pay.domain.vo.RefundResult;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 退款记录Service接口
 *
 * @author Lion Li
 * @date 2025-04-28
 */
public interface IPayRefundService {

    /**
     * 查询退款记录
     *
     * @param refundId 主键
     * @return 退款记录
     */
    PayRefundVo queryById(Long refundId);

    /**
     * 分页查询退款记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 退款记录分页列表
     */
    TableDataInfo<PayRefundVo> queryPageList(PayRefundBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的退款记录列表
     *
     * @param bo 查询条件
     * @return 退款记录列表
     */
    List<PayRefundVo> queryList(PayRefundBo bo);

    /**
     * 新增退款记录
     *
     * @param bo 退款记录
     * @return 是否新增成功
     */
    Boolean insertByBo(PayRefundBo bo);

    /**
     * 修改退款记录
     *
     * @param bo 退款记录
     * @return 是否修改成功
     */
    Boolean updateByBo(PayRefundBo bo);

    /**
     * 校验并批量删除退款记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 执行退款
     *
     * @return 退款受理结果
     */
    String refund(RefundBo refundBo);


    void updateRefundStatus(Long refundId, String status);
}
