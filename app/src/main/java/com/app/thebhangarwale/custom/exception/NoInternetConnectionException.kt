package com.app.thebhangarwale.custom.exception

import java.io.IOException

class NoInternetConnectionException(override var message: String?=null) : IOException(message)