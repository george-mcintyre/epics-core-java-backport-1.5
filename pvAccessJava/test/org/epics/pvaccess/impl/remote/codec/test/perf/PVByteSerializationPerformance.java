/*
 *
 */
package org.epics.pvaccess.impl.remote.codec.test.perf;

import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;
import org.epics.pvaccess.PVFactory;
import org.epics.pvdata.pv.*;

import java.nio.ByteBuffer;

/**
 * @author msekoranja
 */
public class PVByteSerializationPerformance extends JapexDriverBase implements SerializableControl, DeserializableControl {

    PVByte pvField;

    final int DEFAULT_BUFFER_SIZE = 102400;
    final int ELEMENTS = DEFAULT_BUFFER_SIZE / 8;

    final ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);


    /* (non-Javadoc)
     * @see org.epics.pvdata.pv.DeserializableControl#ensureData(int)
     */
    public void ensureData(int size) {
        // TODO Auto-generated method stub

    }


    /* (non-Javadoc)
     * @see org.epics.pvdata.pv.DeserializableControl#alignData(int)
     */
    public void alignData(int alignment) {
        // TODO Auto-generated method stub

    }


    /* (non-Javadoc)
     * @see org.epics.pvdata.pv.SerializableControl#flushSerializeBuffer()
     */
    public void flushSerializeBuffer() {
        // TODO Auto-generated method stub

    }


    /* (non-Javadoc)
     * @see org.epics.pvdata.pv.SerializableControl#ensureBuffer(int)
     */
    public void ensureBuffer(int size) {
        // TODO Auto-generated method stub

    }


    /* (non-Javadoc)
     * @see org.epics.pvdata.pv.SerializableControl#alignBuffer(int)
     */
    public void alignBuffer(int alignment) {
        // TODO Auto-generated method stub

    }

    public void cachedSerialize(Field field, ByteBuffer buffer) {
        // no cache
        field.serialize(buffer, this);
    }

    static final FieldCreate fieldCreate = PVFactory.getFieldCreate();

    public Field cachedDeserialize(ByteBuffer buffer) {
        // no cache
        return fieldCreate.deserialize(buffer, this);
    }

    /* (non-Javadoc)
     * @see com.sun.japex.JapexDriverBase#initializeDriver()
     */
    @Override
    public void initializeDriver() {
        super.initializeDriver();

        Field field = fieldCreate.createScalar(ScalarType.pvByte);
        pvField = (PVByte) PVFactory.getPVDataCreate().createPVField(field);
    }

    int index;
    boolean testSerialization;

    /* (non-Javadoc)
     * @see com.sun.japex.JapexDriverBase#prepare(com.sun.japex.TestCase)
     */
    @Override
    public void prepare(TestCase testCase) {
        testSerialization = testCase.getBooleanParam("testSerialization");
        if (testSerialization) {
            buffer.clear();
        } else {
            buffer.clear();
            for (int i = 0; i < ELEMENTS; i++) {
                pvField.put((byte) i);
                pvField.serialize(buffer, this);
            }
            buffer.flip();
            index = 0;
        }
    }


    /* (non-Javadoc)
     * @see com.sun.japex.JapexDriverBase#run(com.sun.japex.TestCase)
     */
    @Override
    public void run(TestCase testCase) {
        if (testSerialization) {
            if (index++ == ELEMENTS) {
                buffer.clear();
                index = 1;
            }
            pvField.put((byte) index);
            pvField.serialize(buffer, this);

        } else {
            if (index++ == ELEMENTS) {
                buffer.position(0);
                index = 1;
            }
            pvField.deserialize(buffer, this);
        }
    }

}
