package org.dromara.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.pay.domain.PayOrder;
import org.dromara.pay.domain.bo.PayOrderBo;
import org.dromara.pay.domain.vo.PayOrderVo;
import org.dromara.pay.mapper.PayOrderMapper;
import org.dromara.pay.service.IPayOrderService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 支付订单Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@RequiredArgsConstructor
@Service
public class PayOrderServiceImpl implements IPayOrderService {

    private final PayOrderMapper baseMapper;

    /**
     * 查询支付订单
     *
     * @param orderId 主键
     * @return 支付订单
     */
    @Override
    public PayOrderVo queryById(Long orderId){
        return baseMapper.selectVoById(orderId);
    }
    @Override
    public PayOrderVo queryById(String orderId){
        return baseMapper.selectVoById(orderId);
    }

    /**
     * 分页查询支付订单列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 支付订单分页列表
     */
    @Override
    public TableDataInfo<PayOrderVo> queryPageList(PayOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PayOrder> lqw = buildQueryWrapper(bo);
        return queryPage(lqw,pageQuery);
    }

    @Override
    public TableDataInfo<PayOrderVo> queryClientPageList(PayOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PayOrder> lqw = buildQueryWrapper(bo);
        return queryPage(lqw,pageQuery);
    }

    private TableDataInfo<PayOrderVo> queryPage(LambdaQueryWrapper<PayOrder> lqw,PageQuery pageQuery){
        Page<PayOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的支付订单列表
     *
     * @param bo 查询条件
     * @return 支付订单列表
     */
    @Override
    public List<PayOrderVo> queryList(PayOrderBo bo) {
        LambdaQueryWrapper<PayOrder> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<PayOrder> buildQueryWrapper(PayOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<PayOrder> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(PayOrder::getOrderId);
        lqw.eq(StringUtils.isNotBlank(bo.getOrderNo()), PayOrder::getOrderNo, bo.getOrderNo());
        lqw.eq(StringUtils.isNotBlank(bo.getChannel()), PayOrder::getChannel, bo.getChannel());
        lqw.eq(bo.getAmount() != null, PayOrder::getAmount, bo.getAmount());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), PayOrder::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getSubject()), PayOrder::getSubject, bo.getSubject());
        lqw.eq(bo.getUserId() != null, PayOrder::getUserId, bo.getUserId());
        lqw.eq(bo.getPayTime() != null, PayOrder::getPayTime, bo.getPayTime());
        lqw.eq(bo.getExpireTime() != null, PayOrder::getExpireTime, bo.getExpireTime());
        lqw.eq(StringUtils.isNotBlank(bo.getNotifyUrl()), PayOrder::getNotifyUrl, bo.getNotifyUrl());
        return lqw;
    }

    /**
     * 新增支付订单
     *
     * @param bo 支付订单
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(PayOrderBo bo) {
        PayOrder add = MapstructUtils.convert(bo, PayOrder.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setOrderId(add.getOrderId());
        }
        return flag;
    }

    /**
     * 修改支付订单
     *
     * @param bo 支付订单
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(PayOrderBo bo) {
        PayOrder update = MapstructUtils.convert(bo, PayOrder.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(PayOrder entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除支付订单信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
