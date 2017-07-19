package pancor.pl.ztmgdansk.data.remote.net

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module class NetModule {

    @Provides @Singleton fun provideHttpCache(app: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024
        return Cache(app.cacheDir, cacheSize)
    }

    @Provides @Singleton fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides @Singleton fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
        client.addInterceptor { chain ->
            val request = chain.request()
                    .newBuilder()
                    .build()
            chain.proceed(request)
        }
        return client.build()
    }

    @Provides @Singleton fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): NetService {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(""/* TODO add base URL*/)
                .client(okHttpClient)
                .build()
                .create(NetService::class.java)
    }
}