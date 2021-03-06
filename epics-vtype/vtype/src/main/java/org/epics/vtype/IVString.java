/*
 * Copyright information and license terms for this software can be
 * found in the file LICENSE.TXT included with the distribution.
 */
package org.epics.vtype;

/**
 * Immutable {@code VString} implementation.
 *
 * @author carcassi
 */
final class IVString extends VString {

    private final String value;
    private final Alarm alarm;
    private final Time time;

    IVString(String value, Alarm alarm, Time time) {
        VType.argumentNotNull("value", value);
        VType.argumentNotNull("alarm", alarm);
        VType.argumentNotNull("time", time);
        this.value = value;
        this.alarm = alarm;
        this.time = time;
    }

    @Override
    public String getValue() {
        return value;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public Time getTime() {
        return time;
    }

}
