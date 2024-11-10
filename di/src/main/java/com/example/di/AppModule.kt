package com.example.di

import com.example.core.Constants
import com.example.data.local.AppDatabase
import com.example.data.local.dao.ProfileDao
import com.example.data.remote.api.AppApi
import com.example.data.remote.api.AuthApi
import com.example.data.remote.intercepter.AuthInterceptor
import com.example.data.remote.intercepter.MainInterceptor
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.ChatRepositoryImpl
import com.example.data.repository.DataStoreRepositoryImpl
import com.example.data.repository.ProfileRepositoryImpl
import com.example.data.repository.TokenRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.ChatRepository
import com.example.domain.repository.DataStoreRepository
import com.example.domain.repository.ProfileRepository
import com.example.domain.repository.TokenRepository
import com.example.domain.use_case.CheckAuthCodeUseCase
import com.example.domain.use_case.EditProfileUseCase
import com.example.domain.use_case.GetAccessTokenUseCase
import com.example.domain.use_case.GetChatUseCase
import com.example.domain.use_case.GetChatsUseCase
import com.example.domain.use_case.GetProfileUseCase
import com.example.domain.use_case.RegisterUseCase
import com.example.domain.use_case.SendAuthCodeUseCase
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.dsl.single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers

object AppModule {
    fun initKoin(
        appDeclaration: KoinAppDeclaration = {},
    ) = startKoin {
        modules(
            dataBaseModule,
            daoModule,
            datStorePreferences,
            retrofit,
            apiModule,
            repositories,
            useCases
        )
        appDeclaration()
    }


    private val useCases: Module = module {
        single { GetAccessTokenUseCase(get()) }
        single { SendAuthCodeUseCase(get()) }
        single { CheckAuthCodeUseCase(get(), get()) }
        single { RegisterUseCase(get(), get()) }
        single { GetProfileUseCase(get(), get()) }
        single { EditProfileUseCase(get(), get()) }
        single { GetChatsUseCase(get()) }
        single { GetChatUseCase(get()) }
    }


    private val repositories: Module = module {
        single<TokenRepository> { TokenRepositoryImpl(dataStoreRepository = get()) }
        single<AuthRepository> { AuthRepositoryImpl(api = get()) }
        single<ProfileRepository> { ProfileRepositoryImpl(api = get(), dao = get<ProfileDao>()) }
        single<ChatRepository> { ChatRepositoryImpl() }
    }


    private val retrofit: Module = module {
        single(named(AppRetrofitKey)) {
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get(named(AppOkHttpKey)))
                .build()
        }

        single(named(AuthRetrofitKey)) {
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get(named(AuthOkHttpKey)))
                .build()
        }

        single(named(AuthOkHttpKey)) {
            OkHttpClient.Builder()
                .addInterceptor(MainInterceptor())
                .build()
        }

        single(named(AppOkHttpKey)) {
            OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(get()))
                .build()
        }

    }
    private val apiModule: Module = module {
        factory<AuthApi> {
            val retrofit: Retrofit = get(named(AuthRetrofitKey))
            retrofit.create(AuthApi::class.java)
        }
        factory<AppApi> {
            val retrofit: Retrofit = get(named(AppRetrofitKey))
            retrofit.create(AppApi::class.java)
        }
    }

    private val dataBaseModule: Module = module {
        single {
            AppDatabase.getInstance(androidApplication())
        }
    }
    private val daoModule: Module = module {
        single<ProfileDao> {
            get<AppDatabase>().profileDao()
        }
    }

    private val datStorePreferences: Module = module {
        single<DataStoreRepository> { DataStoreRepositoryImpl(androidContext()) }
    }


    private const val AuthRetrofitKey = "auth_retrofit_key"
    private const val AppRetrofitKey = "app_retrofit_key"

    private const val AuthOkHttpKey = "auth_retrofit_key"
    private const val AppOkHttpKey = "app_retrofit_key"
}

