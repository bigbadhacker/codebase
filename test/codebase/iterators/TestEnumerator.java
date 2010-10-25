/*
 * Created on 3/Set/2005
 */
package codebase.iterators;

import codebase.tests.CodeBlock;
import codebase.tests.EnhancedTestCase;

public class TestEnumerator
        extends EnhancedTestCase {

    /**
     * Tests an enumerator with zero elements.
     */
    public final void testZero() {
        assertEquals(new EmptyIterator(), new Enumerator(0, 0));
        assertEquals(new EmptyIterator(), new Enumerator(1, 1));
        assertEquals(new EmptyIterator(), new Enumerator(7, 7));
    }

    /**
     * Tests an enumerator with one element.
     */
    public final void testOne() {
        assertEquals(new ArrayIterator(new Object[] {new Integer(0)}),
            new Enumerator(0, 1));
        assertEquals(new ArrayIterator(new Object[] {new Integer(0)}),
            new Enumerator(0, -1));
        assertEquals(new ArrayIterator(new Object[] {new Integer(1)}),
            new Enumerator(1, 2));
        assertEquals(new ArrayIterator(new Object[] {new Integer(-1)}),
            new Enumerator(-1, -2));
        assertEquals(new ArrayIterator(new Object[] {new Integer(7)}),
            new Enumerator(7, 8));
        assertEquals(new ArrayIterator(new Object[] {new Integer(-7)}),
            new Enumerator(-7, -8));
    }

    /**
     * Tests an enumerator with multiple element
     */
    public final void testMultiple() {
        assertEquals(new ArrayIterator(new Object[] {
                new Integer(0), new Integer(1), new Integer(2)}),
            new Enumerator(0, 3));
        assertEquals(new ArrayIterator(new Object[] {
                new Integer(0), new Integer(-1), new Integer(-2)}),
            new Enumerator(0, -3));
        assertEquals(new ArrayIterator(new Object[] {
                new Integer(2), new Integer(3), new Integer(4)}),
            new Enumerator(2, 5));
        assertEquals(new ArrayIterator(new Object[] {
                new Integer(-2), new Integer(-3), new Integer(-4)}),
            new Enumerator(-2, -5));
    }

    /**
     * Tests that the reset method works correclt
     */
    public final void testReset() {
        Enumerator e = new Enumerator(0, 3);
        assertEquals(e.next(), new Integer(0));
        assertEquals(e.next(), new Integer(1));
        e.reset();
        assertEquals(new ArrayIterator(new Object[] {
                new Integer(0), new Integer(1), new Integer(2)}), e);
    }


    /**
     * Tests that the reset method works correclt
     */
    public final void testPeek() {
        final Enumerator e = new Enumerator(0, 3);
        assertEquals(e.peek(), new Integer(0));
        assertEquals(e.next(), new Integer(0));

        // Peek is idempotent
        assertEquals(e.peek(), new Integer(1));
        assertEquals(e.peek(), new Integer(1));

        assertEquals(e.next(), new Integer(1));
        assertEquals(e.next(), new Integer(2));
       
        assertTrue(e.supportsPeek());

        assertThrows(new CodeBlock() {
            public void execute() {
                e.peek();
            }
        }, new IllegalStateException());
    }
}