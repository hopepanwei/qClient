package top.missz.bot.contact;

import top.missz.bot.message.Message;
import top.missz.bot.message.MessageChain;

public interface Contact {

    default int sendMessage(Message message) {
        MessageChain messageChain = new MessageChain();
        messageChain.add(message);
        return this.sendMessage(messageChain);
    }

    int sendMessage(MessageChain messageChain);

}
