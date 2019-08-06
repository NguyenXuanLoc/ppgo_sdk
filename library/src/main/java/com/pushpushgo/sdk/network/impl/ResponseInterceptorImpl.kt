package com.pushpushgo.sdk.network.impl

import android.content.Context
import com.pushpushgo.sdk.network.ResponseInterceptor
import okhttp3.Interceptor
import okhttp3.Response


internal class ResponseInterceptorImpl(context: Context) : ResponseInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            if (response.code() == 401 || response.code() == 403) {
//                todo add handle auth
            }
            var body: String? = null
            try {
                val responseBodyCopy = response.peekBody(java.lang.Long.MAX_VALUE)
                body = responseBodyCopy.string()
            } catch (e: Exception) {}


        }
        return response
    }

}