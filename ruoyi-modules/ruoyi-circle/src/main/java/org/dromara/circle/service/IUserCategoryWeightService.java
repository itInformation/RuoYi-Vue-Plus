package org.dromara.circle.service;

import org.dromara.circle.domain.vo.UserCategoryWeightVo;
import org.dromara.circle.domain.bo.UserCategoryWeightBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户分类兴趣权重Service接口
 *
 * @author Lion Li
 * @date 2025-03-21
 */
public interface IUserCategoryWeightService {

    /**
     * 查询用户分类兴趣权重
     *
     * @param userId 主键
     * @return 用户分类兴趣权重
     */
    UserCategoryWeightVo queryById(Long userId);

    /**
     * 分页查询用户分类兴趣权重列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户分类兴趣权重分页列表
     */
    TableDataInfo<UserCategoryWeightVo> queryPageList(UserCategoryWeightBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的用户分类兴趣权重列表
     *
     * @param bo 查询条件
     * @return 用户分类兴趣权重列表
     */
    List<UserCategoryWeightVo> queryList(UserCategoryWeightBo bo);

    /**
     * 新增用户分类兴趣权重
     *
     * @param bo 用户分类兴趣权重
     * @return 是否新增成功
     */
    Boolean insertByBo(UserCategoryWeightBo bo);

    /**
     * 修改用户分类兴趣权重
     *
     * @param bo 用户分类兴趣权重
     * @return 是否修改成功
     */
    Boolean updateByBo(UserCategoryWeightBo bo);

    /**
     * 校验并批量删除用户分类兴趣权重信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Map<Long, Double> getUserCategoryVector(Long userId);
}
