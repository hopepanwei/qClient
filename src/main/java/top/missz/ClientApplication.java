package top.missz;

import top.missz.bot.EnableBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.missz.bot.contact.support.Group;
import top.missz.bot.message.MessageChain;
import top.missz.config.SpringUtils;
import top.missz.service.SendMsgService;

import java.util.Scanner;

@SpringBootApplication
@EnableBot
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        Scanner input=new Scanner(System.in);
        SendMsgService sendMsgService = SpringUtils.getBean(SendMsgService.class);
        do {
            //接受String类型
            String str=input.nextLine();
            Group group = new Group(682702579);
            MessageChain message = new MessageChain();
            message.text(str);
            group.sendMessage(message);
            sendMsgService.sendSJKGroupMsg(group);
        } while (true);
    }

}
