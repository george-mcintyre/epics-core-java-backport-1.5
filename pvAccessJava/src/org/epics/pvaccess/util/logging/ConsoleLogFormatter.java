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

package org.epics.pvaccess.util.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * An implementation of <code>java.util.logging.Formatter</code>.
 * Produces single line log reports meant to go to the console.
 *
 * @author Matej Sekoranja (matej.sekoranjaATcosylab.com)
 */
public class ConsoleLogFormatter extends Formatter {
    /**
     * System property key to enable trace messages.
     */
    public static String KEY_TRACE = "TRACE";
    /**
     * Line separator string.
     */
    private final boolean showTrace = System.getProperties().containsKey(KEY_TRACE);

    /**
     * Line separator string.
     */
    private static final String lineSeparator = System.getProperty("line.separator");

    /**
     * ISO 8601 date formatter.
     */
    private static final SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    /**
     * Date object (used not to recreate it every time).
     */
    private final Date date = new Date();

    /**
     * Format the given LogRecord.
     *
     * @param record the log record to be formatted.
     * @return a formatted log record
     */
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder(128);

        synchronized (date) {
            date.setTime(record.getMillis());
            sb.append(timeFormatter.format(date));
        }

        sb.append(' ');
        sb.append(record.getMessage());
        sb.append(' ');

        // trace
        if (showTrace) {
            // source
            sb.append('[');
            if (record.getSourceClassName() != null)
                sb.append(record.getSourceClassName());

            // method name
            if (record.getSourceMethodName() != null) {
                sb.append('#');
                sb.append(record.getSourceMethodName());
            }
            sb.append(']');
        }

        sb.append(lineSeparator);


        // exceptions
        if (record.getThrown() != null) {
            record.getThrown().printStackTrace();
        }

        return new String(sb);
    }

}
