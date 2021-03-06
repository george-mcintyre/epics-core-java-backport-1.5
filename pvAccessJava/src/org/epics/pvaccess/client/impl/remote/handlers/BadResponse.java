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

package org.epics.pvaccess.client.impl.remote.handlers;

import org.epics.pvaccess.PVAConstants;
import org.epics.pvaccess.client.impl.remote.ClientContextImpl;
import org.epics.pvaccess.impl.remote.Transport;
import org.epics.pvaccess.util.HexDump;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;


/**
 * Bad response handler.
 *
 * @author <a href="mailto:matej.sekoranjaATcosylab.com">Matej Sekoranja</a>
 * @version $Id$
 */
public class BadResponse extends AbstractClientResponseHandler {

    public BadResponse(ClientContextImpl context) {
        super(context, "Bad response");
    }


    /* (non-Javadoc)
     * @see org.epics.pvaccess.impl.remote.AbstractResponseHandler#handleResponse(java.net.InetSocketAddress, org.epics.pvaccess.impl.remote.Transport, byte, byte, int, java.nio.ByteBuffer)
     */
    @Override
    public void handleResponse(InetSocketAddress responseFrom,
                               Transport transport, byte version, byte command, int payloadSize,
                               ByteBuffer payloadBuffer) {
        super.handleResponse(responseFrom, transport, version, command, payloadSize, payloadBuffer);

        context.getLogger().severe("Undecipherable message (bad response type " + command + ") from " + responseFrom + ".");

        // TODO remove debug output
        if (payloadBuffer.hasArray()) {
            int start = Math.max(0, payloadBuffer.position() - PVAConstants.PVA_MESSAGE_HEADER_SIZE);
            HexDump.hexDump(description, payloadBuffer.array(), start, payloadBuffer.limit());
        }
    }

}
