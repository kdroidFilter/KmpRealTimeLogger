# KMP RealTime Logger 🚀

KMP RealTime Logger is a Kotlin Multiplatform logging library that replicates the functionality of Android's Log library for use in common code across all platforms (Native, JVM, JS, WASM, and Android).

## Features ✨

- Supports all major platforms: Native, JVM, JS, WASM, and Android 🌍
- Mimics the Android Log API for familiar usage 🤖
- Configurable log levels 📊
- Platform-specific implementations ⚙️
- Available on Maven Central 📦
- Throwable support in `Log.e()` for detailed error logging including stack traces 🐞
- Development mode setting for controlling logging in production vs development phases 🚼️
- Live log broadcasting to another device on the same local network 🛡️

## Installation 🚠

Add the following dependency to your `build.gradle.kts` file:

```kotlin
implementation("io.github.kdroidfilter:kmplog:0.6.2")
```

KMPLog is available on Maven Central, ensuring easy integration into your projects.

## Usage 📋

### Basic Logging 📝

```kotlin
import com.kdroid.kmplog.Log

Log.d("MyTag", "This is a debug message")
Log.i("MyTag", "This is an info message")
Log.e("MyTag", "This is an error message")
Log.e("MyTag", "This is an error message with exception", throwable)
```

### Setting Log Level 🔧

```kotlin
Log.setLogLevel(Log.INFO)
```

### Setting Development Mode 🚼️

```kotlin
Log.setDevelopmentMode(true)
```

Logs will only be displayed when development mode is enabled, providing better control over logging during production and development phases.

### Enabling Broadcasting Mode 🛡️
⚠️ Note: It is strongly recommended to enable broadcasting only in debug mode, for instance using BuildConfig.DEBUG on Android. Broadcasting logs in production environments could expose sensitive information. ⚠️
KMPLog allows you to broadcast logs live to another device on the same local network. This can be useful for real-time log monitoring during development or debugging sessions.

To enable broadcasting mode, use the following function:

```kotlin
Log.enableBroadcastingMode(ip: String? = null, port: Int = DEFAULT_SERVICE_PORT)
```

- `ip`: Optional. Specify the IP address of the client device to receive the logs. If not provided, the library will automatically discover and connect to the client device using mDNS (Multicast DNS) services.
- `port`: Optional. Specify the port to use for broadcasting. By default, it uses the `DEFAULT_SERVICE_PORT`.

Example:

```kotlin
Log.enableBroadcastingMode("192.168.1.10", 8080)
```

This will broadcast logs to the device with IP `192.168.1.10` on port `8080`.

⚠️ Note: 
When using the KMPLog library on an Android emulator, it is important to ensure that the emulator is connected to the Wi-Fi network. This allows the emulator to properly communicate over the local network for broadcasting and receiving logs.
### Checking if a Log Level is Loggable 🔍

```kotlin
if (Log.isLoggable("MyTag", Log.DEBUG)) {
    // Perform debug logging
}
```

## Client Application 📱💻

<p align="center">
    <img src="assets/icon.png" alt="Android 1" height="150">
</p>

To facilitate real-time log monitoring, we have also developed a client application that listens to the logs broadcasted over the local network.

The client application uses mDNS to discover broadcasting devices automatically and display the logs in real-time. Note that automatic detection using mDNS only works from an Android or JVM application.

### Features of the Client Application 🌟

- Automatic discovery of broadcasting devices using mDNS 🔍
- Real-time log display with filters for different log levels (DEBUG, INFO, WARN, ERROR) 📊
- Easy setup with minimal configuration required ⚙️

### Screenshots 🖼️

Below are some screenshots demonstrating the functionality of the client application:

- **Real-Time Log Monitoring** 🛡️

<p align="center">
    <img src="assets/android_01.png" alt="Android 1" height="400">
</p>

<p align="center">
  <img src="assets/desktop_01.png" alt="Desktop 1" height="400">
</p>

### Installation and Setup 📥

The client application is available for installation on Android, Windows and Linux. Please download it on the release section.

## Under the Hood 🚀

The client application works by creating a WebSocket server on the local network using Ktor. This server listens for incoming connections from devices broadcasting logs. The server is also published as an mDNS (Multicast DNS) service, making it discoverable by other devices on the same local network without requiring manual configuration.

The KMPLog library itself connects to this WebSocket server using Ktor's WebSocket client capabilities. By leveraging Ktor's lightweight and asynchronous nature, the setup remains efficient and allows real-time log streaming with minimal latency. This architecture enables developers to easily monitor application logs during development or debugging sessions across different platforms.

## API Reference 📚

- `Log.v(tag: String, msg: String)`: Log a VERBOSE message 🗣️
- `Log.d(tag: String, msg: String)`: Log a DEBUG message 🐛
- `Log.i(tag: String, msg: String)`: Log an INFO message ℹ️
- `Log.w(tag: String, msg: String)`: Log a WARN message ⚠️
- `Log.e(tag: String, msg: String, throwable: Throwable?)`: Log an ERROR message, optionally with a throwable for detailed error logging ❌
- `Log.wtf(tag: String, msg: String)`: Log a WTF (What a Terrible Failure) message 😱
- `Log.println(priority: Int, tag: String, msg: String)`: Log a message with custom priority 📝
- `Log.setDevelopmentMode(isDevelopment: Boolean)`: Enable or disable development mode 🚼️
- `Log.setLogLevel(level: Int)`: Set the log level 🔧
- `Log.enableBroadcastingMode(ip: String? = null, port: Int = DEFAULT_SERVICE_PORT)`: Enable broadcasting mode to stream logs to another device on the local network 🛡️

## Contributing 🤝

Contributions are welcome! Please feel free to submit a Pull Request. 💡

The desktop version of the client application uses the Jewel theme, which requires the JetBrains JDK to build the application. Please ensure that you have the JetBrains JDK installed and properly configured when building the desktop client.

## License 📄

KMPLog is released under the MIT License. See the [LICENSE](LICENSE) file for details.

