package top.missz.bot.core;

import top.missz.bot.config.BotConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Sharable
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private final BotConfig botConfig;
    private final BotDispatcher botDispatcher;

    private WebSocketClientHandshaker webSocketClientHandshaker;

    public WebSocketClientHandshaker getWebSocketClientHandshaker() {
        return webSocketClientHandshaker;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws URISyntaxException {
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + this.botConfig.getAccessToken());
        this.webSocketClientHandshaker = WebSocketClientHandshakerFactory
                .newHandshaker(new URI(String.format("ws://%s:%d", this.botConfig.getWebsocketUrl(), this.botConfig.getWebsocketPort())), WebSocketVersion.V13, null, false, httpHeaders);;
        this.webSocketClientHandshaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        BotApplication.connection(this.botConfig.getWebsocketUrl(), this.botConfig.getWebsocketPort());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        Channel ch = ctx.channel();
        if (!this.webSocketClientHandshaker.isHandshakeComplete()) {
            try {
                this.webSocketClientHandshaker.finishHandshake(ch, (FullHttpResponse) msg);
                log.info("Go-cqhttp connected!");
            } catch (WebSocketHandshakeException e) {
                log.error("Go-cqhttp failed to connect, Token authentication failed!");
                BotApplication.getApplicationContext().close();
                Runtime.getRuntime().exit(0);
            }
            return;
        }
        if (msg instanceof WebSocketFrame) {
            WebSocketFrame frame = (WebSocketFrame) msg;
            if (frame instanceof TextWebSocketFrame) {
                TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
                this.botDispatcher.handle(textFrame.text());
            } else if (frame instanceof CloseWebSocketFrame) {
                ch.close();
            }
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(new PingWebSocketFrame());
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }
}
