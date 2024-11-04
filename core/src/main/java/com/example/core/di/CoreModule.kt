package com.example.core.di

import com.example.core.data.local.AppDatabase
import com.example.core.data.remote.api.ApiService
import com.example.core.data.remote.intercepter.AuthInterceptor
import com.example.core.data.repository.AuthRepositoryImpl
import com.example.core.data.repository.DataStoreRepositoryImpl
import com.example.core.domain.repository.AuthRepository
import com.example.core.domain.repository.DataStoreRepository
import com.example.core.utils.Constants
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.KoinAppDeclaration
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoreModule {
    fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
        appDeclaration()
        modules(
            dataBaseModule,
            datStorePreferences,
            repositories,
            retrofit
        )
    }

    private val dataBaseModule: Module = module {
        single { AppDatabase.getInstance(androidContext()) }
    }
    private val datStorePreferences: Module = module {
        single<DataStoreRepository> { DataStoreRepositoryImpl(androidContext()) }
    }

    private val retrofit: Module = module {
        single<Retrofit> {
            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(get()))
                .build()

            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        single<ApiService> {
            val retrofit: Retrofit = get()
            retrofit.create(ApiService::class.java)
        }
    }

    private val repositories: Module = module {
        single<AuthRepository> {
            AuthRepositoryImpl(
                dataStoreRepository = get(),
                apiService = get()
            )
        }
    }
}