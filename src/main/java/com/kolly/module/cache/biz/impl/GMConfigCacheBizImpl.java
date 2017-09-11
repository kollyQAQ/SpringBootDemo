package com.kolly.module.cache.biz.impl;

import com.kolly.module.cache.biz.GMConfigCacheBiz;
import com.kolly.module.cache.dao.GMConfigDao;
import com.kolly.module.cache.po.GMConfig;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kolly on 2017/9/11.
 */
@Service
@EnableScheduling
@Lazy(false)
public class GMConfigCacheBizImpl implements GMConfigCacheBiz {

	@Autowired
	private GMConfigDao configDao;

	// V6 配置缓存
	private Map<Long, GMConfig> gmConfigMap = new HashMap<>();

	public GMConfigCacheBizImpl() {
	}

	@Scheduled(initialDelay = 500, fixedRate = 120000)
	public void loadGmConfigJob() {

		String time = "2000-01-01 00:00:00";
		if (!gmConfigMap.isEmpty()) {
			time = DateFormatUtils.format(System.currentTimeMillis() - 600000, "yyyy-MM-dd HH:mm:ss");
		}

		List<GMConfig> configs = configDao.getScoreBoardV6Config(time);
		Map<Long, GMConfig> temp = new HashMap< >();
		for (GMConfig config : configs) {
			temp.put(config.getShopId(), config);
		}

		synchronized (gmConfigMap) {
			gmConfigMap.putAll(temp);
		}
	}

	@Override
	public GMConfig getGMConfigByShopId(long shopId) {
		return gmConfigMap.get(shopId);
	}
}
