package com.eyoubika.common;

/*==========================================================================================*
 * Description:	排序对象。
 * Class:		RankData
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年9月29日 下午2:08:17
 *==========================================================================================*/
public class RankData {
	private String id;
	private double dataD;
	private String dataS;
	private int dataI;
	private String dataM;

	RankData(String id, double dataD) {
		this.id = id;
		this.dataD = dataD;
	}

	RankData(String id, double dataD, String dataM) {
		this.id = id;
		this.dataD = dataD;
		this.dataM = dataM;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getDataD() {
		return dataD;
	}

	public void setDataD(double dataD) {
		this.dataD = dataD;
	}

	public String getDataS() {
		return dataS;
	}

	public void setDataS(String dataS) {
		this.dataS = dataS;
	}

	public int getDataI() {
		return dataI;
	}

	public void setDataI(int dataI) {
		this.dataI = dataI;
	}

	public String getDataM() {
		return dataM;
	}

	public void setDataM(String dataM) {
		this.dataM = dataM;
	}

	public String toString() {
		String string = "";
		string += "Id:\t" + this.getId() + "\n";
		string += "dataD:\t" + this.getDataD() + "\n";
		string += "dataI:\t" + this.getDataI() + "\n";
		string += "dataS:\t" + this.getDataS() + "\n";
		string += "dataM:\t" + this.getDataM() + "\n";
		return string;
	}

	public void init() {
		this.setId(null);
		this.setDataD(0);
		this.setDataI(0);
		this.setDataS(null);
		this.setDataM(null);
	}
}
