
package com.didi.arius.gateway.remote.response;

import com.google.gson.annotations.Expose;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SlaveInfoResponse {

    @Expose
    private List<Integer> accessProjects;
    @Expose
    private String        cluster;
    @Expose
    private Long templateId;
    @Expose
    private String templateName;
    @Expose
    private String topic;

    /**
     * 用于索引多type改造   是否启用索引名称映射 0 禁用 1 启用
     */
    @Expose
    private Boolean mappingIndexNameEnable;

    /**
     * 多type索引type名称到单type索引模板名称的映射
     */
    @Expose
    private Map<String/*typeName*/,String/*templateName*/> typeIndexMapping;

}