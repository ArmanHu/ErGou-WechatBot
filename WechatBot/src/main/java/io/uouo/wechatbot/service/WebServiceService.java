package io.uouo.wechatbot.service;

/**
 * 二狗成语接龙的主体程序
 * @author ming
 *
 */
public interface WebServiceService {

	/**
	 * 通过城市名称获取天气情况
	 * @param s
	 */
	void getWeatherByCityName(String s);

	void getStationAndTimeByTrainCode(String s);
}
