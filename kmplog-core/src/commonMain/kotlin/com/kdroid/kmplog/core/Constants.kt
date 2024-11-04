package com.kdroid.kmplog.core

//Logger config
const val MAX_TAG_LENGTH = 20
const val MAX_MSG_LENGTH = 100
const val SERVICE_TYPE = "_websocket._tcp.local."
const val ANDROID_SERVICE_TYPE = "_websocket._tcp."
const val SERVICE_NAME = "KmpLog"
const val SERVICE_PORT = 8180
const val SERVICE_PATH = "/log"
const val SERICE_DESCRIPTION = "KmpLog Websocket Service"

//Priority
const val VERBOSE = 2
const val DEBUG = 3
const val INFO = 4
const val WARN = 5
const val ERROR = 6
const val ASSERT = 7

//Ansi Colors
const val RESET = "\u001B[0m"
const val GRAY = "\u001B[90m"
const val BLUE = "\u001B[34m"
const val GREEN = "\u001B[32m"
const val YELLOW = "\u001B[33m"
const val RED = "\u001B[31m"
const val MAGENTA = "\u001B[35m"
