package woowacourse.shopping.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import woowacourse.shopping.BuildConfig
import woowacourse.shopping.data.api.ProductApi
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.interceptor.MockProductInterceptor

object NetworkModule {
    fun provideGson(): Gson = GsonBuilder().create()

    fun provideMockInterceptor(productDao: ProductDao): Interceptor = MockProductInterceptor(productDao)

    fun provideOkHttpClient(mockInterceptor: Interceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(mockInterceptor)
            .build()

    fun provideRetrofit(
        client: OkHttpClient,
        gson: Gson,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.MOCK_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    fun provideProductService(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)
}
