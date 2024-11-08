package com.example.data.remote.intercepter

import okhttp3.Interceptor
import okhttp3.Response

class MainInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder()
            .headers(original.headers)
            .addHeader(
                "accept",
                "application/json"
            )
            .addHeader(
                "Content-Type",
                "application/json"
            )

            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }
}