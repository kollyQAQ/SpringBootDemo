package com.kolly;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class SpringBootDemoApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testHelloWorld() {
		ResponseEntity entity = this.restTemplate.getForEntity("/getUser", String.class);
		System.out.println(entity.getBody());
	}

}
