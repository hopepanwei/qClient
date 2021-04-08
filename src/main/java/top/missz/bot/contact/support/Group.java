package top.missz.bot.contact.support;

import top.missz.bot.api.ApiResult;
import top.missz.bot.api.support.GroupBan;
import top.missz.bot.api.support.SendGroupMsg;
import top.missz.bot.api.support.SendPrivateMsg;
import top.missz.bot.contact.Contact;
import top.missz.bot.core.BotApplication;
import top.missz.bot.exception.BotException;
import top.missz.bot.message.Message;
import top.missz.bot.message.MessageChain;

public class Group implements Contact {

    private final long groupId;

    public Group(long groupId) {
        this.groupId = groupId;
    }

    public long getGroupId() {
        return groupId;
    }

    @Override
    public int sendMessage(MessageChain messageChain) {
        ApiResult apiResult = BotApplication.invokeApi(SendGroupMsg.buildApi(this.groupId, messageChain));
        if (apiResult == null || !"ok".equals(apiResult.getStatus())) {
            throw new BotException("调用api出错: " + apiResult);
        }
        return apiResult.getData().getIntValue("message_id");
    }

    public int sendTempMessage(long userId, Message message) {
        MessageChain messageChain = new MessageChain();
        messageChain.add(message);
        return this.sendTempMessage(userId, messageChain);
    }

    public int sendTempMessage(long userId, MessageChain messageChain) {
        ApiResult apiResult = BotApplication.invokeApi(SendPrivateMsg.buildApi(userId, messageChain));
        if (apiResult == null || !"ok".equals(apiResult.getStatus())) {
            throw new BotException("调用api出错: " + apiResult);
        }
        return apiResult.getData().getIntValue("message_id");
    }

    public void groupBan() {
        ApiResult apiResult = BotApplication.invokeApi(GroupBan.buildApi(this.groupId, true));
        if (apiResult == null || !"ok".equals(apiResult.getStatus())) {
            throw new BotException("调用api出错: " + apiResult);
        }
    }

    public void groupPardon() {
        ApiResult apiResult = BotApplication.invokeApi(GroupBan.buildApi(this.groupId, false));
        if (apiResult == null || !"ok".equals(apiResult.getStatus())) {
            throw new BotException("调用api出错: " + apiResult);
        }
    }

}
