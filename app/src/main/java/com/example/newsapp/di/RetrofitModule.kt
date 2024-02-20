package com.example.newsapp.di

import android.content.Context
import com.example.newsapp.BuildConfig
import com.example.newsapp.data.articles.datasource.remote.NewsApiServices
import com.example.newsapp.util.Constants.Defaults.BASE_URL
import com.example.newsapp.util.Constants.Defaults.DATE_FORMAT_SERVER

import com.example.newsapp.util.NetworkConnectionInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val REQUEST_TIME_OUT: Long = 60

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        logging: HttpLoggingInterceptor,
        connectivityInterceptor: Interceptor,
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(connectivityInterceptor)

        if (BuildConfig.DEBUG) {
            okHttpClient.addNetworkInterceptor(logging)
        }

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setDateFormat(DATE_FORMAT_SERVER)
            .setLenient()
            .create()
    }


    @Provides
    @Singleton
    fun provideConnectivityInterceptor(@ApplicationContext context : Context): Interceptor {
        return NetworkConnectionInterceptor(context)
    }


    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideArticlesServices(retrofit: Retrofit): NewsApiServices {
        return retrofit.create(NewsApiServices::class.java)
    }

}
