package io.uouo.wechatbot.domain;

/**
 * @author: [青衫] 'QSSSYH@QQ.com'
 * @Date: 2021-03-16 18:48
 * @Description: < 描述 >
 */
public class ResponseMsg {
    /** 消息内容 */
    private String content;
	/** 消息id */
    private String id;
    /** 消息id1 */
    private String id1;
    /** 消息id2 */
    private String id2;
    /** 消息id3 */
    private String id3;
    /** 消息srvid */
    private String srvid;
    /** 消息id3 */
    private String time;
    /** 发送消息类型 */
    private Integer type;
    /** 接收消息人的 微信原始id */
    private String wxid;
    /** 接收人 */
    private String receiver;
    /** 发送人 */
    private String sender;
    /** 状态 */
    private String status;
    
    
    public String getId1() {
		return id1;
	}

	public void setId1(String id1) {
		this.id1 = id1;
	}

	public String getId2() {
		return id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}

	public String getId3() {
		return id3;
	}

	public void setId3(String id3) {
		this.id3 = id3;
	}

	public String getSrvid() {
		return srvid;
	}

	public void setSrvid(String srvid) {
		this.srvid = srvid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ResponseMsg [content=" + content + ", id=" + id + ", id1=" + id1 + ", id2=" + id2 + ", id3=" + id3
				+ ", srvid=" + srvid + ", time=" + time + ", type=" + type + ", wxid=" + wxid + ", receiver=" + receiver
				+ ", sender=" + sender + ", status=" + status + "]";
	}

}
