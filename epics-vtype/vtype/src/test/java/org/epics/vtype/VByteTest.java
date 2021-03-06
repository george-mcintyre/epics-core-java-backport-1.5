/*
 * Copyright information and license terms for this software can be
 * found in the file LICENSE.TXT included with the distribution.
 */
package org.epics.vtype;

/**
 *
 * @author carcassi
 */
public class VByteTest extends FeatureTestVNumber<Byte, VByte> {

    @Override
    Byte getValue() {
        return 1;
    }

    @Override
    Byte getAnotherValue() {
        return 0;
    }

    @Override
    VByte of(Byte value, Alarm alarm, Time time, Display display) {
        return VByte.of(value, alarm, time, display);
    }

    @Override
    String getToString() {
        // Modified precision of test to match joda time's millisecond precision
        return "VByte[1, MINOR(DB) - LOW, 2012-12-05T14:57:21.521Z]";
    }

}
