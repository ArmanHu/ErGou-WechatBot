package io.uouo.wechatbot.domain;

import java.io.Serializable;

/**
 * 在别人接龙的时候，记录别人发出的所有成功接龙的成语
 * @author ming
 *
 */
public class ChengyuUsed implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//序号
	private int jielongNo;

	private String wxid;
	//成语
	private String chengyu;
	
	public int getJielongNo() {
		return jielongNo;
	}

	public void setJielongNo(int jielongNo) {
		this.jielongNo = jielongNo;
	}

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
		return "ChengyuUsed [jielongNo=" + jielongNo + ", wxid=" + wxid + ", chengyu=" + chengyu + "]";
	}


	
}
