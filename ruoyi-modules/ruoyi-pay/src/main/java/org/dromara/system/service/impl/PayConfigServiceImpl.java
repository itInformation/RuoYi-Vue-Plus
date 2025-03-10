package org.dromara.system.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.sms.domain.SysSmsConfig;
import org.dromara.common.sms.domain.vo.SysSmsConfigVo;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.PayConfigBo;
import org.dromara.system.domain.vo.PayConfigVo;
import org.dromara.system.domain.PayConfig;
import org.dromara.system.mapper.PayConfigMapper;
import org.dromara.system.service.IPayConfigService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 支付配置Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@RequiredArgsConstructor
@Service
public class PayConfigServiceImpl implements IPayConfigService {

    private final PayConfigMapper baseMapper;

    /**
     * 查询支付配置
     *
     * @param configId 主键
     * @return 支付配置
     */
    @Override
    public PayConfigVo queryById(Long configId){
        return baseMapper.selectVoById(configId);
    }
    @Override
    public PayConfigVo queryByChannel(String channel) {
        return baseMapper.selectVoOne(Wrappers.lambdaQuery(PayConfig.class)
            .eq(PayConfig::getChannel, channel));
    }
    /**
     * 分页查询支付配置列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 支付配置分页列表
     */
    @Override
    public TableDataInfo<PayConfigVo> queryPageList(PayConfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PayConfig> lqw = buildQueryWrapper(bo);
        Page<PayConfigVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的支付配置列表
     *
     * @param bo 查询条件
     * @return 支付配置列表
     */
    @Override
    public List<PayConfigVo> queryList(PayConfigBo bo) {
        LambdaQueryWrapper<PayConfig> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<PayConfig> buildQueryWrapper(PayConfigBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<PayConfig> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(PayConfig::getConfigId);
        lqw.eq(StringUtils.isNotBlank(bo.getChannel()), PayConfig::getChannel, bo.getChannel());
        lqw.eq(StringUtils.isNotBlank(bo.getAppId()), PayConfig::getAppId, bo.getAppId());
        lqw.eq(StringUtils.isNotBlank(bo.getMchId()), PayConfig::getMchId, bo.getMchId());
        lqw.eq(StringUtils.isNotBlank(bo.getApiKey()), PayConfig::getApiKey, bo.getApiKey());
        lqw.eq(StringUtils.isNotBlank(bo.getCertPath()), PayConfig::getCertPath, bo.getCertPath());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), PayConfig::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增支付配置
     *
     * @param bo 支付配置
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(PayConfigBo bo) {
        PayConfig add = MapstructUtils.convert(bo, PayConfig.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setConfigId(add.getConfigId());
        }
        return flag;
    }

    /**
     * 修改支付配置
     *
     * @param bo 支付配置
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(PayConfigBo bo) {
        PayConfig update = MapstructUtils.convert(bo, PayConfig.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(PayConfig entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除支付配置信息
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
