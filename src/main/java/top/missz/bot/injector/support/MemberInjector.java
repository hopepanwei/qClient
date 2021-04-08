package top.missz.bot.injector.support;

import top.missz.bot.contact.support.Member;
import top.missz.bot.event.message.GroupMessageEvent;
import top.missz.bot.event.message.MessageEvent;
import top.missz.bot.injector.MessageObjectInjector;
import top.missz.bot.message.MessageChain;

public class MemberInjector implements MessageObjectInjector<Member> {
    @Override
    public Class<Member> getType() {
        return Member.class;
    }

    @Override
    public Member getObject(MessageEvent messageEvent, MessageChain messageChain) {
        if (!(messageEvent instanceof GroupMessageEvent)) {
            return null;
        }
        return new Member(messageEvent.getUserId());
    }
}
