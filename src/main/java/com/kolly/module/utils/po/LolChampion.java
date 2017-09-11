package com.kolly.module.utils.po;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LolChampion {

	private String championId;
	private String cnName = "";
	private String enName = "";

	public LolChampion() {
	}

	public LolChampion(String championId, String cnName, String enName) {
		this.cnName = cnName;
		this.enName = enName;
		this.championId = championId;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getChampionId() {
		return championId;
	}

	public void setChampionId(String championId) {
		this.championId = championId;
	}

	public String getAvatar() {
		return "";
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String toJSONString() {
		return JSON.toJSONString(this);
	}
}
