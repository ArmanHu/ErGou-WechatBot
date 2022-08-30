package io.uouo.wechatbot.domain;

import java.io.Serializable;

public class BuguaLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String wxid;
	
	//卦名
	private String divinationName;
	
	//解卦内容
	private String DivinationInfo;
	
	//当日日期
	private String Date;

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	

	public String getDivinationName() {
		return divinationName;
	}

	public void setDivinationName(String divinationName) {
		this.divinationName = divinationName;
	}

	public String getDivinationInfo() {
		return DivinationInfo;
	}

	public void setDivinationInfo(String divinationInfo) {
		DivinationInfo = divinationInfo;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	@Override
	public String toString() {
		return "BuguaLog [wxid=" + wxid + ", divinationName=" + divinationName + ", DivinationInfo=" + DivinationInfo
				+ ", Date=" + Date + "]";
	}

	
}
