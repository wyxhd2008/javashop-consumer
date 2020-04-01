package com.enation.app.shop.goods;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enation.app.core.event.IGoodsChangeEvent;
import com.enation.app.shop.goods.model.vo.GoodsVo;
import com.enation.app.shop.goods.service.IGoodsManager;
import com.enation.app.shop.goods.service.IGoodsQueryManager;
import com.enation.app.shop.shop.apply.service.IShopManager;
import com.enation.framework.jms.support.goods.GoodsChangeMsg;
/**
 * 
 * 商品修改更新店铺商品数量
 * @author zjp
 * @version v6.4.1
 * @since v6.4.1
 * 2018年1月8日 下午4:17:20
 */
@Component
public class ShopGoodsCountConsumer implements IGoodsChangeEvent{
	
	@Autowired
	private IGoodsQueryManager goodsQueryManager;
	
	@Autowired
	private IGoodsManager goodsManager;
	
	@Autowired
	private IShopManager shopManager;
	
	@Override
	public void goodsChange(GoodsChangeMsg goodsChangeMsg) {
		//获取商品id
		Integer[] goods_ids = goodsChangeMsg.getGoods_ids();
		if(goods_ids.length>0) {
			Integer goods_id = goods_ids[0];
			//获取商品店铺id
			GoodsVo goods = goodsManager.getFromCache(goods_id);
			Integer sellerGoodsCount = goodsQueryManager.getSellerGoodsCount(goods.getSeller_id());
			//更新店铺信息
			Map map = new HashMap();
			map.put("shop_id", goods.getSeller_id());
			map.put("goods_num", sellerGoodsCount.toString());
			shopManager.editShop(map);
		}
	}
}
