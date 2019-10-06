/**
 * 
 */
package org.jim.server;

import org.jim.common.ImConst;
import org.jim.common.ImConfig;
import org.jim.server.handler.ImServerAioHandler;
import org.jim.server.helper.redis.RedisMessageHelper;
import org.jim.server.listener.ImGroupListener;
import org.jim.server.listener.ImServerAioListener;
import org.tio.core.intf.GroupListener;
import org.tio.core.ssl.SslConfig;
import org.tio.server.AioServer;

import java.io.IOException;

/**
 * 
 * @author WChao
 *
 */
public class ImServerStarter {
	
	private ImServerAioHandler imAioHandler = null;
	private ImServerAioListener imAioListener = null;
	private ImServerGroupContext imServerGroupContext = null;
	private ImGroupListener imGroupListener = null;
	private AioServer aioServer = null;
	private ImConfig imConfig = null;
	
	public ImServerStarter(ImConfig imConfig){
		this(imConfig,null);
	}
	
	public ImServerStarter(ImConfig imConfig, ImServerAioListener imAioListener){
		this.imConfig = imConfig;
		this.imAioListener = imAioListener;
		init();
	}
	
	public void init() {
		System.setProperty("tio.default.read.buffer.size", String.valueOf(imConfig.getReadBufferSize()));
		imAioHandler = new ImServerAioHandler(imConfig) ;
		if(imAioListener == null) {
			imAioListener = new ImServerAioListener(imConfig);
		}
		//设置群组监听器，非必须，根据需要自己选择性实现;
		imConfig.setImGroupListener(new ImGroupListener());

		this.imGroupListener = (ImGroupListener)imConfig.getImGroupListener();
		imServerGroupContext = new ImServerGroupContext(imConfig, imAioHandler, imAioListener);
		imServerGroupContext.setGroupListener(imGroupListener);
		if(imConfig.getMessageHelper() == null){
			imConfig.setMessageHelper(new RedisMessageHelper(imConfig));
		}
		//开启SSL
//		if(ImConst.ON.equals(imConfig.getIsSSL())){
//			SslConfig sslConfig = imConfig.getSslConfig();
//			if(sslConfig != null) {
//				imServerGroupContext.setSslConfig(sslConfig);
//			}
//		}
        System.out.println("imServerGroupContext.hashCode()===>>>"+imServerGroupContext.hashCode());
		aioServer = new AioServer(imServerGroupContext);
	}
	
	public void start() throws IOException {
		aioServer.start(this.imConfig.getBindIp(),this.imConfig.getBindPort());
	}
	
	public void stop(){
		aioServer.stop();
	}
}
