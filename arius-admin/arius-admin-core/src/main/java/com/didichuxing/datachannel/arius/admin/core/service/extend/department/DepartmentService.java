package com.didichuxing.datachannel.arius.admin.core.service.extend.department;

import com.didichuxing.datachannel.arius.admin.common.bean.common.Result;

/**
 * @author linyunan
 * @date 2021-04-26
 */
public interface DepartmentService {
    /**
     * 根据Type获取部门列表
     */
    Result<String> listDepartmentsByType();
}
