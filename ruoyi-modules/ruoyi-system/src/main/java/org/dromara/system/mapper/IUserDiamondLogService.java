package org.dromara.system.mapper;

import org.dromara.system.domain.vo.UserDiamondLogVo;
import org.dromara.system.domain.bo.UserDiamondLogBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户钻石流水Service接口
 *
 * @author Lion Li
 * @date 2025-04-18
 */
public interface IUserDiamondLogService {

    /**
     * 查询用户钻石流水
     *
     * @param id 主键
     * @return 用户钻石流水
     */
    UserDiamondLogVo queryById(Long id);

    /**
     * 分页查询用户钻石流水列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户钻石流水分页列表
     */
    TableDataInfo<UserDiamondLogVo> queryPageList(UserDiamondLogBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的用户钻石流水列表
     *
     * @param bo 查询条件
     * @return 用户钻石流水列表
     */
    List<UserDiamondLogVo> queryList(UserDiamondLogBo bo);

    /**
     * 新增用户钻石流水
     *
     * @param bo 用户钻石流水
     * @return 是否新增成功
     */
    Boolean insertByBo(UserDiamondLogBo bo);

    /**
     * 修改用户钻石流水
     *
     * @param bo 用户钻石流水
     * @return 是否修改成功
     */
    Boolean updateByBo(UserDiamondLogBo bo);

    /**
     * 校验并批量删除用户钻石流水信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
