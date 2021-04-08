package top.missz.bot.handler.message;

import com.alibaba.fastjson.JSONObject;
import top.missz.bot.annotation.FriendMessageHandler;
import top.missz.bot.annotation.TempMessageHandler;
import top.missz.bot.contact.support.TempFriend;
import top.missz.bot.core.BotApplication;
import top.missz.bot.core.HandlerMethod;
import top.missz.bot.event.message.PrivateMessageEvent;
import top.missz.bot.handler.EventHandler;
import top.missz.bot.message.Message;
import top.missz.bot.message.MessageChain;
import top.missz.bot.message.MessageTypeHandle;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class PrivateMessageEventHandler implements EventHandler {
    @Override
    public void handle(JSONObject jsonObject) {
        if (!PrivateMessageEvent.isSupport(jsonObject)) {
            return;
        }
        PrivateMessageEvent privateMessageEvent = jsonObject.toJavaObject(PrivateMessageEvent.class);
        MessageChain messageChain = new MessageChain();
        for (int i = 0; i < privateMessageEvent.getMessage().size(); i++) {
            messageChain.add(MessageTypeHandle.getMessage(privateMessageEvent.getMessage().getJSONObject(i)));
        }
        log.debug(messageChain.toMessageString());
        Set<HandlerMethod> handlerMethodSet;
        if ("group".equals(privateMessageEvent.getSubType())) {
            List<HandlerMethod> handlerMethodList = BotApplication.getHandlerMethodList("bot");
            if (handlerMethodList == null || handlerMethodList.isEmpty()) {
                return;
            }
            handlerMethodSet = handlerMethodList.stream().filter(handlerMethod -> {
                if (!handlerMethod.getMethod().isAnnotationPresent(TempMessageHandler.class)) {
                    return false;
                }
                TempMessageHandler tempMessageHandler = handlerMethod.getMethod().getAnnotation(TempMessageHandler.class);
                if (tempMessageHandler.senderId() != 0 && tempMessageHandler.senderId() != privateMessageEvent.getUserId()) {
                    return false;
                }
                return tempMessageHandler.regex().equals("none") || messageChain.toString().matches(tempMessageHandler.regex());
            }).collect(Collectors.toSet());
        } else if ("friend".equals(privateMessageEvent.getSubType())) {
            List<HandlerMethod> handlerMethodList = BotApplication.getHandlerMethodList("bot");
            if (handlerMethodList == null || handlerMethodList.isEmpty()) {
                return;
            }
            handlerMethodSet = handlerMethodList.stream().filter(handlerMethod -> {
                if (!handlerMethod.getMethod().isAnnotationPresent(FriendMessageHandler.class)) {
                    return false;
                }
                FriendMessageHandler friendMessageHandler = handlerMethod.getMethod().getAnnotation(FriendMessageHandler.class);
                if (friendMessageHandler.senderId() != 0 && friendMessageHandler.senderId() != privateMessageEvent.getUserId()) {
                    return false;
                }
                return friendMessageHandler.regex().equals("none") || messageChain.toString().matches(friendMessageHandler.regex());
            }).collect(Collectors.toSet());
        } else {
            return;
        }
        List<Object> resultList = BotApplication.handleMethod(handlerMethodSet, privateMessageEvent, messageChain);
        for (Object result : resultList) {
            if (result instanceof Message) {
                new TempFriend(privateMessageEvent.getUserId()).sendMessage((Message) result);
            }
            if (result instanceof MessageChain) {
                new TempFriend(privateMessageEvent.getUserId()).sendMessage((MessageChain) result);
            }
        }
    }
}
