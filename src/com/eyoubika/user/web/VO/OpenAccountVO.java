package com.eyoubika.user.web.VO;
public class OpenAccountVO{
	private String userId;	//用户标识
	private String token;	//令牌
	private String msg;	//消息
	private String ret;	//返回码
	private String exId;	//交易所ID
	private String status;	//状态
	private String identifyNumber;	//证件号码
	private String openType;	//开户类型
	private String emerName;	//紧急联系人
	private String bankId;	//开户银行
	private String bankPlace;	//开户银行所在地
	private String batchNo;	//批次号
	private String email;	//客户邮箱
	private String clientName;	//客户姓名
	private String userName;	//联系人姓名
	private String emerPnumber;	//紧急联系人手机号码
	private String openNo;	//开户业务编号
	private String pnumber;	//手机号码
	private String identifyType;	//证件号类型
	private String bankAccount;	//银行账号
	private String applyTime;	//申请时间
	private String imgs;		//图片地址
	private String  province; 	//省份
	private String 	city;	//城市
	private String 	street;	//街道
	private String 	channel;	//开户渠道
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getBankPlace() {
		return bankPlace;
	}
	public void setBankPlace(String bankPlace) {
		this.bankPlace = bankPlace;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getExId(){
		return exId;
	}
	public void setExId(String exId){
		this.exId = exId;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getIdentifyNumber(){
		return identifyNumber;
	}
	public void setIdentifyNumber(String identifyNumber){
		this.identifyNumber = identifyNumber;
	}
	public String getOpenType(){
		return openType;
	}
	public void setOpenType(String openType){
		this.openType = openType;
	}
	public String getEmerName(){
		return emerName;
	}
	public void setEmerName(String emerName){
		this.emerName = emerName;
	}
	public String getBankId(){
		return bankId;
	}
	public void setBankId(String bankId){
		this.bankId = bankId;
	}
	public String getBatchNo(){
		return batchNo;
	}
	public void setBatchNo(String batchNo){
		this.batchNo = batchNo;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getClientName(){
		return clientName;
	}
	public void setClientName(String clientName){
		this.clientName = clientName;
	}
	public String getUserName(){
		return userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getEmerPnumber(){
		return emerPnumber;
	}
	public void setEmerPnumber(String emerPnumber){
		this.emerPnumber = emerPnumber;
	}
	public String getOpenNo(){
		return openNo;
	}
	public void setOpenNo(String openNo){
		this.openNo = openNo;
	}
	public String getPnumber(){
		return pnumber;
	}
	public void setPnumber(String pnumber){
		this.pnumber = pnumber;
	}
	public String getIdentifyType(){
		return identifyType;
	}
	public void setIdentifyType(String identifyType){
		this.identifyType = identifyType;
	}
	public String getBankAccount(){
		return bankAccount;
	}
	public void setBankAccount(String bankAccount){
		this.bankAccount = bankAccount;
	}
	public String getRet(){
		return ret;
	}
	public void setRet(String ret){
		this.ret = ret;
	}
	public String getToken(){
		return token;
	}
	public void setToken(String token){
		this.token = token;
	}
	public String getMsg(){
		return msg;
	}
	public void setMsg(String msg){
		this.msg = msg;
	}
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	public String toString() {
		String string = "";
		string += "交易所ID:\t"+ this.getExId()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		string += "证件号码:\t"+ this.getIdentifyNumber()+"\n";
		string += "开户类型:\t"+ this.getOpenType()+"\n";
		string += "紧急联系人:\t"+ this.getEmerName()+"\n";
		string += "开户银行:\t"+ this.getBankId()+"\n";
		string += "批次号:\t"+ this.getBatchNo()+"\n";
		string += "客户邮箱:\t"+ this.getEmail()+"\n";
		string += "客户姓名:\t"+ this.getClientName()+"\n";
		string += "联系人姓名:\t"+ this.getUserName()+"\n";
		string += "紧急联系人手机号码:\t"+ this.getEmerPnumber()+"\n";
		string += "开户业务编号:\t"+ this.getOpenNo()+"\n";
		string += "手机号码:\t"+ this.getPnumber()+"\n";
		string += "证件号类型:\t"+ this.getIdentifyType()+"\n";
		string += "银行账号:\t"+ this.getBankAccount()+"\n";
		string += "用户标识:\t"+ this.getUserId()+"\n";
		string += "令牌:\t"+ this.getToken()+"\n";
		string += "返回码:\t"+ this.getRet()+"\n";
		string += "消息:\t"+ this.getMsg()+"\n";
		return string;
	}
}
