package io.uouo.wechatbot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

import io.uouo.wechatbot.common.AjaxResult;
import io.uouo.wechatbot.common.util.MyFileUtil;
import io.uouo.wechatbot.domain.ResponseMsg;
import io.uouo.wechatbot.domain.WechatMsg;
import io.uouo.wechatbot.service.PersonService;
import io.uouo.wechatbot.service.WebServiceService;
import io.uouo.wechatbot.service.WechatBotService;
import io.uouo.wechatbot.webservice.weather.ArrayOfString;
import io.uouo.wechatbot.webservice.weather.WeatherWebService;
import io.uouo.wechatbot.webservice.weather.WeatherWebServiceSoap;

/**
 * @author: ming
 * @Date: 2022年8月3日17点00分
 * @Description: <  >
 */
@Service
public class WebServiceServiceImpl implements WebServiceService	 {
	@Autowired
	private WechatBotService wechatBotService;
	@Autowired
	private PersonService personService;
	
	@Override
	public void getWeatherByCityName(String s) {
		//实时发送请求获取群内wxid=昵称
		personService.getNickNameAndWxidByChatroom();
		
		ResponseMsg responseMsg = JSONObject.parseObject(s,ResponseMsg.class);
		String roomid = responseMsg.getReceiver();
		//想要和二狗沟通者的wxid
		String wxid = responseMsg.getSender();
		String msgContent = responseMsg.getContent();
		if(!StringUtils.hasLength(roomid)&&!StringUtils.hasLength(wxid)){
			roomid = AjaxResult.CHATROOM;
			wxid = AjaxResult.MYWXID;
		}
		//如果不是二狗运行群，就不响应
		if(!AjaxResult.CHATROOM.equals(roomid))
		return;
		WechatMsg wechatMsg = new WechatMsg();
		wechatMsg.setWxid(roomid);
		//通过微信id获取昵称
		String nickName = MyFileUtil.getNickNameByWxid(wxid);
		
		String cityName = getCityNameByMsgContent(msgContent);
		if(!StringUtils.hasLength(cityName)){
			wechatMsg.setContent("@"+nickName+" [旺柴]请输入正确的格式查询天气，如：二狗北京天气");
			wechatBotService.sendTextMsg(wechatMsg);
			return;
		}
		
		String content = getWeatherbyCityName(cityName,nickName);
		if(!StringUtils.hasLength(content))
		return;
		
		wechatMsg.setContent(content);
		wechatBotService.sendTextMsg(wechatMsg);
	}
	
	private static String getCityNameByMsgContent(String content) {
		String cityName = "";
		try {
			String[] strings = content.split("二狗");
			String[] split = strings[1].split("天气");
			cityName = split[0];
		} catch (Exception e) {
			e.printStackTrace();
			return cityName;
		}
		return cityName;
	}



	private static String getWeatherbyCityName(String cityName, String nickName) {
		WeatherWebService service = new WeatherWebService();
		WeatherWebServiceSoap portType = service.getWeatherWebServiceSoap();
		
		ArrayOfString arrayOfString = portType.getWeatherbyCityName(cityName);
		List<String> list = arrayOfString.getString();
		if(list==null||list.size()==0)
		return "";
		
		String weather = "@"+nickName+" "+list.get(0)+" "+list.get(1)+" "+list.get(5)+"\n"
				+list.get(6)+" "+list.get(7)+" \n\n"+list.get(10);
		
		return weather;
	}


	@Override
	public void getStationAndTimeByTrainCode(String s) {
		//实时发送请求获取群内wxid=昵称
		personService.getNickNameAndWxidByChatroom();
		
		ResponseMsg responseMsg = JSONObject.parseObject(s,ResponseMsg.class);
		String roomid = responseMsg.getReceiver();
		//想要和二狗沟通者的wxid
		String wxid = responseMsg.getSender();
		String msgContent = responseMsg.getContent();
		if(!StringUtils.hasLength(roomid)&&!StringUtils.hasLength(roomid)){
			roomid = AjaxResult.CHATROOM;
			wxid = AjaxResult.MYWXID;
		}
		//如果不是二狗运行群，就不响应
		if(!AjaxResult.CHATROOM.equals(roomid))
		return;
		WechatMsg wechatMsg = new WechatMsg();
		wechatMsg.setWxid(roomid);
		//通过微信id获取昵称
		String nickName = MyFileUtil.getNickNameByWxid(wxid);
		
		String trainCode = getTrainCodeByMsgContent(msgContent);
		if(!StringUtils.hasLength(trainCode)){
			wechatMsg.setContent("@"+nickName+" [旺柴]请输入正确的格式查询列车时刻，如：二狗列车时刻k179");
			wechatBotService.sendTextMsg(wechatMsg);
			return;
		}
		
		String content = getStationAndTimeByTrainCode(trainCode,nickName);
		if(!StringUtils.hasLength(content))
		return;
		
		wechatMsg.setContent(content);
		wechatBotService.sendTextMsg(wechatMsg);
	}
	

	private static String getTrainCodeByMsgContent(String content) {
		String trainCode = "";
		try {
			String[] strings = content.split("二狗列车时刻");
			trainCode = strings[0];
		} catch (Exception e) {
			e.printStackTrace();
			return trainCode;
		}
		return trainCode;
	}



	private static String getStationAndTimeByTrainCode(String trainCode, String nickName) {
		/*TrainTimeWebService service = new TrainTimeWebService();
		TrainTimeWebServiceSoap portType = service.getTrainTimeWebServiceSoap();
		String userId ="";
		ArrayOfString arrayOfString = portType.getStationAndTimeByTrainCode(trainCode,userId);
		List<String> list = arrayOfString.getString();
		if(list==null||list.size()==0)
		return "";
		
		for (String string : list) {
			System.out.println(string);
		}
		String stationAndTime = "@"+nickName+" "+list.get(0)+" "+list.get(1)+" "+list.get(5)+" "
				+list.get(6)+" "+list.get(7)+" \n"+list.get(10);
		
		return stationAndTime;*/
		return "";
	}

	public static void main(String[] args) {
		String content="二狗北京天气";
		String cityName = getCityNameByMsgContent(content);
		String weather = getWeatherbyCityName(cityName,"11");
		System.out.println(weather);
		/*String content2="二狗列车时刻k179";
		String trainCode = getTrainCodeByMsgContent(content2);
		String stationAndTime = getStationAndTimeByTrainCode(trainCode,"11");
		System.out.println(stationAndTime);*/
	}
    
}
