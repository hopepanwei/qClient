package top.missz.bot.message.support;

import com.alibaba.fastjson.JSON;
import top.missz.bot.message.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyMessage implements Message {

    private int id;

    private String text;

    private long qq;

    public ReplyMessage(int messageId) {
        this.id = messageId;
    }

    @Override
    public String toString() {
        return "reply[" + id + "]";
    }

    @Override
    public String toMessageString() {
        return String.format("{\"type\":\"%s\",\"data\":%s}", "reply", JSON.toJSONString(this));
    }

}
