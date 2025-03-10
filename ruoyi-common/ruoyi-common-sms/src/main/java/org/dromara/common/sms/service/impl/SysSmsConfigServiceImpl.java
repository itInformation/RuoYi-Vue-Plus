package org.dromara.common.sms.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.sms.domain.SysSmsConfig;
import org.dromara.common.sms.domain.bo.SysSmsConfigBo;
import org.dromara.common.sms.domain.vo.SysSmsConfigVo;
import org.dromara.common.sms.mapper.SysSmsConfigMapper;
import org.dromara.common.sms.service.ISysSmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 短信配置Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Service
public class SysSmsConfigServiceImpl implements ISysSmsConfigService {
    @Autowired
    private SysSmsConfigMapper baseMapper;

    /**
     * 查询短信配置
     *
     * @param configId 主键
     * @return 短信配置
     */
    @Override
    public SysSmsConfigVo queryById(Long configId){
        return baseMapper.selectVoById(configId);
    }

    @Override
    public SysSmsConfigVo queryBySupplier(String supplier) {
        return baseMapper.selectVoOne(Wrappers.lambdaQuery(SysSmsConfig.class)
            .eq(SysSmsConfig::getSupplier, supplier));
    }

    /**
     * 分页查询短信配置列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 短信配置分页列表
     */
    @Override
    public TableDataInfo<SysSmsConfigVo> queryPageList(SysSmsConfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysSmsConfig> lqw = buildQueryWrapper(bo);
        Page<SysSmsConfigVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的短信配置列表
     *
     * @param bo 查询条件
     * @return 短信配置列表
     */
    @Override
    public List<SysSmsConfigVo> queryList(SysSmsConfigBo bo) {
        LambdaQueryWrapper<SysSmsConfig> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysSmsConfig> buildQueryWrapper(SysSmsConfigBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysSmsConfig> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(SysSmsConfig::getConfigId);
        lqw.eq(StringUtils.isNotBlank(bo.getSupplier()), SysSmsConfig::getSupplier, bo.getSupplier());
        lqw.eq(StringUtils.isNotBlank(bo.getAccessKey()), SysSmsConfig::getAccessKey, bo.getAccessKey());
        lqw.eq(StringUtils.isNotBlank(bo.getSecretKey()), SysSmsConfig::getSecretKey, bo.getSecretKey());
        lqw.eq(StringUtils.isNotBlank(bo.getSignature()), SysSmsConfig::getSignature, bo.getSignature());
        lqw.eq(StringUtils.isNotBlank(bo.getTemplateId()), SysSmsConfig::getTemplateId, bo.getTemplateId());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysSmsConfig::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增短信配置
     *
     * @param bo 短信配置
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysSmsConfigBo bo) {
        SysSmsConfig add = MapstructUtils.convert(bo, SysSmsConfig.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setConfigId(add.getConfigId());
        }
        return flag;
    }

    /**
     * 修改短信配置
     *
     * @param bo 短信配置
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(SysSmsConfigBo bo) {
        SysSmsConfig update = MapstructUtils.convert(bo, SysSmsConfig.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysSmsConfig entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除短信配置信息
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
