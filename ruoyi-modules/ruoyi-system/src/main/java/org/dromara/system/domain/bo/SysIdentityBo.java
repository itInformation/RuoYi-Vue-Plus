package org.dromara.system.domain.bo;

import lombok.Data;

/**
 * 业务对象 发起实名认证
 *
 * @author Lion Li
 * @date 2025-04-01
 */
@Data
public class SysIdentityBo {

    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 上传身份信息接口中获取的h5faceid
     */
    private String h5FaceId;
    /**
     * 上传身份信息接口中获取的h5faceid
     */

    private String optimalDomain;

}
