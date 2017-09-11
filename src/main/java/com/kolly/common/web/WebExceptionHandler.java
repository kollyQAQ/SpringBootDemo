package com.kolly.common.web;

import com.kolly.common.BasicResult;
import com.kolly.common.exception.BizException;
import com.kolly.common.mapper.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class WebExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler({ServletException.class})
	public void handleServletException(HttpServletRequest request, HttpServletResponse response, ServletException e)
			throws IOException {
		logger.error("ServletException occurs during url:{}", request.getRequestURL(), e);
		outputMessage(response, -1, "ServletException:" + e.getMessage());
	}

	@ExceptionHandler(BizException.class)
	public void handleBizException(HttpServletRequest request, HttpServletResponse response, BizException e)
			throws IOException {
		logger.error("BizException occurs during :{}", request.getRequestURI(), e);
		outputMessage(response, e.getErrCode(), "BizException:" + e.getMessage());
	}

	@ExceptionHandler({Error.class, Exception.class, Throwable.class})
	public void exception(HttpServletRequest request, HttpServletResponse response, Throwable e) throws IOException {
		logger.error("Unknown Exception occurs during :{}", request.getRequestURI(), e);
		outputMessage(response, -1, "Unknown Exception:" + e.getMessage());
	}

	private void outputMessage(HttpServletResponse response, long errCode, String errMsg) throws IOException {
		BasicResult result = BasicResult.createFailResult(errCode, errMsg);
		String json = new JsonMapper().toJson(result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		ServletOutputStream os = response.getOutputStream();
		os.write(json.getBytes("utf-8"));
	}
}