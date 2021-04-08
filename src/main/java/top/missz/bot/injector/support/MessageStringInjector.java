package top.missz.bot.injector.support;

import top.missz.bot.event.message.MessageEvent;
import top.missz.bot.injector.MessageObjectInjector;
import top.missz.bot.message.MessageChain;

public class MessageStringInjector implements MessageObjectInjector<String> {
    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public String getObject(MessageEvent messageEvent, MessageChain messageChain) {
        return messageChain.toString();
    }
}
