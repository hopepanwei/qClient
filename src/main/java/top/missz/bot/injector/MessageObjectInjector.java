package top.missz.bot.injector;

import top.missz.bot.event.message.MessageEvent;
import top.missz.bot.message.MessageChain;

public interface MessageObjectInjector<T> {

    Class<T> getType();

    T getObject(MessageEvent messageEvent, MessageChain messageChain);

}