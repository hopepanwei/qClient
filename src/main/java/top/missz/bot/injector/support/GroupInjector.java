package top.missz.bot.injector.support;

import top.missz.bot.contact.support.Group;
import top.missz.bot.event.message.GroupMessageEvent;
import top.missz.bot.event.message.MessageEvent;
import top.missz.bot.injector.MessageObjectInjector;
import top.missz.bot.message.MessageChain;

public class GroupInjector implements MessageObjectInjector<Group> {
    @Override
    public Class<Group> getType() {
        return Group.class;
    }

    @Override
    public Group getObject(MessageEvent messageEvent, MessageChain messageChain) {
        if (!(messageEvent instanceof GroupMessageEvent)) {
            return null;
        }
        return new Group(((GroupMessageEvent) messageEvent).getGroupId());
    }
}
