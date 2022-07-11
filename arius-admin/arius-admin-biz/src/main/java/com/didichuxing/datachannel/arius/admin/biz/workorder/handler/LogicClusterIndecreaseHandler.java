package com.didichuxing.datachannel.arius.admin.biz.workorder.handler;

import com.alibaba.fastjson.JSON;
import com.didichuxing.datachannel.arius.admin.biz.cluster.ClusterNodeManager;
import com.didichuxing.datachannel.arius.admin.biz.workorder.BaseWorkOrderHandler;
import com.didichuxing.datachannel.arius.admin.biz.workorder.content.LogicClusterIndecreaseContent;
import com.didichuxing.datachannel.arius.admin.common.bean.common.OperateRecord;
import com.didichuxing.datachannel.arius.admin.common.bean.common.Result;
import com.didichuxing.datachannel.arius.admin.common.bean.dto.cluster.ClusterRegionWithNodeInfoDTO;
import com.didichuxing.datachannel.arius.admin.common.bean.entity.cluster.ClusterLogic;
import com.didichuxing.datachannel.arius.admin.common.bean.entity.workorder.WorkOrder;
import com.didichuxing.datachannel.arius.admin.common.bean.entity.workorder.detail.AbstractOrderDetail;
import com.didichuxing.datachannel.arius.admin.common.bean.entity.workorder.detail.LogicClusterIndecreaseOrderDetail;
import com.didichuxing.datachannel.arius.admin.common.bean.po.order.WorkOrderPO;
import com.didichuxing.datachannel.arius.admin.common.constant.operaterecord.OperateTypeEnum;
import com.didichuxing.datachannel.arius.admin.common.constant.operaterecord.TriggerWayEnum;
import com.didichuxing.datachannel.arius.admin.common.constant.project.ProjectClusterLogicAuthEnum;
import com.didichuxing.datachannel.arius.admin.common.constant.result.ResultType;
import com.didichuxing.datachannel.arius.admin.common.constant.workorder.WorkOrderTypeEnum;
import com.didichuxing.datachannel.arius.admin.common.exception.AdminOperateException;
import com.didichuxing.datachannel.arius.admin.common.util.AriusObjUtils;
import com.didichuxing.datachannel.arius.admin.common.util.ConvertUtil;
import com.didichuxing.datachannel.arius.admin.core.service.cluster.logic.ClusterLogicService;
import com.didichuxing.datachannel.arius.admin.core.service.project.ProjectClusterLogicAuthService;
import com.didiglobal.logi.security.common.vo.user.UserBriefVO;
import com.didiglobal.logi.security.service.ProjectService;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author d06679
 * @date 2019/4/29
 */
@Service("logicClusterIndecreaseHandler")
public class LogicClusterIndecreaseHandler extends BaseWorkOrderHandler {

    @Autowired
    private ClusterLogicService clusterLogicService;

    @Autowired
    private ProjectClusterLogicAuthService projectClusterLogicAuthService;

    @Autowired
    private ClusterNodeManager clusterNodeManager;
    @Autowired
    private ProjectService projectService;

    /**
     * 工单是否自动审批
     *
     * @param workOrder 工单类型
     * @return result
     */
    @Override
    public boolean canAutoReview(WorkOrder workOrder) {
        return false;
    }

    @Override
    public AbstractOrderDetail getOrderDetail(String extensions) {
        LogicClusterIndecreaseContent content = JSON.parseObject(extensions, LogicClusterIndecreaseContent.class);

        return ConvertUtil.obj2Obj(content, LogicClusterIndecreaseOrderDetail.class);
    }

    @Override
    public List<UserBriefVO> getApproverList(AbstractOrderDetail detail) {
        return getOPList();
    }

    @Override
    public Result<Void> checkAuthority(WorkOrderPO orderPO, String userName) {
        if (isOP(userName)) {
            return Result.buildSucc();
        }
        return Result.buildFail(ResultType.OPERATE_FORBIDDEN_ERROR.getMessage());
    }

    /**************************************** protected method ******************************************/

    /**
     * 验证用户提供的参数
     *
     * @param workOrder 工单
     * @return result
     */
    @Override
    protected Result<Void> validateConsoleParam(WorkOrder workOrder) {
        LogicClusterIndecreaseContent content = ConvertUtil.obj2ObjByJSON(workOrder.getContentObj(),
            LogicClusterIndecreaseContent.class);

        if (AriusObjUtils.isNull(content.getLogicClusterId())) {
            return Result.buildParamIllegal("集群id为空");
        }

        ClusterLogic clusterLogic = clusterLogicService.getClusterLogicById(content.getLogicClusterId());
        if (clusterLogic == null) {
            return Result.buildParamIllegal("集群不存在");
        }

        return Result.buildSucc();
    }

    @Override
    protected String getTitle(WorkOrder workOrder) {
        LogicClusterIndecreaseContent content = ConvertUtil.obj2ObjByJSON(workOrder.getContentObj(),
            LogicClusterIndecreaseContent.class);

        WorkOrderTypeEnum workOrderTypeEnum = WorkOrderTypeEnum.valueOfName(workOrder.getType());
        if (workOrderTypeEnum == null) {
            return "";
        }
        return content.getLogicClusterName() + workOrderTypeEnum.getMessage();
    }

    /**
     * 验证用户是否有该工单权限
     * 要求只有集群所属的projectId才能操作
     *
     * @param workOrder 工单内容
     * @return result
     */
    @Override
    protected Result<Void> validateConsoleAuth(WorkOrder workOrder) {
        LogicClusterIndecreaseContent content = ConvertUtil.obj2ObjByJSON(workOrder.getContentObj(),
            LogicClusterIndecreaseContent.class);

        ProjectClusterLogicAuthEnum logicClusterAuthEnum = projectClusterLogicAuthService
            .getLogicClusterAuthEnum(workOrder.getSubmitorProjectId(), content.getLogicClusterId());

        switch (logicClusterAuthEnum) {
            case ALL:
            case OWN:
                return Result.buildSucc();
            case ACCESS:
                return Result.buildParamIllegal("您的projectId无该集群的扩缩容权限");
            case NO_PERMISSIONS:
            default:
                return Result.buildParamIllegal("您的projectId无该集群的相关权限");
        }
    }

    /**
     * 验证平台参数
     *
     * @param workOrder 工单内容
     * @return result
     */
    @Override
    protected Result<Void> validateParam(WorkOrder workOrder) {
        return Result.buildSucc();
    }

    /**
     * 处理工单
     *
     * @param workOrder 工单
     * @return result
     */
    @Override
    protected Result<Void> doProcessAgree(WorkOrder workOrder, String approver) throws AdminOperateException {
        LogicClusterIndecreaseContent content = ConvertUtil.obj2ObjByJSON(workOrder.getContentObj(),
            LogicClusterIndecreaseContent.class);

        List<ClusterRegionWithNodeInfoDTO> clusterRegionWithNodeInfoDTOList = content.getRegionWithNodeInfo();

        Result<Boolean> regionEditResult = clusterNodeManager.editMultiNode2Region(clusterRegionWithNodeInfoDTOList, approver,
                workOrder.getSubmitorProjectId());
        if (regionEditResult.failed()) { return Result.buildFrom(regionEditResult);}
         operateRecordService.save(new OperateRecord.Builder()
                         .bizId(content.getLogicClusterId())
                         .operationTypeEnum(OperateTypeEnum.MY_CLUSTER_CAPACITY)
                         .triggerWayEnum(TriggerWayEnum.MANUAL_TRIGGER)
                         .content(String.format("%s申请逻辑集群：%s的扩容操作，内容如下：%s",workOrder.getSubmitor(),
                                 content.getLogicClusterName() , content))
                         .userOperation(approver)
                         .project(projectService.getProjectBriefByProjectId(workOrder.getSubmitorProjectId()))
                 .build());
        
        List<String> administrators = getOPList().stream().map(UserBriefVO::getUserName).collect(
                Collectors.toList());
        return Result.buildSuccWithMsg(String.format("请联系管理员【%s】进行后续操作", administrators.get(new Random().nextInt(administrators.size()))));
    }
}