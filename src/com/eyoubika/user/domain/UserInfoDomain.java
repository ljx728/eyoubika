package com.eyoubika.user.domain;
public class UserInfoDomain{
	private Integer userId;	//用户id
	private String nickName;	//昵称
	private String status;	//状态
	private String pnumber;	//手机号
	private String userName;	//姓名
	private String identifyNo;	//身份证
	private String email;	//邮箱
	private String regiTime;	//注册时间
	private String sex;	//性别
	private String telephone;	//电话
	private String city;	//省市
	private String address;	//地址
	private String postcode;	//邮编
	private byte[] icon;	//头像
	private String vipLevel;	//vip等级
	private String socialLevel;	//社交等级
	private Integer species;	//金币
	private Float duration;	//时长
	public Integer getUserId(){
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public String getNickName(){
		return nickName;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getPnumber(){
		return pnumber;
	}
	public void setPnumber(String pnumber){
		this.pnumber = pnumber;
	}
	public String getUserName(){
		return userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getIdentifyNo(){
		return identifyNo;
	}
	public void setIdentifyNo(String identifyNo){
		this.identifyNo = identifyNo;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getRegiTime(){
		return regiTime;
	}
	public void setRegiTime(String regiTime){
		this.regiTime = regiTime;
	}
	public String getSex(){
		return sex;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	public String getTelephone(){
		return telephone;
	}
	public void setTelephone(String telephone){
		this.telephone = telephone;
	}
	public String getCity(){
		return city;
	}
	public void setCity(String city){
		this.city = city;
	}
	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public String getPostcode(){
		return postcode;
	}
	public void setPostcode(String postcode){
		this.postcode = postcode;
	}
	public byte[] getIcon(){
		return icon;
	}
	public void setIcon(byte[] icon){
		this.icon = icon;
	}
	public String getVipLevel(){
		return vipLevel;
	}
	public void setVipLevel(String vipLevel){
		this.vipLevel = vipLevel;
	}
	public String getSocialLevel(){
		return socialLevel;
	}
	public void setSocialLevel(String socialLevel){
		this.socialLevel = socialLevel;
	}
	public Integer getSpecies(){
		return species;
	}
	public void setSpecies(Integer species){
		this.species = species;
	}
	public Float getDuration(){
		return duration;
	}
	public void setDuration(Float duration){
		this.duration = duration;
	}
	public String toString() {
		String string = "";
		string += "用户id:\t"+ this.getUserId()+"\n";
		string += "昵称:\t"+ this.getNickName()+"\n";
		string += "状态:\t"+ this.getStatus()+"\n";
		string += "手机号:\t"+ this.getPnumber()+"\n";
		string += "姓名:\t"+ this.getUserName()+"\n";
		string += "身份证:\t"+ this.getIdentifyNo()+"\n";
		string += "邮箱:\t"+ this.getEmail()+"\n";
		string += "注册时间:\t"+ this.getRegiTime()+"\n";
		string += "性别:\t"+ this.getSex()+"\n";
		string += "电话:\t"+ this.getTelephone()+"\n";
		string += "省市:\t"+ this.getCity()+"\n";
		string += "地址:\t"+ this.getAddress()+"\n";
		string += "邮编:\t"+ this.getPostcode()+"\n";
		string += "头像:\t"+ this.getIcon()+"\n";
		string += "vip等级:\t"+ this.getVipLevel()+"\n";
		string += "社交等级:\t"+ this.getSocialLevel()+"\n";
		string += "金币:\t"+ this.getSpecies()+"\n";
		string += "时长:\t"+ this.getDuration()+"\n";
		return string;
	}
	public void init(){
		this.setUserId(null);
		this.setNickName(null);
		this.setStatus(null);
		this.setPnumber(null);
		this.setUserName(null);
		this.setIdentifyNo(null);
		this.setEmail(null);
		this.setRegiTime(null);
		this.setSex(null);
		this.setTelephone(null);
		this.setCity(null);
		this.setAddress(null);
		this.setPostcode(null);
		this.setIcon(null);
		this.setVipLevel(null);
		this.setSocialLevel(null);
		this.setSpecies(null);
		this.setDuration(null);
	}
}
