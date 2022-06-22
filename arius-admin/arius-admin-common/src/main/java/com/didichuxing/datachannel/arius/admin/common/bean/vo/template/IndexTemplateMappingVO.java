package com.didichuxing.datachannel.arius.admin.common.bean.vo.template;

import com.didichuxing.datachannel.arius.admin.common.bean.vo.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class IndexTemplateMappingVO extends BaseVO {
    private int            id;

    /**
     * 集群名称
     */
    private String         clusterName;

    /**
     * 逻辑模板名称
     */
    private String         templateName;

    /**
     * mapping信息
     */
    private String         templateMapping;
}