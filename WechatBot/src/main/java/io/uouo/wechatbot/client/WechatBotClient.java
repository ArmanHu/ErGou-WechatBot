package io.uouo.wechatbot.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

import io.uouo.wechatbot.common.AjaxResult;
import io.uouo.wechatbot.common.WechatBotCommon;
import io.uouo.wechatbot.domain.WechatMsg;
import io.uouo.wechatbot.idiom.GameStatus;
import io.uouo.wechatbot.service.DivinationService;
import io.uouo.wechatbot.service.IdiomSolitaireService;
import io.uouo.wechatbot.service.PersonService;
import io.uouo.wechatbot.service.WebServiceService;

//http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl
//http://www.webxml.com.cn/WebServices/TrainTimeWebService.asmx?wsdl
//http://www.webxml.com.cn/WebServices/MobileCodeWS.asmx?wsdl
/**
 * websocket机器人客户端
 *
 * @author: [青衫] 'QSSSYH@QQ.com'
 * @Date: 2021-03-16 18:20
 * @Description: < 描述 >
 */
public class WechatBotClient extends WebSocketClient implements WechatBotCommon{

	/**
	 * 注入二狗成语接龙service
	 */
	@Autowired
    private IdiomSolitaireService idiomSolitaireService;
	/**
	 * 注入二狗卜卦service
	 */
	@Autowired
	private  DivinationService divinationService;
	
	@Autowired
	private PersonService personService;

	@Autowired
	private WebServiceService webServiceService;
    /**
     * 描述: 构造方法创建 WechatBotClient对象
     *
     * @param url WebSocket链接地址
     * @return
     * @Author 青衫 [2940500@qq.com]
     * @Date 2021-3-26
     */
    public WechatBotClient(String url) throws URISyntaxException {
        super(new URI(url));
    }

    /**
     * 描述: 在websocket连接开启时调用
     *
     * @param serverHandshake
     * @return void
     * @Author 青衫 [2940500@qq.com]
     * @Date 2021-3-16
     */
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.err.println("已发送尝试连接到微信客户端请求");
        personService.getNickNameAndWxidByChatroom();
        System.out.println("执行方法,获取群成员昵称。");
    }
    
    
    /**
     * 描述: 方法在接收到消息时调用
     *
     * @param s
     * @return void
     * @Author 青衫 [2940500@qq.com]
     * @Date 2021-3-16
     */
    @Override
    public void onMessage(String s) {
        System.out.println("微信中收到了消息:" + s);
        personService.saveNickNameAndWxid(s);
        //判断是否为接龙状态如果是，直接走程序
        if (GameStatus.isRunning(AjaxResult.CHATROOM)) {
            idiomSolitaireService.idiomSolitaire(s);
            return;
        }
        //成语接龙
        if(s.contains("二狗")&&s.contains("接龙"))
        	//出题，并把状态置为接龙状态
        	idiomSolitaireService.idiomSolitaireFirst(s);
        //成语接龙
        if(s.contains("二狗成语提示"))
        	idiomSolitaireService.idiomSolitaireRemind(s);
        //成语接龙：接别人的龙
        else if(s.contains("👉")&&s.contains("👈"))
        	idiomSolitaireService.proceedYourGame(s);
        //成语接龙：接别人的龙
        else if(s.contains("条：")&&s.contains("("))
        	idiomSolitaireService.proceedYourGame(s);
        //卜卦
        else if(s.contains("二狗卜卦"))
        	divinationService.practiseDivination(s);
        //解卦
        else if(s.contains("二狗解卦"))
            divinationService.explainDivination(s);
        //天气
        else if(s.contains("二狗")&&s.contains("天气"))
        	webServiceService.getWeatherByCityName(s);
        else if(s.contains("二狗"))
        	personService.introduceFunctions(s);
    }
    

	/**
     * 描述: 方法在连接断开时调用
     *
     * @param i
     * @param s
     * @param b
     * @return void
     * @Author 青衫 [2940500@qq.com]
     * @Date 2021-3-16
     */
    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("已断开连接... ");
    }

    /**
     * 描述: 方法在连接出错时调用
     *
     * @param e
     * @return void
     * @Author 青衫 [2940500@qq.com]
     * @Date 2021-3-16
     */
    @Override
    public void onError(Exception e) {
        System.err.println("通信连接出现异常:" + e.getMessage());

    }

    /**
     * 描述: 发送消息工具 (其实就是把几行常用代码提取出来 )
     *
     * @param wechatMsg 消息体
     * @return void
     * @Author 青衫 [2940500@qq.com]
     * @Date 2021-3-18
     */
    public void sendMsgUtil(WechatMsg wechatMsg) {
        if (!StringUtils.hasText(wechatMsg.getExt())) {
            wechatMsg.setExt(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getNickname())) {
            wechatMsg.setNickname(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getRoomid())) {
            wechatMsg.setRoomid(NULL_MSG);
        }
        if (!StringUtils.hasText(wechatMsg.getContent())) {
            wechatMsg.setContent(NULL_MSG);
        }
       if (!StringUtils.hasText(wechatMsg.getWxid())) {
            wechatMsg.setWxid(NULL_MSG);
        }


        // 消息Id
        wechatMsg.setId(String.valueOf(System.currentTimeMillis()));
        // 发送消息
        String string = JSONObject.toJSONString(wechatMsg);
        System.err.println(":" + string);
        send(JSONObject.toJSONString(wechatMsg));
    }
}