package com.xyzq.kid.wechat.action;

import com.xyzq.kid.logic.record.service.RecordService;
import com.xyzq.simpson.base.json.JSONObject;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import com.xyzq.simpson.maggie.framework.action.core.IAction;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 */
@MaggieAction(path = "kid/wechat/recordQuery")
public class RecordQueryAction implements IAction {
    /**
     * Action中只支持Autowired注解引入SpringBean
     */
    @Autowired
    private RecordService recordService;


    /**
     * 动作执行
     *
     * @param visitor 访问者
     * @param context 请求上下文
     * @return 下一步动作，包括后缀名，null表示结束
     */
    @Override
    public String execute(Visitor visitor, Context context) throws Exception {
        String serialNumber = String.valueOf(context.parameter("serialNumber"));
        String purchased =String.valueOf(context.parameter("purchased"));
        context.set("msg", "查询成功!");
        context.set("code", "0");
        context.set("data", JSONObject.convertFromObject(recordService.findBy(serialNumber,purchased)));
        return "success.json";
    }

}
