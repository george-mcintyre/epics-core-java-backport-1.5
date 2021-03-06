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

package org.epics.pvaccess.impl.remote;

import org.epics.pvaccess.plugins.SecurityPlugin;
import org.epics.pvaccess.util.logging.LoggerProvider;
import org.epics.pvdata.misc.Timer;

import java.util.Map;


/**
 * Interface defining <code>Context</code> (logging, reactor, thread-pool, etc.).
 *
 * @author <a href="mailto:matej.sekoranjaATcosylab.com">Matej Sekoranja</a>
 * @version $Id$
 */
public interface Context extends LoggerProvider {

    /**
     * Get timer.
     *
     * @return timer.
     */
    Timer getTimer();

    /**
     * Get transport (virtual circuit) registry.
     *
     * @return transport (virtual circuit) registry.
     */
    TransportRegistry getTransportRegistry();

    /**
     * Get map of available security plug-ins.
     *
     * @return the map of available security plug-ins
     */
    Map<String, SecurityPlugin> getSecurityPlugins();

    /**
     * Get internal debug level configuration, higher value means more, 0 means none.
     *
     * @return debug level.
     */
    int getDebugLevel();

}
