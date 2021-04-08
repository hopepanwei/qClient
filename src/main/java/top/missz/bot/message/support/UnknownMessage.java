package top.missz.bot.message.support;

import top.missz.bot.message.Message;
import lombok.Data;

@Data
public class UnknownMessage implements Message {

    private String json;

    @Override
    public String toString() {
        return "json[" + json + "]";
    }

    @Override
    public String toMessageString() {
        return this.json;
    }
}
