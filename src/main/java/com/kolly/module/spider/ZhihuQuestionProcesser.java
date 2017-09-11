package com.kolly.module.spider;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by kolly on 2017/9/4.
 */
public class ZhihuQuestionProcesser implements PageProcessor {

	//设置编码,超时时间,重试次数,cookie
	private Site site = Site.me().setRetryTimes(10).setSleepTime(5000).setTimeOut(5000)
			.addCookie("Domain", "zhihu.com")
			//你的知乎cookie
			.addCookie("z_c0", "2|1:0|10:1504491684|4:z_c0|92:Mi4xaVVoMEFnQUFBQUFBY01MQkxiVFFDeWNBQUFDRUFsVk5wRVhVV1FBOXl2S3dBV3U0OE1jeTI2WDNIdUJtOTNZS1ln|6f16567691a9e0eb164b4e7685f0c4b7a6ab2992b48959af71a02d8813705b8e")
			.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");

	//知乎问题url模版 https://www.zhihu.com/question/20902967
	private static final String URL_QUESTION = "^https:\\/\\/www\\.zhihu\\.com\\/question\\/\\d+$";

	//知乎回答url模版 https://www.zhihu.com/question/19647535/answer/110944270
	private static final String URL_ANSWER = "^https:\\/\\/www\\.zhihu\\.com\\/question\\/\\d+\\/answer\\/\\d+$";

	//图片存储地址
	private static String filePath = "D:\\zhihuSpider\\";

	//抓取问题的Id
	private static String questionId = "42056525";

	//抓取回答要求的最低赞数量
	private static int voteMin = 50;

	public static void main(String[] args) {
		Spider.create(new ZhihuQuestionProcesser())
				.addUrl("https://www.zhihu.com/question/" + questionId)
				.thread(10)
				.run();
	}

	@Override
	public void process(Page page) {

		//页面为问题页，则将答案链接循环加入Downloader
		if (page.getUrl().regex(URL_QUESTION).match()) {
			int offset = 0;
			int limit = 20;
			String url = "https://www.zhihu.com/api/v4/questions/" + questionId + "/answers?include=data%5B*%5D.is_normal" +
					"%2Cis_sticky%2Ccollapsed_by%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Ceditable_content" +
					"%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Cmark_infos%2Ccreated_time%2Cupdated_time" +
					"%2Crelationship.is_authorized%2Cis_author%2Cvoting%2Cis_thanked%2Cis_nothelp%2Cupvoted_followees%3Bdata" +
					"%5B*%5D.author.badge%5B%3F(type%3Dbest_answerer)%5D.topics" +
					"&offset=" + offset + "&limit=" + limit + "&sort_by=default";
			page.addTargetRequest(url);
			//某个具体答案详情页面，则获取详情信息 。
		} else if (page.getUrl().regex(URL_ANSWER).match()) {
			int voteNum = NumberUtils.toInt(page.getHtml().xpath("//div[@class=ContentItem-actions]//button/text()").toString());
			//过滤赞同数低的答案
			if (voteNum >= voteMin) {
				String answerUrl = page.getUrl().toString();
				String answerNo = answerUrl.substring(answerUrl.lastIndexOf("/"));
				String questionTitle = page.getHtml().xpath("//h1[@class=QuestionHeader-title]/text()").toString();
				List<String> urlList = page.getHtml().xpath("//div[@class=RichContent-inner]//img/@src").all();
				urlList.forEach(url -> downloadPicture(url, filePath + questionTitle + questionId,
						answerNo + "-" + RandomStringUtils.random(4, false, true) + url.substring(url.lastIndexOf(".")))
				);
			}
		} else {
			//将回答url放入Downloader
			List<String> ids = new JsonPathSelector("$.data[*].id").selectList(page.getRawText());
			for (String id : ids) {
				String answerUrl = "https://www.zhihu.com/question/" + questionId + "/answer/" + id;
				page.addTargetRequest(answerUrl);
			}
			//寻找剩余回答
			int total = NumberUtils.toInt(new JsonPathSelector("$.paging.totals").select(page.getRawText()));
			int time = total / 20;
			for (int i = 1; i <= time; i++) {
				int offset = i * 20;
				int limit = 20;
				String url = "https://www.zhihu.com/api/v4/questions/" + questionId + "/answers?include=data%5B*%5D.is_normal" +
						"%2Cis_sticky%2Ccollapsed_by%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Ceditable_content" +
						"%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Cmark_infos%2Ccreated_time%2Cupdated_time" +
						"%2Crelationship.is_authorized%2Cis_author%2Cvoting%2Cis_thanked%2Cis_nothelp%2Cupvoted_followees%3Bdata" +
						"%5B*%5D.author.badge%5B%3F(type%3Dbest_answerer)%5D.topics" +
						"&offset=" + offset + "&limit=" + limit + "&sort_by=default";
				page.addTargetRequest(url);
			}
		}
	}

	@Override
	public Site getSite() {
		site.setCharset("UTF-8");
		return site;
	}


	private static void downloadPicture(String urlString, String savePath, String filename) {

		try {

			if (!StringUtils.startsWith(urlString, "http")) {
				return;
			}

			System.out.println("downloading img " + urlString);

			// 构造URL
			URL url = new URL(urlString);
			// 打开连接
			URLConnection con = url.openConnection();
			//设置请求超时为5s
			con.setConnectTimeout(5 * 1000);
			// 输入流
			InputStream is = con.getInputStream();

			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			File path = new File(savePath);
			if (!path.exists()) {
				if (!path.mkdir()) {
					throw new RuntimeException("mkdir " + path + " failed");
				}
			}

			// 开始读取
			OutputStream os = new FileOutputStream(savePath + "/" + filename);
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}

			// 完毕，关闭所有链接
			os.close();
			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
