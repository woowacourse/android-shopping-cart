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
import woowacourse.shopping.data.server.MockProductServer

object NetworkModule {
    private lateinit var productDao: ProductDao

    private val gson: Gson by lazy { GsonBuilder().create() }

    private val mockProductServer: MockProductServer by lazy {
        MockProductServer(productDao)
    }

    private val mockInterceptor: Interceptor by lazy {
        MockProductInterceptor(mockProductServer)
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .addInterceptor(mockInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.MOCK_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val productService: ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }

    fun init(productDao: ProductDao) {
        this.productDao = productDao
    }
}
