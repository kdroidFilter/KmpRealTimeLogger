# KMPLog

KMPLog is a Kotlin Multiplatform logging library that replicates the functionality of Android's Log library for use in common code across all platforms (Native, JVM, JS, WASM, and Android).

## Features

- Supports all major platforms: Native, JVM, JS, WASM, and Android
- Mimics the Android Log API for familiar usage
- Configurable log levels
- Platform-specific implementations
- Available on Maven Central
- Throwable support in `Log.e()` for detailed error logging including stack traces
- Development mode setting for controlling logging in production vs development phases

## Installation

Add the following dependency to your `build.gradle.kts` file:

```kotlin
implementation("io.github.kdroidfilter:kmplog:version")
```

KMPLog is available on Maven Central, ensuring easy integration into your projects.

## Usage

### Basic Logging

```kotlin
import com.kdroid.kmplog.Log

Log.d("MyTag", "This is a debug message")
Log.i("MyTag", "This is an info message")
Log.e("MyTag", "This is an error message")
Log.e("MyTag", "This is an error message with exception", throwable)
```

### Setting Log Level

```kotlin
Log.setLogLevel(Log.INFO)
```

### Setting Development Mode

```kotlin
Log.setDevelopmentMode(true)
```

Logs will only be displayed when development mode is enabled, providing better control over logging during production and development phases.

### Checking if a Log Level is Loggable

```kotlin
if (Log.isLoggable("MyTag", Log.DEBUG)) {
    // Perform debug logging
}
```

## API Reference

- `Log.v(tag: String, msg: String)`: Log a VERBOSE message
- `Log.d(tag: String, msg: String)`: Log a DEBUG message
- `Log.i(tag: String, msg: String)`: Log an INFO message
- `Log.w(tag: String, msg: String)`: Log a WARN message
- `Log.e(tag: String, msg: String, throwable: Throwable?)`: Log an ERROR message, optionally with a throwable for detailed error logging
- `Log.wtf(tag: String, msg: String)`: Log a WTF (What a Terrible Failure) message
- `Log.println(priority: Int, tag: String, msg: String)`: Log a message with custom priority
- `Log.setDevelopmentMode(isDevelopment: Boolean)`: Enable or disable development mode
- `Log.setLogLevel(level: Int)`: Set the log level

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

KMPLog is released under the MIT License. See the [LICENSE](LICENSE) file for details.

