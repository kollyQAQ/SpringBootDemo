package com.kolly.module.cache.po;


public class GMConfig {

	private long shopId;
	private String sBarID = "";
	private String sBarName = "";
	private String feeSysId = "";
	private String netBarId = "";
	private int lgjMaxGrant;

	public GMConfig() {
	}

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public String getFeeSysId() {
		return feeSysId;
	}

	public void setFeeSysId(String feeSysId) {
		this.feeSysId = feeSysId;
	}

	public String getNetBarId() {
		return netBarId;
	}

	public void setNetBarId(String netBarId) {
		this.netBarId = netBarId;
	}

	public int getLgjMaxGrant() {
		return lgjMaxGrant;
	}

	public void setLgjMaxGrant(int lgjMaxGrant) {
		this.lgjMaxGrant = lgjMaxGrant;
	}

	public String getsBarID() {
		return sBarID;
	}

	public void setsBarID(String sBarID) {
		this.sBarID = sBarID;
	}

	public String getsBarName() {
		return sBarName;
	}

	public void setsBarName(String sBarName) {
		this.sBarName = sBarName;
	}
}
