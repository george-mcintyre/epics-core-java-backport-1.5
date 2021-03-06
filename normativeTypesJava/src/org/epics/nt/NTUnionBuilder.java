/*
 * Copyright information and license terms for this software can be
 * found in the file LICENSE that is included with the distribution
 */
package org.epics.nt;

import org.epics.pvdata.pv.Field;
import org.epics.pvdata.pv.FieldBuilder;
import org.epics.pvdata.pv.FieldCreate;
import org.epics.pvdata.pv.ScalarType;
import org.epics.pvdata.pv.Structure;
import org.epics.pvdata.pv.Union;
import org.epics.pvdata.pv.PVStructure;
import org.epics.pvdata.factory.FieldFactory;
import org.epics.pvdata.factory.PVDataFactory;

import java.util.ArrayList;

/**
 * Interface for in-line creating of NTUnion.
 *
 * One instance can be used to create multiple instances.
 * An instance of this object must not be used concurrently (an object has a state).
 * @author dgh
 */
public class NTUnionBuilder
{
    /**
     * Specify the union for the value field.
     * If this is not called then a variantUnion is the default.
     *
     * @param value the introspection object for the union value field
     * @return this instance of  NTUnionBuilder
     */
    public NTUnionBuilder value(Union value)
    {
        valueType = value;
        return this;
    }

    /**
     * Adds descriptor field to the NTUnion.
     *
     * @return this instance of NTUnionBuilder.
     */
    public NTUnionBuilder addDescriptor()
    {
        descriptor = true;
        return this;
    }

    /**
     * Adds alarm structure to the NTUnion.
     *
     * @return this instance of NTUnionBuilder.
     */
    public NTUnionBuilder addAlarm()
    {
        alarm = true;
        return this;
    }

    /**
     * Adds timeStamp field to the NTUnion.
     *
     * @return this instance of NTUnionBuilder.
     */
    public NTUnionBuilder addTimeStamp()
    {
        timeStamp = true;
        return this;
    }

    /**
     * Create a Structure that represents NTUnion.
     * This resets this instance state and allows new instance to be created.
     *
     * @return a new instance of a Structure
     */
    public Structure createStructure()
    {
        NTField ntField = NTField.get();
        FieldCreate fieldCreate = FieldFactory.getFieldCreate();

        if(valueType == null)
            valueType= fieldCreate.createVariantUnion();

        FieldBuilder builder = fieldCreate.createFieldBuilder().
            setId(NTUnion.URI).
            add("value", valueType);

        if (descriptor)
            builder.add("descriptor", ScalarType.pvString);

        if (alarm)
            builder.add("alarm", ntField.createAlarm());

        if (timeStamp)
            builder.add("timeStamp", ntField.createTimeStamp());

        int extraCount = extraFieldNames.size();
        for (int i = 0; i< extraCount; i++)
            builder.add(extraFieldNames.get(i), extraFields.get(i));

        Structure s = builder.createStructure();

        reset();
        return s;
    }

    /**
     * Creates a PVStructure that represents NTUnion.
     * This resets this instance state and allows new instance to be created.
     *
     * @return a new instance of a PVStructure.
     */
    public PVStructure createPVStructure()
    {
        return PVDataFactory.getPVDataCreate().createPVStructure(createStructure());
    }

    /**
     * Creates an NTUnion instance.
     * This resets this instance state and allows new instance to be created.
     *
     * @return a new instance of an NTUnion
     */
    public NTUnion create()
    {
        return new NTUnion(createPVStructure());
    }

    /**
     * Adds extra Field to the type.
     *
     * @param name the name of the field
     * @param field the field to add
     * @return this instance of NTUnionBuilder
     */
    public NTUnionBuilder add(String name, Field field)
    {
        extraFields.add(field);
        extraFieldNames.add(name);
        return this;
    }

    NTUnionBuilder()
    {
        reset();
    }

    private void reset()
    {
        valueType = null;
        descriptor = false;
        alarm = false;
        timeStamp = false;
        extraFieldNames.clear();
        extraFields.clear();
    }

    private Union valueType;
    private boolean descriptor;
    private boolean alarm;
    private boolean timeStamp;

    // NOTE: this preserves order, however it does not handle duplicates
    private ArrayList<String> extraFieldNames = new ArrayList<String>();
    private ArrayList<Field> extraFields = new ArrayList<Field>();
}

