package com.kdroid.kmplog

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration.Companion.seconds

internal var webSocketChannel: SendChannel<Frame>? = null
internal val connections = mutableSetOf<DefaultWebSocketServerSession>()
internal val mutex = Mutex()


@OptIn(DelicateCoroutinesApi::class)
internal fun startServer() {
    GlobalScope.launch {
        embeddedServer(CIO, port = 8080, host = "0.0.0.0") {

            install(CORS) {
                anyHost()
            }

            install(WebSockets) {
                pingPeriod = 15.seconds
                timeout = 15.seconds
                maxFrameSize = Long.MAX_VALUE
                masking = false
            }

            routing {
                webSocket("/log") {
                    // Assigner le canal de la nouvelle connexion
                    webSocketChannel = this.outgoing

                    // Ajouter la nouvelle connexion au set
                    mutex.withLock {
                        connections += this
                    }

                    try {
                        // Écouter les frames entrants
                        for (frame in incoming) {
                            if (frame is Frame.Text) {
                                val receivedText = frame.readText()

                                // Diffuser le message reçu à tous les clients connectés
                                mutex.withLock {
                                    connections.forEach { session ->
                                        launch {
                                            try {
                                                session.outgoing.send(Frame.Text(receivedText))
                                            } catch (e: Exception) {
                                                // Gérer les exceptions d'envoi (optionnel)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (e: ClosedReceiveChannelException) {
                        // Client déconnecté
                    } catch (e: Exception) {
                        // Gérer d'autres exceptions
                    } finally {
                        // Supprimer la connexion lorsqu'elle se ferme
                        mutex.withLock {
                            connections -= this
                        }
                    }
                }

                get("/") {
                    // Chargez le fichier HTML des ressources
                    val htmlFile = webClientHtml
                    call.respondText(htmlFile, ContentType.Text.Html)
                }
            }
        }.start(wait = false)
    }
}

internal suspend fun sendMessageToWebSocket(message: String) {
    mutex.withLock {
        connections.forEach { session ->
            try {
                session.outgoing.send(Frame.Text(message))
            } catch (e: Exception) {
                // Gérer les exceptions d'envoi (optionnel)
            }
        }
    }
}

val webClientHtml = """
 <!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WebSocket Logs</title>
    <style>
        :root {
            --bg-color-dark: #1e1e1e;
            --text-color-dark: #d4d4d4;
            --bg-color-light: #ffffff;
            --text-color-light: #1e1e1e;
            --primary-color: #007acc;
            --theme-color-1: #007acc;
            --theme-color-2: #ff5733;
            --theme-color-3: #28a745;
            --theme-color-4: #8e44ad;
            --theme-color-5: #f39c12;
            --theme-color-6: #e74c3c;
            --transition-duration: 0.5s;
            --html-gray-dark: #808080;
            --html-blue-dark: #0000FF;
            --html-green-dark: #008000;
            --html-yellow-dark: #FFFF00;
            --html-red-dark: #FF0000;
            --html-magenta-dark: #FF00FF;
            --html-gray-light: #a9a9a9;
            --html-blue-light: #4169e1;
            --html-green-light: #32cd32;
            --html-yellow-light: #ffd700;
            --html-red-light: #ff6347;
            --html-magenta-light: #ff77ff;
        }

        body {
            margin: 0;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: 'Courier New', Courier, monospace;
            background-color: var(--bg-color-dark);
            color: var(--text-color-dark);
            transition: background-color var(--transition-duration), color var(--transition-duration);
        }

        #logContainer {
            width: 80%;
            height: 80%;
            overflow-y: auto;
            padding: 20px;
            border-radius: 10px;
            border: 1px solid var(--primary-color);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            background-color: rgba(255, 255, 255, 0.8);
        }

        .message-dark {
            color: var(--text-color-dark);
        }

        .message-light {
            color: var(--text-color-light);
        }

        /* Custom Scrollbar Styles */
        #logContainer::-webkit-scrollbar {
            width: 12px;
        }

        #logContainer::-webkit-scrollbar-track {
            background: var(--bg-color-dark);
            border-radius: 10px;
        }

        #logContainer::-webkit-scrollbar-thumb {
            background: var(--primary-color);
            border-radius: 10px;
            border: 3px solid var(--bg-color-dark);
        }

        #logContainer::-webkit-scrollbar-thumb:hover {
            background: #005a9e;
        }

        #themeModeToggle {
            position: fixed;
            top: 20px;
            right: 60px;
            background: transparent;
            border: none;
            cursor: pointer;
            font-size: 24px;
            color: var(--text-color-dark);
            transition: color var(--transition-duration);
        }

        #fullScreenToggle {
            position: fixed;
            top: 20px;
            right: 20px;
            background: transparent;
            border: none;
            cursor: pointer;
            font-size: 24px;
            color: var(--text-color-dark);
            transition: color var(--transition-duration);
        }

        #connectionIndicator {
            position: fixed;
            bottom: 20px;
            left: 20px;
            width: 20px;
            height: 20px;
            border-radius: 50%;
            background-color: red;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
        }

        #themeButtons {
            position: fixed;
            bottom: 20px;
            right: 20px;
            display: flex;
            gap: 10px;
        }

        .themeButton {
            width: 20px;
            height: 20px;
            border-radius: 50%;
            cursor: pointer;
            border: none;
        }

        #themeButton1 { background-color: var(--theme-color-1); }
        #themeButton2 { background-color: var(--theme-color-2); }
        #themeButton3 { background-color: var(--theme-color-3); }

        #textSizeControls {
            position: fixed;
            bottom: 20px;
            display: flex;
            gap: 5px;
        }

        #increaseTextSize, #decreaseTextSize {
            background: var(--bg-color-light);
            border: 2px solid var(--primary-color);
            color: var(--primary-color);
            cursor: pointer;
            font-size: 16px;
            padding: 5px;
            width: 30px;
            height: 30px;
            border-radius: 50%;
            text-align: center;
            line-height: 1;
            transition: background-color var(--transition-duration), color var(--transition-duration), border-color var(--transition-duration);
        }

        #increaseTextSize:hover, #decreaseTextSize:hover {
            background-color: var(--primary-color);
            color: var(--bg-color-dark);
            border-color: var(--bg-color-dark);
        }
    </style>
</head>
<body>
<div id="title" style="position: fixed; top: 10px; font-size: 24px; font-weight: bold; color: var(--primary-color); text-align: center; width: 100%;">KMP Log Web Client</div>
<button id="themeModeToggle" style="color: var(--text-color-dark);">☽</button>
<button id="fullScreenToggle" style="color: var(--text-color-dark);">⛶</button>
<div id="themeButtons">
    <button id="themeButton4" class="themeButton" style="background-color: var(--theme-color-4);"></button>
    <button id="themeButton5" class="themeButton" style="background-color: var(--theme-color-5);"></button>
    <button id="themeButton6" class="themeButton" style="background-color: var(--theme-color-6);"></button>
    <button id="themeButton1" class="themeButton"></button>
    <button id="themeButton2" class="themeButton"></button>
    <button id="themeButton3" class="themeButton"></button>
</div>
<div id="logContainer"></div>
<div id="connectionIndicator"></div><span id="connectionStatus" style="position: fixed; bottom: 20px; left: 50px; font-size: 18px; color: var(--primary-color);">Disconnected</span>
<div id="textSizeControls">
    <button id="increaseTextSize">+</button>
    <button id="decreaseTextSize">-</button>
</div>
<script>
    const logContainer = document.getElementById('logContainer');
    const themeModeToggle = document.getElementById('themeModeToggle');
    const fullScreenToggle = document.getElementById('fullScreenToggle');

    fullScreenToggle.addEventListener('click', function () {
        if (!document.fullscreenElement) {
            document.documentElement.requestFullscreen().catch((err) => {
                console.warn(`Error attempting to enable full-screen mode: $\{err.message}`);
            });
        } else {
            document.exitFullscreen().catch((err) => {
                console.warn(`Error attempting to exit full-screen mode: $\{err.message}`);
            });
        }
    });
    const connectionIndicator = document.getElementById('connectionIndicator');
    const increaseTextSize = document.getElementById('increaseTextSize');
    const decreaseTextSize = document.getElementById('decreaseTextSize');
    const themeButtons = document.querySelectorAll('.themeButton');

    // Set the initial theme based on user preference or saved preference
    const savedTheme = localStorage.getItem('theme');
    let darkTheme = savedTheme ? (savedTheme === 'dark') : window.matchMedia("(prefers-color-scheme: dark)").matches;
    let currentFontSize = parseInt(localStorage.getItem('fontSize')) || 16;
    logContainer.style.fontSize = currentFontSize + 'px';

    function applyTheme() {
        document.body.style.transition = 'background-color var(--transition-duration), color var(--transition-duration)';
        logContainer.style.transition = 'background-color var(--transition-duration)';
        if (darkTheme) {
            document.body.style.backgroundColor = 'var(--bg-color-dark)';
            document.body.style.color = 'var(--text-color-dark)';
            logContainer.style.backgroundColor = 'rgba(0, 0, 0, 0.8)';
            themeModeToggle.innerHTML = '☽'; // Moon icon for dark mode
            themeModeToggle.style.color = 'var(--text-color-dark)';
            fullScreenToggle.style.color = 'var(--text-color-dark)';
            increaseTextSize.style.background = 'var(--bg-color-dark)';
            decreaseTextSize.style.background = 'var(--bg-color-dark)';
        } else {
            document.body.style.backgroundColor = 'var(--bg-color-light)';
            document.body.style.color = 'var(--text-color-light)';
            logContainer.style.backgroundColor = 'rgba(255, 255, 255, 0.8)';
            themeModeToggle.innerHTML = '☀'; // Sun icon for light mode
            themeModeToggle.style.color = 'var(--text-color-light)';
            fullScreenToggle.style.color = 'var(--text-color-light)';
            increaseTextSize.style.background = 'var(--bg-color-light)';
            decreaseTextSize.style.background = 'var(--bg-color-light)';
        }
    }

    function addMessageToLog(message) {
        const messageClass = darkTheme ? 'message-light' : 'message-dark';
        const messageElement = document.createElement('div');
        messageElement.className = messageClass;

        // Replace color tags with corresponding HTML colors
        let htmlGray = darkTheme ? 'var(--html-gray-dark)' : 'var(--html-gray-light)';
        let htmlBlue = darkTheme ? 'var(--html-blue-dark)' : 'var(--html-blue-light)';
        let htmlGreen = darkTheme ? 'var(--html-green-dark)' : 'var(--html-green-light)';
        let htmlYellow = darkTheme ? 'var(--html-yellow-dark)' : 'var(--html-yellow-light)';
        let htmlRed = darkTheme ? 'var(--html-red-dark)' : 'var(--html-red-light)';
        let htmlMagenta = darkTheme ? 'var(--html-magenta-dark)' : 'var(--html-magenta-light)';

        message = message.replace(/#808080/g, htmlGray)
            .replace(/#0000FF/g, htmlBlue)
            .replace(/#008000/g, htmlGreen)
            .replace(/#FFFF00/g, htmlYellow)
            .replace(/#FF0000/g, htmlRed)
            .replace(/#FF00FF/g, htmlMagenta);

        messageElement.innerHTML = message;
        logContainer.appendChild(messageElement);
        logContainer.scrollTop = logContainer.scrollHeight; // Auto-scroll to bottom
    }

    function updateMessagesTheme() {
        const messages = logContainer.getElementsByTagName('div');
        for (let i = 0; i < messages.length; i++) {
            if (darkTheme) {
                messages[i].classList.remove('message-light');
                messages[i].classList.add('message-dark');
            } else {
                messages[i].classList.remove('message-dark');
                messages[i].classList.add('message-light');
            }
        }
    }

    function changeFontSize(delta) {
        currentFontSize += delta;
        if (currentFontSize < 10) currentFontSize = 10; // Minimum font size
        logContainer.style.fontSize = currentFontSize + 'px';
        localStorage.setItem('fontSize', currentFontSize);
    }

    applyTheme();

    themeModeToggle.addEventListener('click', function () {
        darkTheme = !darkTheme;
        localStorage.setItem('theme', darkTheme ? 'dark' : 'light');
        applyTheme();
        updateMessagesTheme(); // Update the colors of existing messages
    });

    increaseTextSize.addEventListener('click', function () {
        changeFontSize(2);
    });

    decreaseTextSize.addEventListener('click', function () {
        changeFontSize(-2);
    });

    // Add event listeners for theme buttons to change the primary color
    themeButtons.forEach(button => {
        button.addEventListener('click', function () {
            const newPrimaryColor = getComputedStyle(button).backgroundColor;
            document.documentElement.style.setProperty('--primary-color', newPrimaryColor);
        });
    });

    // WebSocket connection with auto-reconnect
    function connectWebSocket() {
        const serverAddress = 'ws://10.0.0.3:8080/log';
        const socket = new WebSocket(serverAddress);

        socket.addEventListener('open', function (event) {
            connectionIndicator.style.backgroundColor = 'green';
            document.getElementById('connectionStatus').textContent = 'Connected';
            document.getElementById('connectionStatus').style.color = 'var(--primary-color)';
        });

        socket.addEventListener('message', function (event) {
            addMessageToLog(event.data);
        });

        socket.addEventListener('close', function (event) {
            if (!event.wasClean) {
                setTimeout(connectWebSocket, 1000); // Retry connection after 1 second
            }
            connectionIndicator.style.backgroundColor = 'red';
            document.getElementById('connectionStatus').textContent = 'Disconnected';
            document.getElementById('connectionStatus').style.color = 'var(--primary-color)';
        });

        socket.addEventListener('error', function (event) {
            socket.close(); // Close the socket on error to trigger reconnect
            connectionIndicator.style.backgroundColor = 'red';
            document.getElementById('connectionStatus').textContent = 'Disconnected';
        });
    }

    connectWebSocket();
</script>
</body>
</html>
""".trimIndent()