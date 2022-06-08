package com.didichuxing.datachannel.arius.admin.biz.template;

import java.util.List;
import java.util.Map;

import com.didichuxing.datachannel.arius.admin.common.bean.common.Result;
import com.didichuxing.datachannel.arius.admin.common.bean.entity.stats.ESIndexStats;
import com.didichuxing.datachannel.arius.admin.common.bean.vo.template.AppIdTemplateAccessCountVO;
import com.didichuxing.datachannel.arius.admin.common.bean.vo.template.TemplateHealthDegreeRecordVO;
import com.didichuxing.datachannel.arius.admin.common.bean.vo.template.TemplateStatsInfoVO;
import com.didichuxing.datachannel.arius.admin.common.bean.vo.template.TemplateValueRecordVO;

/**
 * @author d06679
 * @date 2019-06-24
 */
public interface TemplatePhyStatisManager {
    /**
     * 根据模板id获取最近days天的appid访问统计信息
     * @param logicTemplateId 逻辑索引模板ID
     * @param days 最近多少天
     * @return map
     */
    Result<Map<Integer, Long>> getAccessStatsInfoByTemplateIdAndDays(int logicTemplateId, int days);

    /**
     * 根据模板id获取模板的基本统计信息
     * @param logicTemplateId 模板id
     * @return result
     */
    Result<TemplateStatsInfoVO> getTemplateBaseStatisticalInfoByLogicTemplateId(Long logicTemplateId);

    /**
     * 根据模板Id获取[startDate, endDate]的appid访问统计信息
     * @param logicTemplateId 逻辑索引模板ID
     * @param startDate 开始时刻
     * @param endDate 结束时刻
     * @return list
     */
    Result<List<AppIdTemplateAccessCountVO>> getAccessAppInfos(int logicTemplateId, Long startDate, Long endDate);

    /**
     * 根据模板id获取模板的基本统计信息
     * @param logicTemplateId 模板id
     * @param startDate 开始时刻
     * @param endDate 结束时刻
     * @return list
     */
    Result<List<ESIndexStats>> getIndexStatis(Long logicTemplateId, Long startDate, Long endDate);

    /**
     * 获取某个模板的某段时间的索引健康分统计
     * @param logicTemplateId 模板id
     * @param startDate       查询开始时间，毫秒时间戳
     * @param endDate         毫秒
     * @return list
     */
    Result<List<TemplateHealthDegreeRecordVO>> getHealthDegreeRecordByLogicTemplateId(Long logicTemplateId, Long startDate, Long endDate);

    /**
     * 获取某个模板的某段时间的索引价值
     * @param logicTemplateId 模板id
     * @param startDate       查询开始时间，毫秒时间戳
     * @param endDate         查询结束时间，毫秒时间戳
     * @return list
     */
    Result<List<TemplateValueRecordVO>> getValueRecordByLogicTemplateId(Long logicTemplateId, Long startDate, Long endDate);
}
