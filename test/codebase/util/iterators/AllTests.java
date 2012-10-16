package codebase.util.iterators;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(TestArrayIterator.class);
        suite.addTestSuite(TestBoundedIterator.class);
        suite.addTestSuite(TestRepeater.class);
        suite.addTestSuite(TestEnumerator.class);
        //$JUnit-END$
        return suite;
    }
}
