package com.pushpushgo.sdk.di

import android.content.Context
import com.pushpushgo.sdk.network.ApiRepository
import com.pushpushgo.sdk.network.ApiService
import com.pushpushgo.sdk.network.SharedPreferencesHelper
import com.pushpushgo.sdk.network.interceptor.RequestInterceptor
import com.pushpushgo.sdk.network.interceptor.ResponseInterceptor
import com.pushpushgo.sdk.utils.PlatformType
import com.pushpushgo.sdk.utils.getPlatformType
import org.kodein.di.*

internal class NetworkModule(context: Context, apiKey: String, projectId: String, isDebug: Boolean) : DIAware {

    companion object {
        const val API_KEY = "api_key"
        const val PROJECT_ID = "project_id"
    }

    override val di by DI.lazy {
        constant(tag = API_KEY) with apiKey
        constant(tag = PROJECT_ID) with projectId
        bind<PlatformType>() with singleton { getPlatformType() }
        bind<Context>() with provider { context }
        bind<RequestInterceptor>() with singleton { RequestInterceptor() }
        bind<ResponseInterceptor>() with singleton { ResponseInterceptor() }
        bind<SharedPreferencesHelper>() with singleton { SharedPreferencesHelper(instance()) }
        bind<ApiService>() with singleton {
            ApiService(
                instance(),
                instance(),
                instance(),
                isNetworkDebug = isDebug,
            )
        }
        bind<ApiRepository>() with singleton {
            ApiRepository(
                instance(),
                instance(),
                instance(),
                instance(PROJECT_ID),
                instance(API_KEY),
            )
        }
    }

    val sharedPref by instance<SharedPreferencesHelper>()
    val apiRepository by instance<ApiRepository>()
}
