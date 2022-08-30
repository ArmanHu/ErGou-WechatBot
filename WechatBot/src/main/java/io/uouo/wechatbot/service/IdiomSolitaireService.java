package io.uouo.wechatbot.service;

/**
 * 二狗成语接龙的主体程序
 * @author ming
 *
 */
public interface IdiomSolitaireService {

	/**
	 * 二狗成语接龙 出题程序
	 * @param s
	 */
	public void idiomSolitaireFirst(String s);
	
	/**
	 * 成语接龙 记录程序：到达第十三条或者接不上了退出此程序
	 * @param s
	 * @param jielongStatus 
	 */
	public void idiomSolitaire(String s);

	/**
	 * 去接别人的龙
	 * @param s
	 */
	public void proceedYourGame(String s);

	/**
	 * 删除掉已经用来接龙的成语，并新建文件
	 */
	public void deleteUsedChengyu();

    /**
	 * 二狗成语接龙提醒
	 * @param s
	 */
	public void idiomSolitaireRemind(String s);
	/**
	 * 二狗成语接龙提醒2
	 * @param wxid
	 * @param message
	 */
	public void idiomSolitaireRemind(String roomid,String wxid,String message);
}
