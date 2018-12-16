package com.eyoubika.user.domain;
public class OpenAccountPersDomain{
	private String openNo;	//开户业务编号
	private Integer userId;	//用户id
	private String clientName;	//客户姓名
	private String identifyType;	//证件号类型
	private String identifyNumber;	//证件号码
	private String pnumber;	//手机号码
	private String email;	//客户邮箱
	private String province;	//省份
	private String city;	//城市
	private String street;	//街道
	private String status;	//状态
	private String exId;	//交易所ID
	private String bankId;	//开户银行
	private String bankPlace;	//开户银行所在地
	private String bankAccount;	//银行账号
	private String emerName;	//紧急联系人
	private String emerPnumber;	//紧急联系人手机号码
	private String applyDate;	//申请日期
	private String applyTime;	//申请时间
	private String operDate;	//处理日期
	private String operTime;	//处理时间
	private String operName;	//处理人
	private String channel;		//开户渠道
	
	
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
	public String getIdentifyType(){
		return identifyType;
	}
	public void setIdentifyType(String identifyType){
		this.identifyType = identifyType;
	}
	public String getIdentifyNumber(){
		return identifyNumber;
	}
	public void setIdentifyNumber(String identifyNumber){
		this.identifyNumber = identifyNumber;
	}
	public String getPnumber(){
		return pnumber;
	}
	public void setPnumber(String pnumber){
		this.pnumber = pnumber;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getProvince(){
		return province;
	}
	public void setProvince(String province){
		this.province = province;
	}
	public String getCity(){
		return city;
	}
	public void setCity(String city){
		this.city = city;
	}
	public String getStreet(){
		return street;
	}
	public void setStreet(String street){
		this.street = street;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getExId(){
		return exId;
	}
	public void setExId(String exId){
		this.exId = exId;
	}
	public String getBankId(){
		return bankId;
	}
	public void setBankId(String bankId){
		this.bankId = bankId;
	}
	public String getBankPlace(){
		return bankPlace;
	}
	public void setBankPlace(String bankPlace){
		this.bankPlace = bankPlace;
	}
	public String getBankAccount(){
		return bankAccount;
	}
	public void setBankAccount(String bankAccount){
		this.bankAccount = bankAccount;
	}
	public String getEmerName(){
		return emerName;
	}
	public void setEmerName(String emerName){
		this.emerName = emerName;
	}
	public String getEmerPnumber(){
		return emerPnumber;
	}
	public void setEmerPnumber(String emerPnumber){
		this.emerPnumber = emerPnumber;
	}
	public String getApplyDate(){
		return applyDate;
	}
	public void setApplyDate(String applyDate){
		this.applyDate = applyDate;
	}
	public String getApplyTime(){
		return applyTime;
	}
	public void setApplyTime(String applyTime){
		this.applyTime = applyTime;
	}
	public String getOperDate(){
		return operDate;
	}
	public void setOperDate(String operDate){
		this.operDate = operDate;
	}
	public String getOperTime(){
		return operTime;
	}
	public void setOperTime(String operTime){
		this.operTime = operTime;
	}
	public String getOperName(){
		return operName;
	}
	public void setOperName(String operName){
		this.operName = operName;
	}
	public String toString() {
		String string = "";
		string += "开户业务编号:\t"+ this.getOpenNo()+"\n";
		string += "用户id:\t"+ this.getUserId()+"\n";
		string += "客户姓名:\t"+ this.getClientName()+"\n";
		string += "证件号类型:\t"+ this.getIdentifyType()+"\n";
		string += "证件号码:\t"+ this.getIdentifyNumber()+"\n";
		string += "手机号码:\t"+ this.getPnumber()+"\n";
		string += "客户邮箱:\t"+ this.getEmail()+"\n";
		string += "省份:\t"+ this.getProvince()+"\n";
		string += "城市:\t"+ this.getCity()+"\n";
		string += "街道:\t"+ this.getStreet()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		string += "交易所ID:\t"+ this.getExId()+"\n";
		string += "开户银行:\t"+ this.getBankId()+"\n";
		string += "开户银行所在地:\t"+ this.getBankPlace()+"\n";
		string += "银行账号:\t"+ this.getBankAccount()+"\n";
		string += "紧急联系人:\t"+ this.getEmerName()+"\n";
		string += "紧急联系人手机号码:\t"+ this.getEmerPnumber()+"\n";
		string += "申请日期:\t"+ this.getApplyDate()+"\n";
		string += "申请时间:\t"+ this.getApplyTime()+"\n";
		string += "处理日期:\t"+ this.getOperDate()+"\n";
		string += "处理时间:\t"+ this.getOperTime()+"\n";
		string += "处理人:\t"+ this.getOperName()+"\n";
		string += "渠道:\t"+ this.getChannel()+"\n";
		return string;
	}
	
	public void init(){
		this.setApplyDate(null);
		this.setApplyTime(null);
		this.setBankAccount(null);
		this.setBankId(null);
		this.setBankPlace(null);
		this.setCity(null);
		this.setClientName(null);
		this.setEmail(null);
		this.setEmerName(null);
		this.setEmerPnumber(null);
		this.setExId(null);
		this.setIdentifyNumber(null);
		this.setIdentifyType(null);
		this.setOpenNo(null);
		this.setOperDate(null);
		this.setOperName(null);
		this.setOperTime(null);
		this.setPnumber(null);
		this.setProvince(null);
		this.setStatus(null);
		this.setStreet(null);
		this.setUserId(null);
		this.setChannel(null);
	}
}
