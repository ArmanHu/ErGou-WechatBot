package io.uouo.wechatbot.domain;

import java.io.Serializable;

/**
 * 在接别人的龙的时候，自己发出的所有成语
 * @author ming
 *
 */
public class UsedChengyu implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String wxid;
	//成语
	private String chengyu;
	
	

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	public String getChengyu() {
		return chengyu;
	}

	public void setChengyu(String chengyu) {
		this.chengyu = chengyu;
	}

	@Override
	public String toString() {
		return "UsedChengyu [wxid=" + wxid + ", chengyu=" + chengyu + "]";
	}



}
