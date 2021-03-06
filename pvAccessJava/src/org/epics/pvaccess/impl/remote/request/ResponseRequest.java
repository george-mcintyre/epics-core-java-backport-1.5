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

import org.epics.pvdata.pv.Requester;
import org.epics.pvdata.pv.Status;

/**
 * A request that expects an response.
 * Responses identified by its I/O ID.
 * This interface needs to be extended (to provide method called on response).
 *
 * @author <a href="mailto:matej.sekoranjaATcosylab.com">Matej Sekoranja</a>
 * @version $Id$
 */
public interface ResponseRequest {

    /**
     * Get I/O ID.
     *
     * @return ioid
     */
    int getIOID();

    /**
     * Timeout notification.
     */
    void timeout();

    /**
     * Cancel response request.
     */
    void cancel();

    /**
     * Report status to clients (e.g. disconnected).
     *
     * @param status to report.
     */
    void reportStatus(Status status);

    /**
     * Get request requester.
     *
     * @return request requester.
     */
    Requester getRequester();
}
