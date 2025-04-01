package org.dromara.system.service;

import cn.hutool.json.JSONObject;
import org.dromara.system.domain.vo.SysRealnameAuthVo;
import org.dromara.system.domain.bo.SysRealnameAuthBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author Lion Li
 * @date 2025-04-01
 */
public interface ISysRealnameAuthService {

    /**
     * 查询【请填写功能名称】
     *
     * @param authId 主键
     * @return 【请填写功能名称】
     */
    SysRealnameAuthVo queryById(Long authId);

    /**
     * 分页查询【请填写功能名称】列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 【请填写功能名称】分页列表
     */
    TableDataInfo<SysRealnameAuthVo> queryPageList(SysRealnameAuthBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的【请填写功能名称】列表
     *
     * @param bo 查询条件
     * @return 【请填写功能名称】列表
     */
    List<SysRealnameAuthVo> queryList(SysRealnameAuthBo bo);

    /**
     * 新增【请填写功能名称】
     *
     * @param bo 【请填写功能名称】
     * @return 是否新增成功
     */
    Boolean insertByBo(SysRealnameAuthBo bo);

    /**
     * 修改【请填写功能名称】
     *
     * @param bo 【请填写功能名称】
     * @return 是否修改成功
     */
    Boolean updateByBo(SysRealnameAuthBo bo);

    /**
     * 校验并批量删除【请填写功能名称】信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 开启认证
     */
    String startIdentity(Long userId, String orderNo, String optionDomain, String callBackUrl, String h5FaceId);

    /**
     * 认证完成回调
     */
    void identityCallback(String code,String orderNo,String h5faceId,String newSign);

    /**
     * 查询认证结果
     */
    JSONObject queryIdentityResult(String orderNo);

    String uploadIdentityInfo(Long userId);
}
