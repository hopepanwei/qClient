//package top.missz;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import top.missz.bot.contact.support.Group;
//import top.missz.bot.message.MessageChain;
//import top.missz.service.ReceiveMsgService;
//import top.missz.service.SendMsgService;
//
//@SpringBootTest
//class ClientApplicationTests {
//
//    @Autowired
//    SendMsgService sendMsgService;
//
//    @Test
//    public void sendTest() {
//        Group group = new Group(682702579);
//        MessageChain message = new MessageChain();
//        message.text("1");
//        group.sendMessage(message);
//        sendMsgService.sendSJKGroupMsg(group);
//    }
//
//    @Autowired
//    private ReceiveMsgService receiveMsgService;
//
////    @Test
////    public void receiveMsg() {
////        String msg = "";
////        Group group = new Group(682702579);
////        msg = receiveMsgService.receiveSJKGroupMsg(group, msg);
////        System.out.println(msg);
////
////    }
//
//}
