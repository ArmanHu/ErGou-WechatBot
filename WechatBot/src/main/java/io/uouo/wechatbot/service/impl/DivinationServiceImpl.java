package io.uouo.wechatbot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

import io.uouo.wechatbot.common.AjaxResult;
import io.uouo.wechatbot.common.util.DateTimeUtil;
import io.uouo.wechatbot.common.util.MyFileUtil;
import io.uouo.wechatbot.common.util.RandomTextUtil;
import io.uouo.wechatbot.domain.BuguaLog;
import io.uouo.wechatbot.domain.ResponseMsg;
import io.uouo.wechatbot.domain.WechatMsg;
import io.uouo.wechatbot.service.DivinationService;
import io.uouo.wechatbot.service.PersonService;
import io.uouo.wechatbot.service.WechatBotService;

/**
 * @author: ming
 * @Date: 2022年8月3日17点00分
 * @Description: <  >
 */
@Service
public class DivinationServiceImpl implements DivinationService	 {
	@Autowired
	private WechatBotService wechatBotService;
	@Autowired
	private PersonService personService;
	
	/**
	 * 二狗卜卦主程序:对进入的每一句话进行判断
	 */
	@Override
	public void practiseDivination(String s) {
		//实时发送请求获取群内wxid=昵称
		personService.getNickNameAndWxidByChatroom();
		ResponseMsg responseMsg = JSONObject.parseObject(s,ResponseMsg.class);
		String roomid = responseMsg.getReceiver();
		//想要卜卦者的wxid
		String wxid = responseMsg.getSender();
		
		if(!StringUtils.hasLength(roomid)&&!StringUtils.hasLength(roomid)){
			roomid = AjaxResult.CHATROOM;
			wxid = AjaxResult.MYWXID;
		}
		//如果不是二狗运行群，就不响应
		if(!AjaxResult.CHATROOM.equals(roomid))
		return;
		
		
		//初始化往群里发消息
		WechatMsg wechatMsg = new WechatMsg();
		wechatMsg.setWxid(roomid);
		//1.判断这个人是否已经卜卦，如果是就告诉他已经卜卦过了
		//通过微信id获取当日解卦信息，如果未获取到就让他卜卦，如果获取到了，就告诉他明天再来
		String nickName = MyFileUtil.getNickNameByWxid(wxid);
		String divinationInfo = MyFileUtil.getBuguaLogByWxid(wxid);
		if(StringUtils.hasLength(divinationInfo)){
			String content="@"+nickName+" [旺柴]每日最多卜一卦！多卜不准哦！请明天再来吧！";
			wechatMsg.setContent(content);
			wechatBotService.sendTextMsg(wechatMsg);
			return;
		}
		
		//2.如果当日没有卜卦，就继续
		//通过文件名获取文件路径，文王金钱课卜取一卦
		String filePath = MyFileUtil.getFilePathByFileName(AjaxResult.BUGUA);
	    
		String str = "";
		try {
			str = RandomTextUtil.getRandomText(filePath);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		//
		if(!StringUtils.hasLength(str))
			return;
		
		//由于读取文件的时候 自动将\n转义为\\n了  无法正确换行
	    String str1 = str.replaceAll("\\\\n","\n");
	    
	    //str1结构：卜卦=解卦
	    String[] strings = str1.split("=");
	    String divinationName = strings[0];
	    divinationInfo = strings[1];
	    //卜卦结构：序号、卦名
	    String[] strings2 = divinationName.split("、");
	    //序号
	    String str2 = strings2[0];
	    //卦名
	    String str3 = strings2[1];
	    String content="@"+nickName+" 🎐您卜到了第"+str2+"卦！"+"\n- - - - - - - - - - - - - - -\n"
	    +str3+"\n- - - - - - - - - - - - - - -\n"+"需要解卦请回复【二狗解卦】";
	    //发消息给卜卦的人。
	    wechatMsg.setContent(content);
		wechatBotService.sendTextMsg(wechatMsg);
	    
		//3.将当事人当日卜卦情况 存到卜卦log文件里
		List<BuguaLog> buguaLogList = new ArrayList<BuguaLog>();
		BuguaLog buguaLog = new BuguaLog();
	    String date = DateTimeUtil.getDateToday();
	    buguaLog.setDate(date);
	    buguaLog.setWxid(wxid);
	    buguaLog.setDivinationName(divinationName);
	    buguaLog.setDivinationInfo(divinationInfo);
	    buguaLogList.add(buguaLog);
	    String filePath2 = MyFileUtil.getFilePathByFileName(AjaxResult.BUGUALOG);
		MyFileUtil.writeFile(buguaLogList, filePath2);
	}

	/**
	 * 二狗解卦
	 */
	@Override
	public void explainDivination(String s) {
		personService.getNickNameAndWxidByChatroom();
		ResponseMsg responseMsg = JSONObject.parseObject(s,ResponseMsg.class);
		String roomid = responseMsg.getReceiver();
		//想要卜卦者的wxid
		String wxid = responseMsg.getSender();
		if(!StringUtils.hasLength(roomid)&&!StringUtils.hasLength(wxid)){
			roomid = AjaxResult.CHATROOM;
			wxid = AjaxResult.MYWXID;
		}
		//如果不是二狗运行群，就不响应
		if(!AjaxResult.CHATROOM.equals(roomid))
		return;
		//想要解卦者的wxid
		//初始化往群里发消息
		WechatMsg wechatMsg = new WechatMsg();
		wechatMsg.setWxid(AjaxResult.CHATROOM);
		//1.判断这个人是否已经卜卦，如果是就告诉他已经卜卦过了
		//通过微信id获取当日解卦信息，如果未获取到就让他卜卦，如果获取到了，就告诉他明天再来
		String nickName = MyFileUtil.getNickNameByWxid(wxid);
		String divinationInfo = MyFileUtil.getBuguaLogByWxid(wxid);
		if(StringUtils.hasLength(divinationInfo)){
			String content="@"+nickName+"\n- - - - - - - - - - - - - - -\n"+divinationInfo;
			wechatMsg.setContent(content);
			wechatBotService.sendTextMsg(wechatMsg);
			return;
		}else {
			String content="@"+nickName+" [旺柴]请您卜完卦,再来解卦吧！";
			wechatMsg.setContent(content);
			wechatBotService.sendTextMsg(wechatMsg);
			return;
		}
	}
	
	

	
    
}
