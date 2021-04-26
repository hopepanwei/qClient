package top.missz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.missz.bot.EnableBot;
import top.missz.bot.contact.support.Group;
import top.missz.bot.message.MessageChain;
import top.missz.bot.util.http.HttpUtils;
import top.missz.config.SpringUtils;
import top.missz.service.SendMsgService;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
@EnableBot
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        Scanner input = new Scanner(System.in);
        SendMsgService sendMsgService = SpringUtils.getBean(SendMsgService.class);
        do {
            //接受String类型
            String str = input.nextLine();
            String num = transArrayStr(str, ":([\\d]+):");
            int groupId;
            Group group;
            if (num == null) {
                group = new Group(682702579);
                groupId = 682702579;
            } else {
                group = new Group(Integer.getInteger(num));
                groupId = Integer.getInteger(num);
                str = str.replaceAll(":([\\d]+):", "");
            }
            MessageChain message = new MessageChain();
            if (str.contains("@")) {
                String name = transArrayStr(str, "@([\\s\\S]+):");
                Integer userId = getUserId(groupId, name);
                if (userId != null) {
                    message.at(userId);
                }
                if (name.equals("all")){
                    message.atAll();
                }
                str = str.replaceAll("@([\\s\\S]+):", "");
            }
            message.text(str);
            group.sendMessage(message);
            sendMsgService.sendSJKGroupMsg(group);
        } while (true);
    }

    public static String transArrayStr(String contents, String Reg) {
        Pattern r = Pattern.compile(Reg, Pattern.CASE_INSENSITIVE);
        Matcher mat = r.matcher(contents);
        String list = null;
        while (mat.find()) {
            return mat.group(1);
        }
        return list;
    }

    public static Integer getUserId(int groupId, String name) {
        String response = HttpUtils.sendGet("http://127.0.0.1:5700/get_group_member_list", "group_id=" + groupId);
        JSONObject json = JSONObject.parseObject(response);
        JSONArray data = json.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            JSONObject people = data.getJSONObject(i);
            if (name.equals(people.getString("card")) || name.equals(people.getString("nickname"))) {
                return people.getInteger("user_id");
            }
        }
        return null;
    }

}
