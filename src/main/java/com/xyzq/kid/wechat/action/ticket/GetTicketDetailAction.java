package com.xyzq.kid.wechat.action.ticket;

import com.google.gson.Gson;
import com.xyzq.kid.logic.ticket.entity.TicketEntity;
import com.xyzq.kid.logic.ticket.service.TicketService;
import com.xyzq.kid.logic.user.entity.UserEntity;
import com.xyzq.kid.wechat.action.member.WechatUserAjaxAction;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 增票
 */
@MaggieAction(path = "kid/wechat/getTicketDetail")
public class GetTicketDetailAction extends WechatUserAjaxAction {
    /**
     * Action中只支持Autowired注解引入SpringBean
     */
    @Autowired
    private TicketService ticketService;

    /**
     * 日志对象
     */
    public static Logger logger = LoggerFactory.getLogger(GetTicketDetailAction.class);

    Gson gson=new Gson();
    /**
     * 动作执行
     *
     * @param visitor 访问者
     * @param context 请求上下文
     * @return 下一步动作，包括后缀名，null表示结束
     */
    @Override
    public String doExecute(Visitor visitor, Context context) throws Exception {
        String serialNumber = (String) context.parameter("serialNumber");
        logger.info("[kid/wechat/getTicketDetail]-in[serialNumber:" + serialNumber + "]");

        Map<String,Object> map=new HashMap<>();
        TicketEntity ticket = ticketService.getTicketsInfoBySerialno(serialNumber);
        if(null != ticket) {
            int count = ticketService.queryTickethandselCount(ticket.id);
            if(count == 0 && ticket.type == TicketEntity.TICKET_TYPE_GROUP) {
                map.put("isGive", true);
            } else {
                map.put("isGive", false);
            }
            map.put("id",ticket.id);
            map.put("serialNumber", ticket.serialNumber);
            map.put("type", ticket.type);
            map.put("price", ticket.price);
            map.put("purchaser", ticket.payeropenid);
            map.put("owner", ticket.telephone);
            map.put("expire", ticket.expire);
            map.put("status", ticket.status);

            context.set("data", gson.toJson(map));
            return "success.json";
        }

        context.set("msg", "票不存在！");
        return "success.json";
    }
}
