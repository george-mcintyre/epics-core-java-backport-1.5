/*
 * Copyright (c) 2009 by Cosylab
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

package org.epics.pvaccess.server.impl.remote.plugins;

import org.epics.pvaccess.PVFactory;
import org.epics.pvaccess.server.impl.remote.ServerContextImpl;
import org.epics.pvaccess.server.plugins.BeaconServerStatusProvider;
import org.epics.pvdata.pv.*;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Default <code>BeaconServerDataProvider</code> implementation.
 *
 * @author Matej Sekoranja (matej.sekoranja@cosylab.com)
 * @version $Id$
 */
public class DefaultBeaconServerDataProvider implements
        BeaconServerStatusProvider {

    /**
     * Monitored server context.
     */
    protected ServerContextImpl context;

    /**
     * Status structure.
     */
    protected PVStructure status;

    /**
     * Constructor.
     *
     * @param context server context to be monitored.
     */
    public DefaultBeaconServerDataProvider(ServerContextImpl context) {
        this.context = context;

        initialize();
    }

    /**
     * Initialize data structures.
     */
    private void initialize() {
        FieldCreate fieldCreate = PVFactory.getFieldCreate();
        PVDataCreate pvDataCreate = PVFactory.getPVDataCreate();
        // TODO hierarchy can be used...
        String[] fieldNames = new String[]{
                "connections",
                "allocatedMemory",
                "freeMemory",
                "threads",
                "deadlocks",
                "averageSystemLoad"
        };

        Field[] fields = new Field[6];
        fields[0] = fieldCreate.createScalar(ScalarType.pvInt);
        fields[1] = fieldCreate.createScalar(ScalarType.pvLong);
        fields[2] = fieldCreate.createScalar(ScalarType.pvLong);
        fields[3] = fieldCreate.createScalar(ScalarType.pvInt);
        fields[4] = fieldCreate.createScalar(ScalarType.pvInt);
        fields[5] = fieldCreate.createScalar(ScalarType.pvDouble);

        status = pvDataCreate.createPVStructure(fieldCreate.createStructure(fieldNames, fields));
    }

    /* (non-Javadoc)
     * @see org.epics.pvaccess.server.plugins.BeaconServerStatusProvider#getServerStatusData()
     */
    public PVField getServerStatusData() {

        status.getIntField("connections").put(context.getTransportRegistry().numberOfActiveTransports());
        status.getLongField("allocatedMemory").put(Runtime.getRuntime().totalMemory());
        status.getLongField("freeMemory").put(Runtime.getRuntime().freeMemory());

        ThreadMXBean threadMBean = ManagementFactory.getThreadMXBean();
        status.getIntField("threads").put(threadMBean.getThreadCount());

        final long[] deadlocks = threadMBean.findMonitorDeadlockedThreads();
        status.getIntField("deadlocks").put((deadlocks != null) ? deadlocks.length : 0);

        ManagementFactory.getOperatingSystemMXBean();
        status.getDoubleField("averageSystemLoad").put(-1);

        return status;
    }

}
