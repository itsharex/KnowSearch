package com.didichuxing.datachannel.arius.admin.common.bean.po.project;

import com.didichuxing.datachannel.arius.admin.common.bean.po.BasePO;
import com.didichuxing.datachannel.arius.admin.common.constant.project.ProjectTemplateAuthEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author d06679
 * @date 2019/4/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTemplateAuthPO extends BasePO {

    /**
     * 主键
     */
    private Long    id;

    /**
     * project id
     */
    private Integer projectId;

    /**
     * 模板ID
     */
    private Integer templateId;

    /**
     * 权限类型  读写  读
     * @see ProjectTemplateAuthEnum
     */
    private Integer type;

    /**
     * 责任人列表
     */
    @Deprecated
    private String  responsible;

}