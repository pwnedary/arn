/**
 * 
 */
package org.gamelib.net;

import java.io.IOException;

/**
 * Used to be notified about connection events.
 * @author Axel
 */
public interface SocketListener {
	/**
	 * Called when the remote end has been connected. This will be invoked before any objects are received by {@link #received(Connection2, Object)}. This method should not block for long periods as other network activity will not be processed until it returns.
	 */
	public void connected(Connection connection) throws IOException;

	/**
	 * Called when the remote end is no longer connected.
	 */
	public void disconnected(Connection connection) throws IOException;

	/**
	 * Called when an object has been received from the remote end of the connection. This method should not block for long periods as other network activity will not be processed until it returns.
	 */
	public void received(Object object) throws IOException;

	/**
	 * Called when an object has been sent from the remote end of the connection. This method should not block for long periods as other network activity will not be processed until it returns.
	 */
	public void sent(Object object)throws IOException;
}