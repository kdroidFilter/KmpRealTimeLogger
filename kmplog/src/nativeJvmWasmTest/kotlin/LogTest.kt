
import com.kdroid.kmplog.*
import com.kdroid.kmplog.Log.isLoggable
import com.kdroid.kmplog.Log.setLogLevel
import com.kdroid.kmplog.core.DEBUG
import com.kdroid.kmplog.core.ERROR
import com.kdroid.kmplog.core.VERBOSE
import com.kdroid.kmplog.core.WARN
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LogTest {
    private val tag = "TestTag"

    @BeforeTest
    fun setup() {
        setLogLevel(DEBUG)
        Log.setDevelopmentMode(true)
    }

    @Test
    fun testLogLevelIsLoggable() {
        assertTrue(isLoggable(tag, DEBUG), "DEBUG level should be loggable")
        assertTrue(isLoggable(tag, ERROR), "ERROR level should be loggable")
        assertFalse(isLoggable(tag, VERBOSE), "VERBOSE level should not be loggable")
    }

    @Test
    fun testVerboseLog() {
        setLogLevel(VERBOSE)
        assertTrue(isLoggable( tag, VERBOSE), "VERBOSE level should be loggable after level change")
    }

    @Test
    fun testDebugLog() {
        assertTrue(isLoggable( tag, DEBUG), "DEBUG level should be loggable")
        assertFalse(isLoggable( tag,VERBOSE), "VERBOSE level should not be loggable for DEBUG setting")
    }

    @Test
    fun testChangeLogLevel() {
        setLogLevel(ERROR)
        assertFalse(isLoggable(tag, WARN), "WARN level should not be loggable when log level is set to ERROR")
        assertTrue(isLoggable(tag, ERROR), "ERROR level should be loggable when log level is set to ERROR")
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
