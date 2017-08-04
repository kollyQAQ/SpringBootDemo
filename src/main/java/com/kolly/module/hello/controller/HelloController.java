package com.kolly.module.hello.controller;

import com.kolly.module.hello.po.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kolly on 2017/8/4.
 */
@RestController
public class HelloController {

	@RequestMapping("/hello")
	public String hello() {
		return "Hello World";
	}

	@RequestMapping("/getUser")
	public User getUser() {
		return new User("kolly", 23);
	}
}
