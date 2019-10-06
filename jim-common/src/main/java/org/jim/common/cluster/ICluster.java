/**
 * 
 */
package org.jim.common.cluster;

import org.jim.common.ImPacket;
import org.tio.core.GroupContext;

/** @author WChao */
public interface ICluster {
	void clusterToIp(GroupContext groupContext, String ip, ImPacket packet);
	void clusterToUser(GroupContext groupContext, String userid, ImPacket packet);
	void clusterToGroup(GroupContext groupContext, String group, ImPacket packet);
	void clusterToChannelId(GroupContext groupContext, String channelId, ImPacket packet);
}
