/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.kolly.common.exception;


/**
 * 业务层Exception定义
 * 
 * @author kolly
 */
public class BizException extends BaseException {

	private static final long serialVersionUID = 1668540896739748081L;

	public BizException(long errCode, String errMsg) {
		super(errCode, errMsg);
	}

}
