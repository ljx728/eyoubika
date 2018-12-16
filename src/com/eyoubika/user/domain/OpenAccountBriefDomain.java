package com.eyoubika.user.domain;
public class OpenAccountBriefDomain{
	private String openNo;	//开户业务编号
	private Integer userId;	//用户id
	private String clientName;	//企业名称
	private String pnumber;	//联系人手机号码
	private String applyDate;	//申请日期
	private String openType;	//开户类型
	private String status;	//审核状态
	private String channel;	//开户渠道
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getOpenNo(){
		return openNo;
	}
	public void setOpenNo(String openNo){
		this.openNo = openNo;
	}
	public Integer getUserId(){
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public String getClientName(){
		return clientName;
	}
	public void setClientName(String clientName){
		this.clientName = clientName;
	}
	public String getPnumber(){
		return pnumber;
	}
	public void setPnumber(String pnumber){
		this.pnumber = pnumber;
	}
	public String getApplyDate(){
		return applyDate;
	}
	public void setApplyDate(String applyDate){
		this.applyDate = applyDate;
	}
	public String getOpenType(){
		return openType;
	}
	public void setOpenType(String openType){
		this.openType = openType;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String toString() {
		String string = "";
		string += "开户业务编号:\t"+ this.getOpenNo()+"\n";
		string += "用户id:\t"+ this.getUserId()+"\n";
		string += "企业名称:\t"+ this.getClientName()+"\n";
		string += "联系人手机号码:\t"+ this.getPnumber()+"\n";
		string += "申请日期:\t"+ this.getApplyDate()+"\n";
		string += "开户类型:\t"+ this.getOpenType()+"\n";
		string += "审核状态:\t"+ this.getStatus()+"\n";
		return string;
	}
	public void init(){
		this.setApplyDate(null);
		this.setClientName(null);
		this.setOpenNo(null);
		this.setOpenType(null);
		this.setPnumber(null);
		this.setStatus(null);
		this.setUserId(null);
		this.setChannel(null);
	}
}
