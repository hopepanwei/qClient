package top.missz.bot.handler.meta;

import com.alibaba.fastjson.JSONObject;
import top.missz.bot.core.BotApplication;
import top.missz.bot.event.meta.HeartbeatEvent;
import top.missz.bot.handler.EventHandler;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HeartbeatEventHandler implements EventHandler {

    @Override
    public void handle(JSONObject jsonObject) {
        if (!HeartbeatEvent.isSupport(jsonObject)) {
            return;
        }
        BotApplication.getChannel().writeAndFlush(new PingWebSocketFrame());
        HeartbeatEvent heartbeatEvent = jsonObject.toJavaObject(HeartbeatEvent.class);
        log.debug("heartbeat-event: " + heartbeatEvent);
    }
}
