/*
 * Copyright (c) 2004 by Cosylab
 *
 * The full license specifying the redistribution, modification, usage and other
 * rights and obligations is included with the distribution of this project in
 * the file "LICENSE-CAJ". If the license is not included visit Cosylab web site,
 * <http://www.cosylab.com>.
 *
 * THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND, NOT EVEN THE
 * IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR OF THIS SOFTWARE, ASSUMES
 * _NO_ RESPONSIBILITY FOR ANY CONSEQUENCE RESULTING FROM THE USE, MODIFICATION,
 * OR REDISTRIBUTION OF THIS SOFTWARE.
 */

package org.epics.pvaccess.impl.remote.request;

import org.epics.pvaccess.impl.remote.Transport;
import org.epics.pvaccess.util.HexDump;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * Base (abstract) channel access response handler.
 *
 * @author <a href="mailto:matej.sekoranjaATcosylab.com">Matej Sekoranja</a>
 * @version $Id$
 */
public abstract class AbstractResponseHandler implements ResponseHandler {

    /**
     * Response handler description.
     */
    protected final String description;

    /**
     * Debug flag.
     */
    protected final boolean debug;

    public AbstractResponseHandler(String description, boolean debug) {
        this.description = description;
        // dump messages flag
        this.debug = debug;
    }

    /* (non-Javadoc)
     * @see org.epics.pvaccess.impl.remote.ResponseHandler#handleResponse(java.net.InetSocketAddress, org.epics.pvaccess.impl.remote.Transport, byte, byte, int, java.nio.ByteBuffer)
     */
    public void handleResponse(InetSocketAddress responseFrom, Transport transport, byte version, byte command, int payloadSize, ByteBuffer payloadBuffer) {
        if (debug) {
            if (command != 0) {
                if (payloadBuffer.hasArray())
                    HexDump.hexDump("Message [" + command + ", v" + Integer.toHexString(version) + "] received from " + responseFrom, description,
                            payloadBuffer.array(),
                            payloadBuffer.position(),
                            Math.min(payloadSize, payloadBuffer.limit() - payloadBuffer.position()));    // TODO can be segmented
                else
                    System.out.println("Message [" + command + ", v" + Integer.toHexString(version) + "] received from " + responseFrom + ", payload size = " + payloadSize);
            }
        }
    }

}
