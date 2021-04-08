package top.missz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.missz.bot.contact.support.Group;
import top.missz.bot.message.MessageChain;
import top.missz.service.SendMsgService;

@RestController
@RequestMapping("/send")
public class SendMsgController {
    @Autowired
    SendMsgService sendMsgService;

    @GetMapping("/sendSJK")
    public void sendSJK(@RequestParam("msg") String msg){
        Group group = new Group(682702579);
        MessageChain message = new MessageChain();
        message.text(msg);
        group.sendMessage(message);
        sendMsgService.sendSJKGroupMsg(group);
    }
}
