package com.eyoubika.user.web.VO;
public class QueryOpenAccountVO{
	private String userId;	//用户标识
	private String token;	//令牌
	private String msg;	//消息
	private String ret;	//返回码
	
	private String status;	//状态
	private String openType;	//开户类型
	private String channel;	//开户渠道
	private String clientName;	//客户姓名
	private String emerPnumber;	//紧急联系人手机号码
	private String openNo;	//开户业务编号
	private String pnumber;	//手机号码
	private String applyTime;	//申请时间
	private String applyDate;	//申请日期
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOpenType() {
		return openType;
	}
	public void setOpenType(String openType) {
		this.openType = openType;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getEmerPnumber() {
		return emerPnumber;
	}
	public void setEmerPnumber(String emerPnumber) {
		this.emerPnumber = emerPnumber;
	}
	public String getOpenNo() {
		return openNo;
	}
	public void setOpenNo(String openNo) {
		this.openNo = openNo;
	}
	public String getPnumber() {
		return pnumber;
	}
	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	
}
