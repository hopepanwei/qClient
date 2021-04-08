package top.missz.bot.contact.support;

import top.missz.bot.api.ApiResult;
import top.missz.bot.api.support.SendPrivateMsg;
import top.missz.bot.contact.Contact;
import top.missz.bot.core.BotApplication;
import top.missz.bot.exception.BotException;
import top.missz.bot.message.MessageChain;

public class TempFriend implements Contact {

    private final long userId;

    public TempFriend(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return this.userId;
    }

    @Override
    public int sendMessage(MessageChain messageChain) {
        SendPrivateMsg baseApi = SendPrivateMsg.buildApi(this.userId, messageChain);
        ApiResult apiResult = BotApplication.invokeApi(baseApi);
        if (apiResult == null || !"ok".equals(apiResult.getStatus())) {
            throw new BotException("调用api出错: " + apiResult);
        }
        return apiResult.getData().getIntValue("message_id");
    }

}
