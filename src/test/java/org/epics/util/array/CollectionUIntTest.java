/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.util.array;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author carcassi
 */
public class CollectionUIntTest extends FeatureTestCollectionNumber {

    @Override
    public CollectionNumber createConstantCollection() {
        return new CollectionUInt() {

            @Override
            public IteratorUInt iterator() {
                return new IteratorUInt() {

                    int n=0;

                    @Override
                    public boolean hasNext() {
                        return n < 10;
                    }

                    @Override
                    public int nextInt() {
                        n++;
                        return 1;
                    }
                };
            }

            @Override
            public int size() {
                return 10;
            }
        };
    }
}
