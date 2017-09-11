package com.kolly.module.cache.biz;


import com.kolly.module.cache.po.GMConfig;

public interface GMConfigCacheBiz {

	GMConfig getGMConfigByShopId(long shopId);

}
