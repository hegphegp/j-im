/**
 * 
 */
package org.jim.server.tcp;

import org.apache.log4j.Logger;
import org.jim.common.ImAio;
import org.jim.common.ImConfig;
import org.jim.common.ImPacket;
import org.jim.common.ImStatus;
import org.jim.common.packets.Command;
import org.jim.common.packets.RespBody;
import org.jim.common.protocol.IProtocol;
import org.jim.common.tcp.TcpPacket;
import org.jim.common.tcp.TcpProtocol;
import org.jim.common.tcp.TcpServerDecoder;
import org.jim.common.tcp.TcpServerEncoder;
import org.jim.server.command.AbstractCmdHandler;
import org.jim.server.command.CommandManager;
import org.jim.server.handler.AbstractProtocolHandler;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;
/**
 * 版本: [1.0]
 * 功能说明: 
 * @author : WChao 创建时间: 2017年8月3日 下午7:44:48
 */
public class TcpProtocolHandler extends AbstractProtocolHandler {
	
	Logger logger = Logger.getLogger(TcpProtocolHandler.class);
	
	@Override
	public void init(ImConfig imConfig) {
	}

	@Override
	public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
		TcpPacket tcpPacket = (TcpPacket)packet;
		System.out.println("encode(ByteBuffer buffer, ChannelContext channelContext)的channelContext.hashCode()===>>>"+channelContext.hashCode());
		return TcpServerEncoder.encode(tcpPacket, groupContext, channelContext);
	}

	@Override
	public void handler(Packet packet, ChannelContext channelContext) throws Exception {
		TcpPacket tcpPacket = (TcpPacket)packet;
//		System.out.println(Thread.currentThread().getName()+"   handler===>>>"+tcpPacket.hashCode());
		AbstractCmdHandler cmdHandler = CommandManager.getCommand(tcpPacket.getCommand());
		if(cmdHandler == null){
			ImPacket imPacket = new ImPacket(Command.COMMAND_UNKNOW, new RespBody(Command.COMMAND_UNKNOW,ImStatus.C10017).toByte());
			ImAio.send(channelContext, imPacket);
			return;
		}
		ImPacket response = cmdHandler.handler(tcpPacket, channelContext);
		System.out.println("handler(Packet packet, ChannelContext channelContext)的channelContext.hashCode()===>>>"+channelContext.hashCode());
		if(response != null && tcpPacket.getSynSeq() < 1){
			ImAio.send(channelContext, response);
		}
	}

	@Override
	public TcpPacket decode(ByteBuffer buffer, ChannelContext channelContext) throws AioDecodeException {
		TcpPacket tcpPacket = TcpServerDecoder.decode(buffer, channelContext);
		System.out.println("decode(ByteBuffer buffer, ChannelContext channelContext)的channelContext.hashCode()===>>>"+channelContext.hashCode());
//		System.out.println(Thread.currentThread().getName()+"   decode===>>>"+tcpPacket.hashCode());
		return tcpPacket;
	}

	@Override
	public IProtocol protocol() {
		return new TcpProtocol();
	}
}
