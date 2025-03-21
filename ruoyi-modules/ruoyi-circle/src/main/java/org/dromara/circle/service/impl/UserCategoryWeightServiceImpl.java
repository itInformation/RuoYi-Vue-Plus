package org.dromara.circle.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.circle.domain.bo.UserCategoryWeightBo;
import org.dromara.circle.domain.vo.UserCategoryWeightVo;
import org.dromara.circle.domain.UserCategoryWeight;
import org.dromara.circle.mapper.UserCategoryWeightMapper;
import org.dromara.circle.service.IUserCategoryWeightService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 用户分类兴趣权重Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-21
 */
@RequiredArgsConstructor
@Service
public class UserCategoryWeightServiceImpl implements IUserCategoryWeightService {

    private final UserCategoryWeightMapper baseMapper;

    /**
     * 查询用户分类兴趣权重
     *
     * @param userId 主键
     * @return 用户分类兴趣权重
     */
    @Override
    public UserCategoryWeightVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 分页查询用户分类兴趣权重列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户分类兴趣权重分页列表
     */
    @Override
    public TableDataInfo<UserCategoryWeightVo> queryPageList(UserCategoryWeightBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<UserCategoryWeight> lqw = buildQueryWrapper(bo);
        Page<UserCategoryWeightVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的用户分类兴趣权重列表
     *
     * @param bo 查询条件
     * @return 用户分类兴趣权重列表
     */
    @Override
    public List<UserCategoryWeightVo> queryList(UserCategoryWeightBo bo) {
        LambdaQueryWrapper<UserCategoryWeight> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<UserCategoryWeight> buildQueryWrapper(UserCategoryWeightBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<UserCategoryWeight> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(UserCategoryWeight::getUserId);
        lqw.orderByAsc(UserCategoryWeight::getCatId);
        lqw.eq(bo.getWeight() != null, UserCategoryWeight::getWeight, bo.getWeight());
        lqw.eq(bo.getLastActive() != null, UserCategoryWeight::getLastActive, bo.getLastActive());
        return lqw;
    }

    /**
     * 新增用户分类兴趣权重
     *
     * @param bo 用户分类兴趣权重
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(UserCategoryWeightBo bo) {
        UserCategoryWeight add = MapstructUtils.convert(bo, UserCategoryWeight.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改用户分类兴趣权重
     *
     * @param bo 用户分类兴趣权重
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(UserCategoryWeightBo bo) {
        UserCategoryWeight update = MapstructUtils.convert(bo, UserCategoryWeight.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(UserCategoryWeight entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户分类兴趣权重信息
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

    /**
     * 用户分类特征向量构建
     */
    public Map<Long, Double> getUserCategoryVector(Long userId) {
        // 获取用户行为分类及权重
        List<UserCategoryWeight> weights = baseMapper.selectByUser(userId);

        // 归一化处理
        double total = weights.stream().mapToDouble(UserCategoryWeight::getWeight).sum();

        return weights.stream()
            .collect(Collectors.toMap(
                UserCategoryWeight::getCatId,
                w -> w.getWeight() / total
            ));
    }
}
