package io.uouo.wechatbot.idiom;

public class GameNode {
    
    private String wxid;
    //昵称
    private String nickName;
    //成语
    private String idiom;


    public GameNode(String wxid, String nickName, String idiom) {
		super();
		this.wxid = wxid;
		this.nickName = nickName;
		this.idiom = idiom;
	}


	public String getWxid() {
		return wxid;
	}


	public void setWxid(String wxid) {
		this.wxid = wxid;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getIdiom() {
		return idiom;
	}


	public void setIdiom(String idiom) {
		this.idiom = idiom;
	}


	@Override
	public String toString() {
		return "GameNode [wxid=" + wxid + ", nickName=" + nickName + ", idiom=" + idiom + "]";
	}

	

}
