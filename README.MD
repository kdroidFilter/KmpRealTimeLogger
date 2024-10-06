# KMPLog

KMPLog is a Kotlin Multiplatform logging library that replicates the functionality of Android's Log library for use in common code across all platforms (Native, JVM, JS, WASM, and Android).

## Features

- Supports all major platforms: Native, JVM, JS, WASM, and Android
- Mimics the Android Log API for familiar usage
- Configurable log levels
- Platform-specific implementations

## Installation

[Add installation instructions here, e.g., Gradle dependencies]

## Usage

### Basic Logging

```kotlin
import com.kdroid.kmplog.Log

Log.d("MyTag", "This is a debug message")
Log.i("MyTag", "This is an info message")
Log.e("MyTag", "This is an error message")
```

### Setting Log Level

```kotlin
Log.setLogLevel(Log.INFO)
```

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
- `Log.e(tag: String, msg: String)`: Log an ERROR message
- `Log.wtf(tag: String, msg: String)`: Log a WTF (What a Terrible Failure) message
- `Log.println(priority: Int, tag: String, msg: String)`: Log a message with custom priority

## Platform-Specific Details

[Add any platform-specific implementation details or usage notes here]

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

[Add your chosen license here]
