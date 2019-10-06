package org.jim.common.message;

import org.jim.common.listener.ImBindListener;
import org.jim.common.packets.ChatBody;
import org.jim.common.packets.Group;
import org.jim.common.packets.User;
import org.jim.common.packets.UserMessageData;

import java.util.List;

/**
 * @author WChao
 * @date 2018年4月9日 下午4:31:51
 */
public interface MessageHelper {
	/**
	 * 获取im群组、人员绑定监听器;
	 * @return
	 */
	ImBindListener getBindListener();

	/**
	 * 判断用户是否在线
	 * @param userId
	 * @return
	 */
	boolean isOnline(String userId);

	/**
	 * 获取群组所有成员信息(根据type区分在线还是离线)
	 * @param groupId
	 * @param type(0:所有在线用户,1:所有离线用户,2:所有用户[在线+离线])
	 * @return
	 */
	Group getGroupUsers(String groupId, Integer type);

	/**
	 * 获取群组所有成员信息（在线+离线)
	 * @param userId
	 * @param type(0:所有在线用户,1:所有离线用户,2:所有用户[在线+离线])
	 * @return
	 */
	List<Group> getAllGroupUsers(String userId, Integer type);

	/**
	 * 获取好友分组所有成员信息
	 * @param userId
	 * @param friendGroupId
	 * @param type(0:所有在线用户,1:所有离线用户,2:所有用户[在线+离线])
	 * @return
	 */
	Group getFriendUsers(String userId, String friendGroupId, Integer type);

	/**
	 * 获取好友分组所有成员信息
	 * @param userId
	 * @param type(0:所有在线用户,1:所有离线用户,2:所有用户[在线+离线])
	 * @return
	 */
	List<Group> getAllFriendUsers(String userId, Integer type);

	/**
	 * 根据在线类型获取用户信息;
	 * @param userId
	 * @param type(0:所有在线用户,1:所有离线用户,2:所有用户[在线+离线])
	 * @return
	 */
	User getUserByType(String userId, Integer type);

	/**
	 * 添加群组成员
	 * @param userid
	 * @param groupId
	 */
	void addGroupUser(String userid, String groupId);

	/**
	 * 获取群组所有成员;
	 * @param groupId
	 * @return
	 */
	List<String> getGroupUsers(String groupId);

	/**
	 * 获取用户拥有的群组;
	 * @param userId
	 * @return
	 */
	List<String> getGroups(String userId);

	/**
	 * 消息持久化写入
	 * @param timelineTable
	 * @param timelineId
	 * @param chatBody
	 */
	void writeMessage(String timelineTable, String timelineId, ChatBody chatBody);

	/**
	 * 移除群组用户
	 * @param userId
	 * @param groupId
	 */
	void removeGroupUser(String userId, String groupId);

	/**
	 * 获取与指定用户离线消息;
	 * @param userId
	 * @param fromUserId
	 * @return
	 */
	UserMessageData getFriendsOfflineMessage(String userId, String fromUserId);

	/**
	 * 获取与所有用户离线消息;
	 * @param userId
	 * @return
	 */
	UserMessageData getFriendsOfflineMessage(String userId);

	/**
	 * 获取用户指定群组离线消息;
	 * @param userId
	 * @param groupId
	 * @return
	 */
	UserMessageData getGroupOfflineMessage(String userId, String groupId);

	/**
	 * 获取与指定用户历史消息;
	 * @param userId
	 * @param fromUserId
	 * @param beginTime 消息区间开始时间
	 * @param endTime 消息区间结束时间
	 * @param offset 分页偏移量
	 * @param count 数量
	 * @return
	 */
	UserMessageData getFriendHistoryMessage(String userId, String fromUserId, Double beginTime, Double endTime, Integer offset, Integer count);
	
	/**
	 * 获取与指定群组历史消息;
	 * @param userId
	 * @param groupid
	 * @param beginTime 消息区间开始时间
	 * @param endTime 消息区间结束时间
	 * @param offset 分页偏移量
	 * @param count 数量
	 * @return
	 */
	UserMessageData getGroupHistoryMessage(String userId, String groupid, Double beginTime, Double endTime, Integer offset, Integer count);
}
