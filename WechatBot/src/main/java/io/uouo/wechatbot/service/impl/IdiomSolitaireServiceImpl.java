package io.uouo.wechatbot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

import io.uouo.wechatbot.common.AjaxResult;
import io.uouo.wechatbot.common.util.ChengyuUtil;
import io.uouo.wechatbot.common.util.MyFileUtil;
import io.uouo.wechatbot.domain.ResponseMsg;
import io.uouo.wechatbot.domain.UsedChengyu;
import io.uouo.wechatbot.domain.WechatMsg;
import io.uouo.wechatbot.idiom.GameStatus;
import io.uouo.wechatbot.idiom.IdiomGame;
import io.uouo.wechatbot.service.IdiomSolitaireService;
import io.uouo.wechatbot.service.WechatBotService;

/**
 * @author: ming
 * @Date: 2022年8月3日17点00分
 * @Description: <  >
 */
@Service
public class IdiomSolitaireServiceImpl implements IdiomSolitaireService {
	
	@Autowired
	private WechatBotService wechatBotService;
	
	
	
	/**
	 * 被呼唤二狗时，我需要出题
	 */
	@Override
	public void idiomSolitaireFirst(String s) {
		ResponseMsg responseMsg = JSONObject.parseObject(s,ResponseMsg.class);
		String wxid = responseMsg.getSender();
		String roomid = responseMsg.getReceiver();
		String content = responseMsg.getContent();
		if(!StringUtils.hasLength(roomid)&&!StringUtils.hasLength(wxid)){
			roomid = AjaxResult.CHATROOM;
			wxid = AjaxResult.MYWXID;
		}
		if(!AjaxResult.CHATROOM.equals(roomid))
		return;
		boolean isLimit = true;
		if(content.contains("无限")){
			isLimit = false;
		}
		IdiomGame.start(roomid,isLimit);
		
	}
	
	@Override
	public void idiomSolitaire(String s) {
		ResponseMsg responseMsg = JSONObject.parseObject(s,ResponseMsg.class);
		String wxid = responseMsg.getSender();
		String roomid = responseMsg.getReceiver();
		if(!StringUtils.hasLength(roomid)&&!StringUtils.hasLength(wxid)){
			roomid = AjaxResult.CHATROOM;
			wxid = AjaxResult.MYWXID;
		}
		if(!AjaxResult.CHATROOM.equals(roomid))
		return;
		//获取此人昵称
		//String nickName = MyFileUtil.getNickNameByWxid(wxid);
		
		String msgContent = responseMsg.getContent();
		
		
		GameStatus.getGame(roomid).setMessage(wxid, msgContent);

		
	}
	
	@Override
	public void proceedYourGame(String s) {
		
		ResponseMsg responseMsg = JSONObject.parseObject(s,ResponseMsg.class);
		String responseMsgContent = responseMsg.getContent();
		if(StringUtils.hasLength(responseMsg.getReceiver())){
			if(!AjaxResult.CHATROOM.equals(responseMsg.getReceiver()))
				return;
		}
		String chengyu = getChengyuByMsgContent(responseMsgContent);
		if(chengyu.length()<4)
		return;
		
		String filePath = MyFileUtil.getFilePathByFileName(AjaxResult.USEDCHENGYU);
		
		//将成语接龙情况 存到接龙log文件里
		List<UsedChengyu> chengyuList = new ArrayList<UsedChengyu>();
		UsedChengyu usedChengyu = new UsedChengyu();
	    usedChengyu.setWxid(AjaxResult.MYWXID);
	    usedChengyu.setChengyu(chengyu);
	    chengyuList.add(usedChengyu);
	    MyFileUtil.writeFile(chengyuList, filePath);
		
		//根据最后一个字获取可以用来接龙的成语列表
		List<String> list= ChengyuUtil.getChengyuListByChengyu(chengyu);
		if(list==null||list.size()==0)
		return;
		
		//获取已经用过的接龙过的成语
		List<UsedChengyu> UsedChengyuList = MyFileUtil.readFile(filePath);
		List<String> list2 = new ArrayList<String>();
		if(UsedChengyuList!=null && UsedChengyuList.size()!=0){
			for (UsedChengyu usedChengyu1 : UsedChengyuList) {
				list2.add(usedChengyu1.getChengyu());
			}
		}
		list.removeAll(list2);
		if(list==null||list.size()==0)
		return;
		String string = ChengyuUtil.getRandomStringFromList(list);
		if(!StringUtils.hasLength(string))
		return;
		WechatMsg wechatMsg = new WechatMsg();
		wechatMsg.setWxid(AjaxResult.CHATROOM);
		wechatMsg.setContent(string);
		wechatBotService.sendTextMsg(wechatMsg);
	}
	
	public static String getChengyuByMsgContent(String content){
		String chengyu="";
		if(content.contains("👉")&&content.contains("👈")){
			try {
				String[] strings = content.split("👉");
				if(strings.length<=1)
					return "";
				String[] split = strings[1].split("👈");
				chengyu = split[0];
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				String[] strings = content.split("条：");
				if(strings.length<=1)
					return "";
				String[] split = strings[1].split("\\(");
				chengyu = split[0];
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return chengyu;
	}

	public static void main(String[] args) {
		//System.out.println(getChengyuByMsgContent("第6条：年复一年(nián)"));
		//System.out.println(getChengyuByMsgContent("第2条：👉意气风发👈"));
		IdiomSolitaireServiceImpl a = new IdiomSolitaireServiceImpl();
		String s = "{'content':'二狗接龙','id':'20220810152303','receiver':'21398419530@chatroom','sender':'wxid_gpl326asr22i22','srvid':1,'time':'2022-08-10 15:23:03','type':1}";
		a.idiomSolitaire(s);
	}
	
	@Override
	public void deleteUsedChengyu() {
		String filePath = MyFileUtil.class.getClassLoader().getResource(AjaxResult.USEDCHENGYU).getPath();
		MyFileUtil.deleteAndCreateFile(filePath);
	}


	@Override
	public void idiomSolitaireRemind(String s) {
		ResponseMsg responseMsg = JSONObject.parseObject(s,ResponseMsg.class);
		String wxid = responseMsg.getSender();
		String roomid = responseMsg.getReceiver();
		if(!StringUtils.hasLength(roomid)&&!StringUtils.hasLength(wxid)){
			roomid = AjaxResult.CHATROOM;
			wxid = AjaxResult.MYWXID;
		}
		String nickName = MyFileUtil.getNickNameByWxid(wxid);
		if(!AjaxResult.CHATROOM.equals(roomid))
		return;
		String content = responseMsg.getContent();
		String keyword = getKeyword(content);
		WechatMsg wechatMsg = new WechatMsg();
		wechatMsg.setWxid(AjaxResult.CHATROOM);
		if(!StringUtils.hasLength(keyword)){
	    	wechatMsg.setContent("@"+nickName+" 请输入正确的格式哦，如二狗成语提示 月");
			wechatBotService.sendTextMsg(wechatMsg);
			return;
		}
		List<String> chengyuList = ChengyuUtil.getChengyuListByKeyword(keyword);
		if(chengyuList==null||chengyuList.size()==0){
			wechatMsg.setContent("@"+nickName+" 该关键字在词库找不到可以接龙的成语哦");
			wechatBotService.sendTextMsg(wechatMsg);
			return;
		}
		int size = chengyuList.size()>10?10:chengyuList.size();
		
		StringBuffer sb = new StringBuffer("@"+nickName+" 找到了可以接龙["+keyword+"]的部分成语:\n");
		for (int i=0;i< size;i++) {
			if(i==size-1){
				sb.append(chengyuList.get(i));
			}else{
				sb.append(chengyuList.get(i)+"\n");
			}
		}
		wechatMsg.setContent(sb.toString());
		wechatBotService.sendTextMsg(wechatMsg);
	}
	
	@Override
	public void idiomSolitaireRemind(String roomid,String wxid,String message) {
		if(!StringUtils.hasLength(roomid)){
			roomid = AjaxResult.CHATROOM;
		}
		if(!StringUtils.hasLength(wxid)){
			wxid = AjaxResult.MYWXID;
		}
		String nickName = MyFileUtil.getNickNameByWxid(wxid);
		if(!AjaxResult.CHATROOM.equals(roomid))
		return;
		String content = message;
		String keyword = getKeyword(content);
		WechatMsg wechatMsg = new WechatMsg();
		wechatMsg.setWxid(AjaxResult.CHATROOM);
		if(!StringUtils.hasLength(keyword)){
	    	wechatMsg.setContent("@"+nickName+" 请输入正确的格式哦，如二狗成语提示 月");
			wechatBotService.sendTextMsg(wechatMsg);
			return;
		}
		List<String> chengyuList = ChengyuUtil.getChengyuListByKeyword(keyword);
		if(chengyuList==null||chengyuList.size()==0){
			wechatMsg.setContent("@"+nickName+" 该关键字["+keyword+"]在词库找不到可以接龙的成语哦");
			wechatBotService.sendTextMsg(wechatMsg);
			return;
		}
		int size = chengyuList.size()>10?10:chengyuList.size();
		
		StringBuffer sb = new StringBuffer("@"+nickName+" 找到了可以接龙["+keyword+"]的部分成语:\n");
		for (int i=0;i< size;i++) {
			if(i==size-1){
				sb.append(chengyuList.get(i));
			}else{
				sb.append(chengyuList.get(i)+"\n");
			}
		}
		wechatMsg.setContent(sb.toString());
		wechatBotService.sendTextMsg(wechatMsg);
	}

	private String getKeyword(String content) {
		String keyword = "";
		try{
			String[] split = content.split(" ");
			String keywordStr = split[1];
			keyword = keywordStr.substring(keywordStr.length()-1);
			if(!StringUtils.hasLength(keyword))
				return keyword;
			if(ChengyuUtil.isChineseChar(keyword.toCharArray()[0]))
				return keyword;
		}catch(Exception e){
			e.printStackTrace();
		}
		return keyword;
	}

}
