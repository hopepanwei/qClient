package top.missz.bot.injector.support;

import top.missz.bot.event.message.GroupMessageEvent;
import top.missz.bot.event.message.MessageEvent;
import top.missz.bot.injector.MessageObjectInjector;
import top.missz.bot.message.MessageChain;

public class GroupMessageEventInjector implements MessageObjectInjector<GroupMessageEvent> {
    @Override
    public Class<GroupMessageEvent> getType() {
        return GroupMessageEvent.class;
    }

    @Override
    public GroupMessageEvent getObject(MessageEvent messageEvent, MessageChain messageChain) {
        if (!(messageEvent instanceof GroupMessageEvent)) {
            return null;
        }
        return (GroupMessageEvent) messageEvent;
    }
}
