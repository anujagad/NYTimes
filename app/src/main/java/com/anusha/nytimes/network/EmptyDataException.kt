package com.anusha.nytimes.network

import okhttp3.Headers

class EmptyDataException(val headers: Headers) : Exception()