package top.missz.bot.injector.support;

import top.missz.bot.event.message.MessageEvent;
import top.missz.bot.injector.MessageObjectInjector;
import top.missz.bot.message.MessageChain;

public class MessageChainInjector implements MessageObjectInjector<MessageChain> {
    @Override
    public Class<MessageChain> getType() {
        return MessageChain.class;
    }

    @Override
    public MessageChain getObject(MessageEvent messageEvent, MessageChain messageChain) {
        return messageChain;
    }
}
