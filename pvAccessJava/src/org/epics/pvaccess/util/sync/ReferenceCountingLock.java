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

package org.epics.pvaccess.util.sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Reference counting mutex implementation w/ deadlock detection.
 * Synchronization helper class used (intended for use) for activation/deactivation synchronization.
 * This class enforces <code>attempt</code> method of acquiring the locks to prevent deadlocks.
 * Class also offers reference counting.
 * (NOTE: automatic lock counting was not implemented due to imperfect usage.)
 * <p>
 * Example of usage:
 * <code>
 * ReferenceCountingLock lock;
 * if (lock.acquire(3*Sync.ONE_MINUTE))
 * {
 * try
 * {
 * // critical section here
 * }
 * finally
 * {
 * lock.release();
 * }
 * }
 * else
 * {
 * throw new TimoutException("Deadlock detected...");
 * }
 *
 * </code>
 *
 * @author <a href="mailto:matej.sekoranjaATcosylab.com">Matej Sekoranja</a>
 * @version $Id$
 */
final class ReferenceCountingLock {
    /**
     * Number of current locks.
     */
    private final AtomicInteger references = new AtomicInteger(1);

    /**
     * Synchronization mutex.
     */
    private final Lock lock = new ReentrantLock();

    /**
     * Constructor of <code>ReferenceCountingLock</code>.
     * After construction lock is free and reference count equals <code>1</code>.
     */
    public ReferenceCountingLock() {
        // no-op.
    }

    /**
     * Attempt to acquire lock.
     *
     * @param msecs the number of milliseconds to wait.
     *              An argument less than or equal to zero means not to wait at all.
     * @return <code>true</code> if acquired, <code>false</code> otherwise.
     */
    public boolean acquire(long msecs) {
        try {
            return lock.tryLock(msecs, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ie) {
            return false;
        }
    }

    /**
     * Release previously acquired lock.
     */
    public void release() {
        lock.unlock();
    }

    /**
     * Increment number of references.
     */
    public void increment() {
        references.incrementAndGet();
    }

    /**
     * Decrement number of references.
     *
     * @return number of references.
     */
    public int decrement() {
        return references.decrementAndGet();
    }

}
