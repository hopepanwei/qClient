package top.missz.bot.support;

import top.missz.bot.core.*;
import top.missz.bot.handler.message.GroupMessageEventHandler;
import top.missz.bot.handler.message.PrivateMessageEventHandler;
import top.missz.bot.handler.meta.HeartbeatEventHandler;
import top.missz.bot.injector.support.*;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zhuan
 */
public class BotApplicationRegistrar implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                BotApplication.class.getName(),
                WebSocketClient.class.getName(),
                WebSocketHandler.class.getName(),
                BotDispatcher.class.getName(),
                HeartbeatEventHandler.class.getName(),
                PrivateMessageEventHandler.class.getName(),
                GroupMessageEventHandler.class.getName(),
                BotInit.class.getName(),
                MessageStringInjector.class.getName(),
                GroupInjector.class.getName(),
                MessageChainInjector.class.getName(),
                TempFriendInjector.class.getName(),
                MemberInjector.class.getName(),
                GroupMessageEventInjector.class.getName(),
                MessageIdInjector.class.getName(),
                MessageIdIntInjector.class.getName()
        };
    }

}
