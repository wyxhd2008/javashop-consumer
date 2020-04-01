package com.enation.app.core.receiver;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enation.app.core.event.IMemberLoginEvent;
import com.enation.app.shop.member.model.vo.MemberLoginMsg;

/**
 * 会员登录消费者
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年9月29日 下午5:05:14
 */
@Component
public class MemberLoginReceiver {

	protected final Logger logger = Logger.getLogger(getClass());
	
	@Autowired(required=false)
	private List<IMemberLoginEvent> events;
	
	/**
	 * 会员登录
	 * @param member_id
	 */
	public void memberLogin(MemberLoginMsg memberLoginMsg){
		
		try{
			if(events!=null){
				for(IMemberLoginEvent event : events){
					event.memberLogin(memberLoginMsg);
				}
			}
		} catch (Exception e) {
			this.logger.error("处理会员登录消息出错",e);
			e.printStackTrace();
		}
	}
}
