/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.synapse.transport.passthru.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.nio.*;
import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;

import java.io.IOException;

public class LoggingSourceHandler implements NHttpServerEventHandler {

    private final Log log;

    private final NHttpServerEventHandler handler;

    public LoggingSourceHandler (final NHttpServerEventHandler handler) {
        super();
        if (handler == null) {
            throw new IllegalArgumentException("HTTP service handler may not be null");
        }
        this.handler = handler;
        this.log = LogFactory.getLog(handler.getClass());
    }

    public void connected(final NHttpServerConnection conn) throws IOException, HttpException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("HTTP connection " + conn + ": Connected");
        }
        this.handler.connected(conn);
    }

    public void closed(final NHttpServerConnection conn) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("HTTP connection " + conn + ": Closed");
        }
        this.handler.closed(conn);
    }

    public void endOfInput(NHttpServerConnection conn) throws IOException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("HTTP connection " + conn + ": Closed at the remote end");
        }
        this.handler.endOfInput(conn);
    }

    public void exception(NHttpServerConnection conn, Exception ex) {
        if (ex.getMessage() == null) {
            ex.printStackTrace();
            return;
        }
        if (ex instanceof ConnectionClosedException ||
                ex.getMessage().contains("Connection reset by peer") ||
                ex.getMessage().contains("forcibly closed")) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("HTTP connection " + conn + ": " + ex.getMessage() +
                        " (Probably the keep-alive connection was closed)");
            }
        } else if (ex instanceof HttpException) {
            this.log.error("HTTP Error occurred on connection " + conn + ": " + ex.getMessage(), ex);
            this.handler.exception(conn, ex);
        } else {
            this.log.error("IO Error occurred on HTTP connection " + conn + ": " + ex.getMessage(), ex);
        }
        this.handler.exception(conn, ex);
    }

    public void requestReceived(final NHttpServerConnection conn) throws IOException, HttpException {
        HttpRequest request = conn.getHttpRequest();
        if (this.log.isDebugEnabled()) {
            this.log.debug("HTTP InRequest Received on connection " + conn + ": "
                    + request.getRequestLine());
        }
        this.handler.requestReceived(conn);
    }

    public void outputReady(final NHttpServerConnection conn, final ContentEncoder encoder) throws IOException, HttpException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("HTTP connection " + conn + ": Output ready");
        }
        this.handler.outputReady(conn, encoder);
        if (this.log.isDebugEnabled()) {
            this.log.debug("HTTP connection " + conn + ": Content encoder " + encoder);
        }
    }

    public void responseReady(final NHttpServerConnection conn) throws IOException, HttpException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("HTTP connection " + conn + ": Response ready");
        }
        this.handler.responseReady(conn);
    }

    public void inputReady(final NHttpServerConnection conn, final ContentDecoder decoder) throws IOException, HttpException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("HTTP connection " + conn + ": Input ready");
        }
        this.handler.inputReady(conn, decoder);
        if (this.log.isDebugEnabled()) {
            this.log.debug("HTTP connection " + conn + ": Content decoder " + decoder);
        }
    }

    public void timeout(final NHttpServerConnection conn) throws IOException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("HTTP connection " + conn + ": Timeout");
        }
        this.handler.timeout(conn);
    }
}
