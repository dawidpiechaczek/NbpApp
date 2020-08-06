package pl.piechaczek.dawid.nbpapp.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import pl.piechaczek.dawid.core.ui.LocalDateTimeAdapter
import pl.piechaczek.dawid.nbpapp.BuildConfig
import pl.piechaczek.dawid.nbpapp.NbpService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@Module
object NetworkModule {

    private const val CONNECT_TIMEOUT = 10000L
    private const val READ_TIMEOUT = 10000L

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)

        return okHttpBuilder.build()
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(LocalDateTimeAdapter())
            .build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        @ApiUrl apiUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    @ApiUrl
    fun provideApiUrl() = BuildConfig.API_URL

    @Provides
    fun provideNbpService(retrofit: Retrofit): NbpService {
        return retrofit.create(NbpService::class.java)
    }
}

@Qualifier
@Retention
annotation class ApiUrl