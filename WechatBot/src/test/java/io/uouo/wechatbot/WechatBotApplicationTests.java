package io.uouo.wechatbot;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.uouo.wechatbot.service.DivinationService;

@SpringBootTest(classes={WechatBotApplication.class})
public class WechatBotApplicationTests {

    @Resource
    private DivinationService divinationService;
    
    @Test
    public void test() {
    	String s= "{'content':'二狗卜卦','id':'20220810152303','receiver':'21398419530@chatroom','sender':'wxid_gpl326asr22i22','srvid':1,'time':'2022-08-10 15:23:03','type':1}";
    	System.out.println(s);
    }

}
