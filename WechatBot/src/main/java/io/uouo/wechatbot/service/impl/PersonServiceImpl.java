package io.uouo.wechatbot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import io.uouo.wechatbot.common.AjaxResult;
import io.uouo.wechatbot.common.WechatBotCommon;
import io.uouo.wechatbot.common.util.MyFileUtil;
import io.uouo.wechatbot.domain.Person;
import io.uouo.wechatbot.domain.ResponseMsg;
import io.uouo.wechatbot.domain.WechatMsg;
import io.uouo.wechatbot.service.PersonService;
import io.uouo.wechatbot.service.WechatBotService;

/**
 * @author: ming
 * @Date: 2022年8月3日17点00分
 * @Description: <  >
 */
@Service
public class PersonServiceImpl implements PersonService	 {
	@Autowired
	private WechatBotService wechatBotService;
	
	@Override
	public void getNickNameAndWxidByChatroom() {
		WechatMsg wechatMsg = new WechatMsg();
		wechatMsg.setWxid("ROOT");
		wechatMsg.setContent(AjaxResult.CHATROOM);
		wechatBotService.getChatroomMemberNick(wechatMsg);
	}

	@Override
	public void saveNickNameAndWxid(String s) {
		ResponseMsg responseMsg = JSONObject.parseObject(s,ResponseMsg.class);
		if(WechatBotCommon.CHATROOM_MEMBER_NICK!=responseMsg.getType())
		return;
		String content = responseMsg.getContent();
		List<Person> list = new ArrayList<Person>();
		list = JSONObject.parseArray(content, Person.class);
		//通过文件名获取文件路径
		String filePath = MyFileUtil.getFilePathByFileName(AjaxResult.PERSON);

		boolean writeFile = MyFileUtil.writeFile(list, filePath);
		if(writeFile){
			System.out.println("已成功保存群成员昵称到本地");
		}
	}

	@Override
	public void introduceFunctions(String s) {
		
		ResponseMsg responseMsg = JSONObject.parseObject(s,ResponseMsg.class);
		String wxid = responseMsg.getSender();
		String roomid = responseMsg.getReceiver();
		
		if(!AjaxResult.CHATROOM.equals(roomid)||AjaxResult.MYWXID.equals(wxid))
		return;
		
		String nickName = MyFileUtil.getNickNameByWxid(wxid);
		StringBuffer sb = new StringBuffer("@"+nickName+" 我还不会闲聊，听不懂您在说什么。。。\n");
        
    	sb.append("想要成语接龙，可以跟我说:二狗成语接龙\n");
    	sb.append("想要无限接龙，可以跟我说:二狗无限接龙\n");
    	sb.append("想要退出接龙，可以跟我说:退出接龙\n");
    	sb.append("想要成语提示，可以跟我说:二狗成语提示 月\n");
    	sb.append("想要诚心卜卦，可以跟我说:二狗卜卦\n");
    	sb.append("想要周易解卦，可以跟我说:二狗解卦\n");
    	sb.append("想要知道天气，可以跟我说:二狗北京天气");
        
        
        WechatMsg wechatMsg = new WechatMsg();
		wechatMsg.setWxid(AjaxResult.CHATROOM);
    	wechatMsg.setContent(sb.toString());
		wechatBotService.sendTextMsg(wechatMsg);
		
		
	}


    
}
