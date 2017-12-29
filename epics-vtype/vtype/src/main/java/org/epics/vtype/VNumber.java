/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.vtype;

import org.epics.util.number.UByte;
import org.epics.util.number.UInteger;
import org.epics.util.number.ULong;
import org.epics.util.number.UShort;

/**
 * Scalar number with alarm, timestamp, display and control information.
 * <p>
 * This class allows to use any scalar number (i.e. {@link VInt} or
 * {@link VDouble}) through the same interface.
 *
 * @author carcassi
 */
public abstract class VNumber extends Scalar implements DisplayProvider {
    
    /**
     * The numeric value.
     * 
     * @return the value
     */
    @Override
    public abstract Number getValue();
    
    /**
     * Default toString implementation for VNumber.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Class type = typeOf(this);
        builder.append(type.getSimpleName())
                .append('[')
                .append(getValue())
                .append(", ")
                .append(getAlarm())
                .append(", ")
                .append(getTime())
                .append(']');
        return builder.toString();
    }
    
    /**
     * Creates a new VNumber based on the type of the data
     * 
     * @param value the value
     * @param alarm the alarm
     * @param time the time
     * @param display the display
     * @return the new number
     */
    public static VNumber of(Number value, Alarm alarm, Time time, Display display){
        if (value instanceof Double) {
            return VDouble.of((Double) value, alarm, time, display);
        } else if (value instanceof Float) {
            return VFloat.of((Float) value, alarm, time, display);
        } else if (value instanceof ULong) {
            return VULong.of((ULong) value, alarm, time, display);
        } else if (value instanceof Long) {
            return VLong.of((Long) value, alarm, time, display);
        } else if (value instanceof UInteger) {
            return VUInt.of((UInteger) value, alarm, time, display);
        } else if (value instanceof Integer) {
            return VInt.of((Integer) value, alarm, time, display);
        } else if (value instanceof UShort) {
            return VUShort.of((UShort) value, alarm, time, display);
        } else if (value instanceof Short) {
            return VShort.of((Short) value, alarm, time, display);
        } else if (value instanceof UByte) {
            return VUByte.of((UByte) value, alarm, time, display);
        } else if (value instanceof Byte) {
            return VByte.of((Byte) value, alarm, time, display);
        }
	throw new IllegalArgumentException("Only standard Java implementations of Number and EPICS unsigned numbers are supported");
    }
    
}
