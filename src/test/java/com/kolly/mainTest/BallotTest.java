package com.kolly.mainTest;

import com.kolly.common.utils.restful.RestClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kolly on 2017/9/11.
 * 刷票程序
 */
public class BallotTest {

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 100; i++) {
			String url = "http://aliyun.wechatpen.com/microvote/handler/AppVoteHandler.ashx";
			HttpHeaders headers = new HttpHeaders();
			List<String> cookies = new ArrayList<>();
			cookies.add("MICROVOTE_wgateid=k=wgateid&v=o_MexjuUbRqAYD3u4oQe-" + RandomStringUtils.random(7, false, true));
			cookies.add("ASP.NET_SessionId=at1qwzduskrq0ua3cogqzrtr");
			cookies.add("CaptchaCode=74C1");
			cookies.add("MICROVOTE_UserID_1403117813=k=UserID_1403117813&v=465789");
			cookies.add("MICROVOTE_mp_wxuser_id_1403117813=k=mp_wxuser_id_1403117813&v=gh_9025252b0d52");
			cookies.add("MICROVOTE_mp_wxuser_name_1403117813=k=mp_wxuser_name_1403117813&v=惟楚競才");
			headers.put("Cookie", cookies);

			Map<String, String> map = new HashMap<>();
			map.put("action", "AddOption");
			map.put("optionlist", "112958");
			map.put("app_case_id", "1403117813");
			map.put("cip", "183.13.102.112");
			map.put("cid", "440330");
			map.put("cname", "广东省深圳市");
			map.put("code", "");

			ResponseEntity<String> responseEntity = RestClient.exchange(url, HttpMethod.POST, headers, map, String.class);
			System.out.println(responseEntity.getBody());
			Thread.sleep(3000);
		}
	}
}
