/*
 * Copyright information and license terms for this software can be
 * found in the file LICENSE.TXT included with the distribution.
 */
package org.epics.gpclient.datasource.sim;

import org.epics.gpclient.datasource.MultiplexedChannelHandler;
import org.epics.util.text.FunctionParser;
import org.epics.vtype.Alarm;
import org.epics.vtype.Display;
import org.epics.vtype.Time;
import org.epics.vtype.VType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation class for a channel that keeps connecting and disconnecting.
 *
 * @author carcassi
 */
class IntermittentChannelHandler extends MultiplexedChannelHandler<Object, Object> {

    private final Object value;
    private final double delayInSeconds;
    private final ScheduledExecutorService exec;
    private final Runnable task = new Runnable() {

        public void run() {
            // Protect the timer thread for possible problems.
            try {
                boolean toConnect = !isConnected();

                if (toConnect) {
                    processConnection(new Object());
                    processMessage(VType.toVTypeChecked(value));
                } else {
                    processMessage(VType.toVTypeChecked(value, Alarm.disconnected(), Time.now(), Display.none()));
                    processConnection(null);
                }
            } catch (Exception ex) {
                log.log(Level.WARNING, "Data simulation problem", ex);
            }
        }
    };
    private static final Logger log = Logger.getLogger(SimulationChannelHandler.class.getName());
    private ScheduledFuture<?> taskFuture;

    IntermittentChannelHandler(String channelName, ScheduledExecutorService exec) {
        super(channelName, true);
        String errorMessage = "Incorrect syntax. Must match intermittentChannel(delayInSeconds, value)";
        List<Object> tokens = FunctionParser.parseFunctionAnyParameter(channelName);
        if (tokens == null || tokens.size() <= 1) {
            throw new IllegalArgumentException(errorMessage);
        }
        if (tokens.size() == 2) {
            value = "Initial Value";
        } else {
            value = FunctionParser.asScalarOrList(tokens.subList(2, tokens.size()));
            if (value == null) {
                throw new IllegalArgumentException(errorMessage);
            }
        }
        delayInSeconds = (Double) tokens.get(1);
        if (delayInSeconds < 0.001) {
            throw new IllegalArgumentException("Delay must be at least 0.001");
        }
        this.exec = exec;
    }

    @Override
    public void connect() {
        taskFuture = exec.scheduleWithFixedDelay(task, 0, (long) (delayInSeconds * 1000), TimeUnit.MILLISECONDS);
    }

    @Override
    public void disconnect() {
        taskFuture.cancel(false);
        processConnection(null);
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("delayInSeconds", delayInSeconds);
        result.put("value", value);
        return result;
    }

    @Override
    protected boolean isConnected(Object payload) {
        return payload != null;
    }

    @Override
    public void write(Object newValue) {
        throw new UnsupportedOperationException("Can't write to simulation channel.");
    }

}
