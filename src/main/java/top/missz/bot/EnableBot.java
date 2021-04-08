package top.missz.bot;

import top.missz.bot.support.BotApplicationRegistrar;
import top.missz.bot.support.BotAutoConfigRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xiaoxu
 * @date 2020-08-07 10:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({BotApplicationRegistrar.class, BotAutoConfigRegistrar.class})
public @interface EnableBot {
}
