package com.didichuxing.datachannel.arius.admin.common.bean.vo.template;

import java.util.List;

import com.didichuxing.datachannel.arius.admin.common.bean.common.Label;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author d06679
 * @date 2019/3/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class IndexTemplateLogicWithLabelVO extends OpLogicTemplateVO {

    private List<Label> labels;

}