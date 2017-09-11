package com.kolly.common;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 接口调用结果的基本类
 * 
 * 注意： 字段名一定要用双引号包括，否则在一些框架库中会出现兼容性问题
 * </p>
 * 
 * @author <a href="mailto:leasen@gmail.com">Leasenli</a>
 * @version 1.0
 */
public class BasicResult {

	/**
	 * 接口错误码字段 取值0标识正常返回数据，其他的取值由具体业务根据实际情况来决定
	 */
	long errCode = 0;

	/**
	 * 用于返回码上报，理论上取值范围应该大于错误码，如果未特殊定义时可以等于错误码字段的内容 例如：
	 * 接口正常返回，错误码为0，但返回码可以为：1列表空，2列表大于100条，等等以此类推的概念，一般当产品有特殊统计需求时才单独处理
	 */
	long retCode = 0;

	/**
	 * 错误信息内容 没有错误的时候为空
	 */
	String msg = "";

	/**
	 * 请求标识 用于前端区分这是哪一次的请求，从输入参数中直接读取，没有参数时为空；接受的字符范围为：字母数字下划线小数点
	 */
	String dtag = null;

	/**
	 * 接口业务真正要返回的数据内容 可以为一个也可以为多个，由接口开发人员根据实际情况来设计
	 */
	Map<String, Object> data = new HashMap<>();

	private BasicResult() {
	}

	private BasicResult(long errCode, String msg) {
		this.errCode = errCode;
		this.msg = msg;
	}

	private BasicResult(long errCode, long retCode, String msg) {
		this.errCode = errCode;
		this.retCode = retCode;
		this.msg = msg;
	}

	/**
	 * 创建返回结果
	 */
	public static BasicResult createResult(long errCode, String msg) {
		return new BasicResult(errCode, 0, msg);
	}
	/**
	 * 创建成功的返回结果
	 */
	public static BasicResult createSuccResult() {
		BasicResult result = new BasicResult(0, 0, "");
		return result;
	}

	/**
	 * 创建成功的返回结果
	 */
	public static BasicResult createSuccResult(String msg) {
		BasicResult result = createSuccResult();
		result.setMsg(msg);
		return result;
	}

	/**
	 * 创建失败的返回结果
	 */
	public static BasicResult createFailResult(long errCode, String msg) {
		return new BasicResult(errCode, 0, msg);
	}

	/**
	 * 增加dtag 请求标识信息
	 */
	public BasicResult addTag(String dtag) {
		this.dtag = dtag;
		return this;
	}

	/**
	 * 增加返回码
	 */
	public BasicResult addRetCode(long retCode) {
		this.retCode = retCode;
		return this;
	}

	/**
	 * 增加业务数据
	 */
	public BasicResult addData(String key, Object value) {
		this.data.put(key, value);
		return this;
	}

	/**
	 * 增加消息
	 */
	public BasicResult addMsg(String msg) {
		this.setMsg(msg);
		return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String toJSONString() {
		return JSON.toJSONString(this);
	}

	public long getErrCode() {
		return errCode;
	}

	public void setErrCode(long errCode) {
		this.errCode = errCode;
	}

	public long getRetCode() {
		return retCode;
	}

	public void setRetCode(long retCode) {
		this.retCode = retCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDtag() {
		return dtag;
	}

	public void setDtag(String dtag) {
		this.dtag = dtag;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
