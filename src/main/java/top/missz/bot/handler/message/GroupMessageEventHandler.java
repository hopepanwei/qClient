package top.missz.bot.handler.message;

import com.alibaba.fastjson.JSONObject;
import top.missz.bot.annotation.GroupMessageHandler;
import top.missz.bot.contact.support.Group;
import top.missz.bot.core.BotApplication;
import top.missz.bot.core.HandlerMethod;
import top.missz.bot.event.message.GroupMessageEvent;
import top.missz.bot.handler.EventHandler;
import top.missz.bot.message.Message;
import top.missz.bot.message.MessageChain;
import top.missz.bot.message.MessageTypeHandle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupMessageEventHandler implements EventHandler {

    @Override
    public void handle(JSONObject jsonObject) {
        if (!GroupMessageEvent.isSupport(jsonObject)) {
            return;
        }
        GroupMessageEvent groupMessageEvent = jsonObject.toJavaObject(GroupMessageEvent.class);
        MessageChain messageChain = new MessageChain();
        for (int i = 0; i < groupMessageEvent.getMessage().size(); i++) {
            messageChain.add(MessageTypeHandle.getMessage(groupMessageEvent.getMessage().getJSONObject(i)));
        }
        log.debug(messageChain.toMessageString());
        List<HandlerMethod> handlerMethodList = BotApplication.getHandlerMethodList("bot");
        if (handlerMethodList == null || handlerMethodList.isEmpty()) {
            return;
        }
        Set<HandlerMethod> handlerMethodSet = handlerMethodList.stream().filter(handlerMethod -> {
            if (!handlerMethod.getMethod().isAnnotationPresent(GroupMessageHandler.class)) {
                return false;
            }
            GroupMessageHandler groupMessageHandler = handlerMethod.getMethod().getAnnotation(GroupMessageHandler.class);
            if (groupMessageHandler.groupId() != 0 && groupMessageHandler.groupId() != groupMessageEvent.getGroupId()) {
                return false;
            }
            if (groupMessageHandler.senderId() != 0 && groupMessageHandler.senderId() != groupMessageEvent.getUserId()) {
                return false;
            }
            return groupMessageHandler.regex().equals("none") || messageChain.toString().matches(groupMessageHandler.regex());
        }).collect(Collectors.toSet());
        List<Object> resultList = BotApplication.handleMethod(handlerMethodSet, groupMessageEvent, messageChain);
        for (Object result : resultList) {
            if (result instanceof Message) {
                new Group(groupMessageEvent.getGroupId()).sendMessage((Message) result);
            }
            if (result instanceof MessageChain) {
                new Group(groupMessageEvent.getGroupId()).sendMessage((MessageChain) result);
            }
        }
    }

}
