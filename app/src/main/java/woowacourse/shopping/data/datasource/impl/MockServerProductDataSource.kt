package woowacourse.shopping.data.datasource.impl

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.entity.ProductEntity
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MockServerProductDataSource(private val mockServer: MockWebServer) : ProductDataSource {
    private val gson = Gson()

    private val client =
        OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()

    override fun products(): List<ProductEntity> {
        var products = emptyList<ProductEntity>()
        thread {
            val request = Request.Builder().url("${mockServer.url("/products")}").build()
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: error("주소를 잘못 입력했습니다.")
            val type: Type = object : TypeToken<List<ProductEntity>>() {}.type
            products = gson.fromJson(responseBody, type)
        }.join()
        return products
    }

    override fun productsByOffset(
        startPosition: Int,
        offset: Int,
    ): List<ProductEntity> {
        var products = emptyList<ProductEntity>()
        thread {
            val request =
                Request.Builder().url("${mockServer.url("/products/$startPosition/$offset")}").build()
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: error("주소를 잘못 입력했습니다.")
            val type: Type? = object : TypeToken<List<ProductEntity>>() {}.type
            products = gson.fromJson(responseBody, type)
        }.join()
        return products
    }

    override fun productById(productId: Long): ProductEntity {
        var product = ProductEntity.STUB_LIST.first()
        thread {
            val request =
                Request.Builder().url("${mockServer.url("/product/${productId.toInt()}")}").build()
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: error("주소를 잘못 입력했습니다.")
            val type: Type? = object : TypeToken<ProductEntity>() {}.type
            product = gson.fromJson(responseBody, type)
        }.join()
        return product
    }

    companion object {
        private const val TIME_OUT = 4L
    }
}
