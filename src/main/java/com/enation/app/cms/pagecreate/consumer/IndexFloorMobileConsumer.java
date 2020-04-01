package com.enation.app.cms.pagecreate.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enation.app.cms.floor.model.vo.CmsManageMsg;
import com.enation.app.cms.floor.service.IFloorManager;
import com.enation.app.core.event.IGoodsChangeEvent;
import com.enation.app.core.event.IMobileIndexChangeEvent;
import com.enation.framework.jms.support.goods.GoodsChangeMsg;

/**
 * 更改移动端首页缓存数据
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年10月20日 下午8:30:37
 */
@Service
public class IndexFloorMobileConsumer implements IMobileIndexChangeEvent,IGoodsChangeEvent{

	@Autowired
	private IFloorManager floorManager;
	
	@Override
	public void mobileIndexChange(CmsManageMsg operation) {
		
		this.floorManager.getAllCmsFrontMobilePanel(true);
	}

	@Override
	public void goodsChange(GoodsChangeMsg goodsChangeMsg) {
		
		this.floorManager.getAllCmsFrontMobilePanel(true);
	}

}
