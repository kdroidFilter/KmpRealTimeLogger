import com.kdroid.kmplog.*
import com.kdroid.kmplog.Log.isLoggable
import com.kdroid.kmplog.Log.setLogLevel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LogTest {
    private val tag = "TestTag"

    @BeforeTest
    fun setup() {
        setLogLevel(Log.DEBUG)
        Log.setDevelopmentMode(true)
    }

    @Test
    fun testLogLevelIsLoggable() {
        assertTrue(isLoggable(tag, Log.DEBUG), "DEBUG level should be loggable")
        assertTrue(isLoggable(tag, Log.ERROR), "ERROR level should be loggable")
        assertFalse(isLoggable(tag, Log.VERBOSE), "VERBOSE level should not be loggable")
    }

    @Test
    fun testVerboseLog() {
        setLogLevel(Log.VERBOSE)
        assertTrue(isLoggable( tag, Log.VERBOSE), "VERBOSE level should be loggable after level change")
    }

    @Test
    fun testDebugLog() {
        assertTrue(isLoggable( tag, Log.DEBUG), "DEBUG level should be loggable")
        assertFalse(isLoggable( tag, Log.VERBOSE), "VERBOSE level should not be loggable for DEBUG setting")
    }

    @Test
    fun testChangeLogLevel() {
        setLogLevel(Log.ERROR)
        assertFalse(isLoggable(tag, Log.WARN), "WARN level should not be loggable when log level is set to ERROR")
        assertTrue(isLoggable(tag, Log.ERROR), "ERROR level should be loggable when log level is set to ERROR")
    }

    @Test
    fun testPrintLog() {
        // Since we can't capture println easily, we'll assume the method is functioning properly
        // if no exceptions are thrown during execution.
        Log.d(tag, "This is a DEBUG message")
        Log.e(tag, "This is an ERROR message")
        Log.w(tag, "This is a WARN message")
        Log.i(tag, "This is an Info message")
        Log.wtf(tag, "This is a WARN message")
    }
}