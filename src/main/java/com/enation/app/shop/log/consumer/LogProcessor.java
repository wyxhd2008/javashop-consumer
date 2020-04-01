package com.enation.app.shop.log.consumer;
 
import com.enation.framework.model.LogStore;
 
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.RabbitListener; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;  
import com.enation.framework.database.IDaoSupport; 
import com.enation.framework.model.Log;

/**
 * 日志记录消费者
 * @author fk
 * @version v1.0
 * @since v6.2
 * 2016年12月12日 上午10:14:25
 */
@Service
public class LogProcessor{
	
	@Autowired
	private IDaoSupport daoSupport;

	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "STORE_LOG_EXCHANGE_QUEUE"),
			exchange = @Exchange(value = "STORE_LOG_EXCHANGE", type = ExchangeTypes.FANOUT)
	))
	@Transactional(propagation = Propagation.REQUIRED)
	public void process(LogStore log) {
			try {
				daoSupport.insert("es_store_logs", log);
			}catch (Exception e) {
				// TODO: handle exception
			}
	}
	

	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "ADMIN_LOG_EXCHANGE_QUEUE"),
			exchange = @Exchange(value = "ADMIN_LOG_EXCHANGE", type = ExchangeTypes.FANOUT)
	))
	@Transactional(propagation = Propagation.REQUIRED)
	public void processAdmin(Log adminlog) {
		try {
			daoSupport.insert("es_admin_logs", adminlog);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	
}
