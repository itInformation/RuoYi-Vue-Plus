package org.dromara.system.service;

import org.dromara.system.domain.vo.MemberTypeVo;
import org.dromara.system.domain.bo.MemberTypeBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 会员类型;Service接口
 *
 * @author Lion Li
 * @date 2025-04-25
 */
public interface IMemberTypeService {

    /**
     * 查询会员类型;
     *
     * @param typeId 主键
     * @return 会员类型;
     */
    MemberTypeVo queryById(Long typeId);

    /**
     * 分页查询会员类型;列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员类型;分页列表
     */
    TableDataInfo<MemberTypeVo> queryPageList(MemberTypeBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的会员类型;列表
     *
     * @param bo 查询条件
     * @return 会员类型;列表
     */
    List<MemberTypeVo> queryList(MemberTypeBo bo);

    /**
     * 新增会员类型;
     *
     * @param bo 会员类型;
     * @return 是否新增成功
     */
    Boolean insertByBo(MemberTypeBo bo);

    /**
     * 修改会员类型;
     *
     * @param bo 会员类型;
     * @return 是否修改成功
     */
    Boolean updateByBo(MemberTypeBo bo);

    /**
     * 校验并批量删除会员类型;信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
