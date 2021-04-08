package top.missz.bot.message.support;

import com.alibaba.fastjson.JSON;
import top.missz.bot.message.Message;
import lombok.Data;

@Data
public class ShareMessage implements Message {

    private String url;

    private String title;

    private String content;

    private String image;

    @Override
    public String toString() {
        return "share[" + title + "]";
    }

    @Override
    public String toMessageString() {
        return String.format("{\"type\":\"%s\",\"data\":%s}", "share", JSON.toJSONString(this));
    }

}
