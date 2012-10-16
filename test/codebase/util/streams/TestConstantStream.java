/*
 * Created on 7/Jun/2005
 */
package codebase.util.streams;


import java.io.IOException;

import codebase.util.streams.ConstantInputStream;


import junit.framework.TestCase;

/**
 * Tests the constant stream.
 */
public class TestConstantStream
        extends TestCase {

    /**
     * Tests that reading form the one element stream always returns the same
     * element.
     */
    public final void testReadOneElement() {
        ConstantInputStream s = new ConstantInputStream("X");
        try {
            // basic
            assertEquals(s.read(), 'X');
            assertEquals(s.read(), 'X');

            // with an array
            byte[] b = new byte[2];
            assertEquals(s.read(b), 2);
            assertEquals(b[0], 'X');
            assertEquals(b[1], 'X');
        } catch (IOException e) {
            fail("Stream read error");
        }
    }

    /**
     * Tests that reading multiple elements correctly returns the buffer
     * elements.
     */
    public void testReadMultipleElement() {
        ConstantInputStream s = new ConstantInputStream("XYZ");
        try {
            // Basic
            assertEquals(s.read(), 'X');
            assertEquals(s.read(), 'Y');
            assertEquals(s.read(), 'Z');
            assertEquals(s.read(), 'X');

            // with an array
            byte[] b = new byte[2];
            assertEquals(s.read(b), 2);
            assertEquals(b[0], 'Y');
            assertEquals(b[1], 'Z');

            assertEquals(s.read(), 'X');
        } catch (IOException e) {
            fail("Stream read error");
        }
    }

    /**
     * Tests that skipping elements form the buffer correctly cycles through the
     * buffer.
     */
    public void testSkipMultipleElement() {
        ConstantInputStream s = new ConstantInputStream("XYZ");
        try {
            assertEquals(s.read(), 'X');
            s.skip(2);
            assertEquals(s.read(), 'X');
        } catch (IOException e) {
            fail("Stream read error");
        }
    }
}
