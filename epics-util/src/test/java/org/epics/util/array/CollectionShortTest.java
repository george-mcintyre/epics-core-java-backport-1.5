/*
 * Copyright information and license terms for this software can be
 * found in the file LICENSE.TXT included with the distribution.
 */
package org.epics.util.array;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author carcassi
 */
public class CollectionShortTest extends FeatureTestCollectionNumber {

    @Override
    public CollectionNumber createConstantCollection() {
        return new AbstractCollectionShort() {

            public IteratorShort iterator() {
                return new IteratorShort() {

                    int n=0;

                    public boolean hasNext() {
                        return n < 10;
                    }

                    public short nextShort() {
                        n++;
                        return (short) 1;
                    }
                };
            }

            public int size() {
                return 10;
            }
        };
    }
}
