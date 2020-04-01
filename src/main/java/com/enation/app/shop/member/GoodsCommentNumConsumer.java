package com.enation.app.shop.member;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enation.app.shop.goods.model.po.Goods;
import com.enation.app.base.AmqpExchange;
import com.enation.app.core.event.IGoodsCommentEvent;
import com.enation.app.shop.comments.model.vo.GoodsCommentMsg;
import com.enation.app.shop.goods.service.IGoodsManager;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.jms.support.goods.GoodsChangeMsg;

/**
 * 商品增加評論數量消费者
 * 
 * @author dongxin
 * @version v1.0
 * @since v6.4.0 2017年12月4日 下午2:03:17
 */
@Component
public class GoodsCommentNumConsumer implements IGoodsCommentEvent {

	@Autowired
	private IGoodsManager goodsManager;
	@Autowired
	private IDaoSupport daoSupport;
	@Autowired
	private AmqpTemplate amqpTemplate;

	@Override
	public void goodsComment(GoodsCommentMsg goodsCommentMsg) {
		if (goodsCommentMsg.getComment_id() != null ) {
			String updatesql = "update es_goods set comment_num=comment_num + 1 where goods_id=?";
			this.daoSupport.execute(updatesql, goodsCommentMsg.getGoods_id());
			// 发送商品消息变化消息
			GoodsChangeMsg goodsChangeMsg = new GoodsChangeMsg(new Integer[] { goodsCommentMsg.getGoods_id() },
					GoodsChangeMsg.UPDATE_OPERATION);
			this.amqpTemplate.convertAndSend(AmqpExchange.GOODS_CHANGE.name(), "goods-change-routingKey", goodsChangeMsg);
		}

	}

}
