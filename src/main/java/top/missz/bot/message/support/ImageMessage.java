package top.missz.bot.message.support;

import com.alibaba.fastjson.JSON;
import top.missz.bot.message.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageMessage implements Message {

    private String file;

    private String type;

    private String url;

    private Integer id;

    public ImageMessage(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "image[/images/" + file + "]";
    }

    @Override
    public String toMessageString() {
        return String.format("{\"type\":\"%s\",\"data\":%s}", "image", JSON.toJSONString(this));
    }

}
