package top.missz.bot.injector.support;

import top.missz.bot.contact.support.TempFriend;
import top.missz.bot.event.message.MessageEvent;
import top.missz.bot.event.message.PrivateMessageEvent;
import top.missz.bot.injector.MessageObjectInjector;
import top.missz.bot.message.MessageChain;

public class TempFriendInjector implements MessageObjectInjector<TempFriend> {
    @Override
    public Class<TempFriend> getType() {
        return TempFriend.class;
    }

    @Override
    public TempFriend getObject(MessageEvent messageEvent, MessageChain messageChain) {
        if (!(messageEvent instanceof PrivateMessageEvent)) {
            return null;
        }
        if (!("group".equals(((PrivateMessageEvent) messageEvent).getSubType()))) {
            return null;
        }
        return new TempFriend(messageEvent.getUserId());
    }
}
