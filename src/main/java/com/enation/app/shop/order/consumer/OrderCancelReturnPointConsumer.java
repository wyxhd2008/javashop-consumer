package com.enation.app.shop.order.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enation.app.base.core.model.Member;
import com.enation.app.core.event.IOrderStatusChangeEvent;
import com.enation.app.shop.member.service.IMemberPointManger;
import com.enation.app.shop.promotion.fulldiscount.model.po.FullDiscountGift;
import com.enation.app.shop.trade.model.enums.OrderStatus;
import com.enation.app.shop.trade.model.po.OrderPo;
import com.enation.app.shop.trade.model.vo.Product;
import com.enation.app.shop.trade.support.OrderStatusChangeMessage;
import com.enation.framework.database.IDaoSupport;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 订单取消时退还消费积分
 * @author zjp
 * @version v1.0
 * @since v6.4.1
 * 2017年12月26日 上午9:41:20
 */
@Component
public class OrderCancelReturnPointConsumer implements IOrderStatusChangeEvent{
	@Autowired
	private IDaoSupport daoSupport;
	
	@Autowired
	private IMemberPointManger memberPointManger;
	
	@Override
	public void orderChange(OrderStatusChangeMessage orderMessage) {
		if (orderMessage.getNewStatus().name().equals(OrderStatus.CANCELLED.name())) {
			
			//获取订单中是否存在积分换购商品，将换购商品积分累加
			OrderPo order = orderMessage.getOrder();
			String items_json = order.getItems_json();
			Gson gson = new Gson();
			List<Product> productList = gson.fromJson(items_json, new TypeToken<List<Product>>() {
			}.getType());
			Integer pm = 0;
			for (Product product : productList) {
				Integer point = product.getPoint();
				if(point!=null) {
					pm+=point;
				}
			}
			//更新会员消费积分
			if(pm>0) {
				String sql = "select * from es_member where member_id= ? ";
				Member member = daoSupport.queryForObject(sql, Member.class, order.getMember_id());
				memberPointManger.add(member, 0, "取消订单，归还消费积分", null, pm, 1);
			}
			
			
		}
	}
}
