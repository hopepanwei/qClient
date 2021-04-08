package top.missz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.missz.bot.annotation.GroupMessageHandler;
import top.missz.bot.contact.support.Group;
import top.missz.bot.event.message.GroupMessageEvent;
import top.missz.bot.message.MessageChain;
import top.missz.service.ReceiveMsgService;

@Service
public class ReceiveMsgServiceImpl implements ReceiveMsgService {
    private static final Logger log = LoggerFactory.getLogger(ReceiveMsgServiceImpl.class);

    @Override
    @GroupMessageHandler(groupId = 682702579)
    public String receiveSJKGroupMsg(Group group, GroupMessageEvent groupMessageEvent, MessageChain messageChain, String msg) {
        String name;
        if (StringUtils.hasLength(groupMessageEvent.getSender().getString("card"))){
            name = groupMessageEvent.getSender().getString("card");
        }else{
            name = groupMessageEvent.getSender().getString("nickname");
        }
        System.out.println("name:" + name + ",message:" + groupMessageEvent.getMessage());
        return msg;
    }
}
