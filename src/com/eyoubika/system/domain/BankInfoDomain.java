package com.eyoubika.system.domain;
public class BankInfoDomain{
	private String bankId;	//银行id
	private String bankName;	//银行名称
	public String getBankId(){
		return bankId;
	}
	public void setBankId(String bankId){
		this.bankId = bankId;
	}
	public String getBankName(){
		return bankName;
	}
	public void setBankName(String bankName){
		this.bankName = bankName;
	}
	public String toString() {
		String string = "";
		string += "银行id:\t"+ this.getBankId()+"\n";
		string += "银行名称:\t"+ this.getBankName()+"\n";
		return string;
	}
}
