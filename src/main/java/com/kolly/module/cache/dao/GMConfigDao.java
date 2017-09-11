package com.kolly.module.cache.dao;


import com.kolly.common.annotation.MyBatisRepository;
import com.kolly.module.cache.po.GMConfig;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@MyBatisRepository
public interface GMConfigDao {

	@Select("SELECT * FROM t_wb_gmclient_sb_config_ex a,t_wb_gmclient_sb_config b WHERE a.shopId = b.shopId AND (a.updateTime > #{time} or b.updateTime > #{time})")
	List<GMConfig> getScoreBoardV6Config(String time);
}
