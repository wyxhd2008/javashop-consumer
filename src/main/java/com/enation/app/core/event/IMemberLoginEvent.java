package com.enation.app.core.event;

import java.util.Map;

import com.enation.app.shop.member.model.vo.MemberLoginMsg;

/**
 * 会员登录事件
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年9月29日 下午5:06:07
 */
public interface IMemberLoginEvent {

	/**
	 * 会员登录
	 * @param memberLoginMsg
	 */
	public void memberLogin(MemberLoginMsg memberLoginMsg);
}
