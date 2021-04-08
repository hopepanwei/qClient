package top.missz.bot.injector.support;

import top.missz.bot.event.message.MessageEvent;
import top.missz.bot.injector.MessageObjectInjector;
import top.missz.bot.message.MessageChain;

public class MessageIdIntInjector implements MessageObjectInjector<Integer> {
    @Override
    public Class<Integer> getType() {
        return int.class;
    }

    @Override
    public Integer getObject(MessageEvent messageEvent, MessageChain messageChain) {
        return messageEvent.getMessageId();
    }
}
