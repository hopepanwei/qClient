package top.missz.service.impl;

import org.springframework.stereotype.Service;
import top.missz.bot.annotation.GroupMessageHandler;
import top.missz.bot.contact.support.Group;
import top.missz.service.SendMsgService;

@Service
public class SendMsgServiceImpl implements SendMsgService {
    @Override
    @GroupMessageHandler(groupId = 682702579)
    public void sendSJKGroupMsg(Group group) {
    }
}
