package io.uouo.wechatbot.idiom;

public class GameNode {
	//成语序号
    private int idiomNo;
    
    private String wxid;
    //成语
    private String idiom;


    public GameNode(int idiomNo, String wxid, String idiom) {
		super();
		this.idiomNo = idiomNo;
		this.wxid = wxid;
		this.idiom = idiom;
	}

	public int getIdiomNo() {
		return idiomNo;
	}


	public void setIdiomNo(int idiomNo) {
		this.idiomNo = idiomNo;
	}


	public String getWxid() {
		return wxid;
	}


	public void setWxid(String wxid) {
		this.wxid = wxid;
	}


	public String getIdiom() {
		return idiom;
	}


	public void setIdiom(String idiom) {
		this.idiom = idiom;
	}


	@Override
	public String toString() {
		return "GameNode [idiomNo=" + idiomNo + ", wxid=" + wxid + ", idiom=" + idiom + "]";
	}

	

}
