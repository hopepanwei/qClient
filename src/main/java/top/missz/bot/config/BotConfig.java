package top.missz.bot.config;

import lombok.Data;

@Data
public class BotConfig {

    private String configKey = "bot";

    private String websocketUrl;

    private int websocketPort;

    private String accessToken;

}
