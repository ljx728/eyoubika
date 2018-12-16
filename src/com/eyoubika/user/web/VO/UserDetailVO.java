package com.eyoubika.user.web.VO;
import com.eyoubika.common.BaseVO;
/*==========================================================================================*
 * Description:	定义了用户视图数据模型
 * Class:		UserVO
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-21 14:10:36
 *==========================================================================================*/
public class UserDetailVO {
	private String email;
	private String nickName;

	private String userName;	//姓名
	private String identifyNo;	//身份证
	private String sex;	//性别
	private String telephone;	//电话
	private String city;	//省市
	private String address;	//地址
	private String postcode;	//邮编
	//private byte[] icon;	//头像
	private String vipLevel;	//vip等级
	private String socialLevel;	//社交等级
	private String species;	//金币
	
	protected String ret;
	protected String msg;
	protected String userId;
	protected String token;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdentifyNo() {
		return identifyNo;
	}
	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}
	public String getSocialLevel() {
		return socialLevel;
	}
	public void setSocialLevel(String socialLevel) {
		this.socialLevel = socialLevel;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public void init(){
		this.setUserId(null);
		this.setToken(null);
		this.setRet(null);
		this.setMsg(null);
		this.setUserName(null);
		this.setIdentifyNo(null);
		this.setSex(null);
		this.setTelephone(null);
		this.setCity(null);
		this.setAddress(null);
		this.setPostcode(null);
		this.setVipLevel(null);
		this.setSocialLevel(null);
		this.setSpecies(null);
	
	}
}