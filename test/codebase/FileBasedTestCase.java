package codebase;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * Base class for testcases doing tests with files.
 * 
 * @author Jeremias Maerki
 * @author Gareth Davis
 */
public abstract class FileBasedTestCase extends TestCase {

    private static File testDir;
   
    public static File getTestDirectory() {
        if (testDir == null) {
            testDir = (new File("test/io/")).getAbsoluteFile();
        }
        return testDir;
    }    
   


    protected void checkFile( File file, File referenceFile )
                throws Exception {
        assertTrue( "Check existence of output file", file.exists() );
        assertEqualContent( referenceFile, file );
    }

    /** Assert that the content of two files is the same. */
    private void assertEqualContent( File f0, File f1 )
        throws IOException
    {
        /*
         * This doesn't work because the filesize isn't updated until the file
         * is closed. assertTrue( "The files " + f0 + " and " + f1 + " have
         * differing file sizes (" + f0.length() + " vs " + f1.length() + ")", (
         * f0.length() == f1.length() ) );
         */
        InputStream is0 = new java.io.FileInputStream( f0 );
        try {
            InputStream is1 = new java.io.FileInputStream( f1 );
            try {
                byte[] buf0 = new byte[ 1024 ];
                byte[] buf1 = new byte[ 1024 ];
                int n0 = 0;
                int n1 = 0;

                while( -1 != n0 )
                {
                    n0 = is0.read( buf0 );
                    n1 = is1.read( buf1 );
                    assertTrue( "The files " + f0 + " and " + f1 +
                                " have differing number of bytes available (" + n0 +
                                " vs " + n1 + ")", ( n0 == n1 ) );

                    assertTrue( "The files " + f0 + " and " + f1 +
                                " have different content", Arrays.equals( buf0, buf1 ) );
                }
            } finally {
                is1.close();
            }
        } finally {
            is0.close();
        }
    }

    /** Assert that the content of a file is equal to that in a byte[]. */
    protected void assertEqualContent( byte[] b0, File file )
        throws IOException
    {
        InputStream is = new java.io.FileInputStream( file );
        try {
            byte[] b1 = new byte[ b0.length ];
            int numRead = is.read( b1 );
            assertTrue( "Different number of bytes", numRead == b0.length && is.available() == 0 );
            for( int i = 0;
                 i < numRead;
                 assertTrue( "Byte " + i + " differs (" + b0[ i ] + " != " + b1[ i ] + ")", 
                    b0[ i ] == b1[ i ] ), i++
                );
        } finally {
            is.close();
        }
    }

    /** Assert that the content of a file is equal to that in a char[]. */
    protected void assertEqualContent( char[] c0, File file )
        throws IOException
    {
        Reader ir = new java.io.FileReader( file );
        try {
            char[] c1 = new char[ c0.length ];
            int numRead = ir.read( c1 );
            assertTrue( "Different number of bytes", numRead == c0.length );
            for( int i = 0;
                 i < numRead;
                 assertTrue( "Byte " + i + " differs (" + c0[ i ] + " != " + c1[ i ] + ")", 
                    c0[ i ] == c1[ i ] ), i++
                );
        } finally {
            ir.close();
        }
    }

    protected void checkWrite(OutputStream output) throws Exception {
        try {
            new java.io.PrintStream(output).write(0);
        } catch (Throwable t) {
            throw new AssertionFailedError(
                "The copy() method closed the stream "
                    + "when it shouldn't have. "
                    + t.getMessage());
        }
    }

    protected void checkWrite(Writer output) throws Exception {
        try {
            new java.io.PrintWriter(output).write('a');
        } catch (Throwable t) {
            throw new AssertionFailedError(
                "The copy() method closed the stream "
                    + "when it shouldn't have. "
                    + t.getMessage());
        }
    }

    protected void deleteFile( File file )
        throws Exception {
        if (file.exists()) {
            assertTrue("Couldn't delete file: " + file, file.delete());
        }
    }
    

}
