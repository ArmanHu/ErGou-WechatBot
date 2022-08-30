package io.uouo.wechatbot.service;

/**
 * 二狗成语接龙的主体程序
 * @author ming
 *
 */
public interface PersonService {

	/**
	 * 发送请求通过roomid获取wxid和昵称
	 * @param s
	 */
	void getNickNameAndWxidByChatroom();

	/**
	 * 将昵称和wxid实时更新进文件中
	 * @param s
	 */
	void saveNickNameAndWxid(String s);

	/**
	 * 介绍功能
	 */
	void introduceFunctions(String s);

	
}
