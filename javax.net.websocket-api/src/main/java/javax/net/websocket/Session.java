//
//  ========================================================================
//  Copyright (c) 1995-2012 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package javax.net.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.websocket.extensions.Extension;

/**
 * A Web Socket session represents a conversation between two web socket
 * endpoints. As soon as the websocket handshake completes successfully, the web
 * socket implementation provides the endpoint an active websocket session. The
 * endpoint can then register interest in incoming messages that are part of
 * this newly created conversation by providing a MessageHandler to the session,
 * and can send messages to the other end of the conversation by means of the
 * RemoteEndpoint object obtained from this session.
 * 
 * @since DRAFT 001
 */
public/* FIXME <T> */interface Session /* FIXME <T> */{
    /**
     * Register to handle to incoming messages in this conversation.
     */
    void addMessageHandler(MessageHandler listener);

    /**
     * Close the current conversation with a normal status code and no reason
     * phrase.
     */
    void close() throws IOException;

    /**
     * Close the current conversation, giving a reason for the closure. Note the
     * websocket spec defines the acceptable uses of status codes and reason
     * phrases.
     * 
     * @param closeStatus
     *            the reason for the closure
     */
    void close(CloseReason closeStatus) throws IOException;

    /**
     * Return the container that this session is part of.
     * 
     * @return the container
     */
    ClientContainer getContainer();

    /**
     * Return the number of seconds since the underlying connection had any
     * activity.
     * 
     * @return the inactive time
     */
    // FIXME: is this physical (net) or logical (mux) connection inactive time?
    long getInactiveTime();

    /**
     * The maximum total length of messages, text or binary, that this Session
     * can handle.
     * 
     * @return the message size
     */
    long getMaximumMessageSize();

    /**
     * Return an unmodifiable copy of the set of MessageHandlers for this
     * Session.
     * 
     * @return the set of message handlers
     */
    Set<MessageHandler> getMessageHandlers();

    /**
     * Return the list of extensions currently in use for this conversation.
     * 
     * @return the negotiated extensions
     */
    List<Extension> getNegotiatedExtensions();

    /**
     * Return the sub protocol agreed during the websocket handshake for this
     * conversation.
     * 
     * @return the negotiated subprotocol
     */
    String getNegotiatedSubprotocol();

    /**
     * Return the request parameters associated with the request this session
     * was opened under.
     * 
     * @return the unmodifiable map of the request parameters
     */
    Map<String, String[]> getParameterMap();

    /**
     * Returns the version of the websocket protocol currently being used. This
     * is taken as the value of the Sec-WebSocket-Version header used in the
     * opening handshake. i.e. "13".
     * 
     * @return the protocol version
     */
    String getProtocolVersion();

    /**
     * Return the query string associated with the request this session was
     * opened under.
     */
    String getQueryString();

    /**
     * Return a reference to the RemoteEndpoint object representing the other
     * end of this conversation.
     * 
     * @return the remote endpoint
     */
    RemoteEndpoint getRemote();

    /**
     * Return a reference to the RemoteEndpoint that can send messages in the
     * form of objects of class c.
     * 
     * @return the remote endpoint instance
     */
    // FIXME breaks interface on .setEncoders()
    // RemoteEndpoint<T> getRemoteL(Class<T> c);

    /**
     * Return the URI that this session was opened under.
     * 
     * @return the request URI.
     */
    // FIXME should this conform to servlet-api
    // FIXME strip query string?
    URI getRequestURI();

    /**
     * Return the number of seconds before this conversation will be closed by
     * the container if it is inactive, ie no messages are either sent or
     * received in that time.
     * 
     * @return the timeout in seconds
     */
    // FIXME: this should be milliseconds
    // FIXME: should conform to Socket.setSoTimeout()
    long getTimeout();

    /**
     * Return true if and only if the underlying socket is open.
     * 
     * @return whether the session is active
     */
    abstract boolean isActive();

    /**
     * Return true if and only if the underlying socket is using a secure
     * transport.
     * 
     * @return whether its using a secure transport
     */
    boolean isSecure();

    /**
     * Remove the given MessageHandler from the set belonging to this session.
     * 
     * @param listener
     *            the handler to be removed. TBD Threading issues wrt handler
     *            invocations vs removal
     */
    void removeMessageHandler(MessageHandler listener);

    /**
     * Sets the list of encoders to be used in this session in order of
     * preference. The first element in the list that matches for a given type
     * will be used rather than a later element in the list that matches for a
     * given type.
     * 
     * @param encoders
     *            the list of encoders
     */
    void setEncoders(List<Encoder> encoders);

    /**
     * Sets the maximum total length of messages, text or binary, that this
     * Session can handle.
     */
    void setMaximumMessageSize(long length);

    /**
     * Set the number of seconds before this conversation will be closed by the
     * container if it is inactive, ie no messages are either sent or received.
     * 
     * @param seconds
     *            the number of seconds
     */
    // FIXME: this should be milliseconds
    // FIXME: should conform to Socket.setSoTimeout()
    void setTimeout(long seconds);
}
