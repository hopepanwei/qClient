package top.missz.service;

import top.missz.bot.contact.support.Group;
import top.missz.bot.event.message.GroupMessageEvent;
import top.missz.bot.message.MessageChain;

public interface ReceiveMsgService {
    /**
     * 接收SJK群消息
     * @param group
     * @param msg
     * @return
     */
    String receiveSJKGroupMsg(Group group, GroupMessageEvent groupMessageEvent, MessageChain messageChain, String msg);
}
