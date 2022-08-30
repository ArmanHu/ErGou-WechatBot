package io.uouo.wechatbot.domain;

import java.io.Serializable;

public class Person implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String wxid;
	
	private String nickname;
	
	private String roomid;

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	@Override
	public String toString() {
		return "Person [wxid=" + wxid + ", nickname=" + nickname + ", roomid=" + roomid + "]";
	}

	
}
