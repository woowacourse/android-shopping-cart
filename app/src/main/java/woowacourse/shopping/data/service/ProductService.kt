package woowacourse.shopping.data.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.entity.ProductEntity
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

class ProductService(private val mockServer: MockWebServer) {
    private val gson = Gson()

    private val client =
        OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()

    fun products(): List<ProductEntity> {
        val request = Request.Builder().url("${mockServer.url("/products")}").build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: error("주소를 잘못 입력했습니다.")
        val type: Type = object : TypeToken<List<ProductEntity>>() {}.type
        return gson.fromJson(responseBody, type)
    }

    fun productsByOffset(
        startPosition: Int,
        offset: Int,
    ): List<ProductEntity> {
        val request =
            Request.Builder().url("${mockServer.url("/products/$startPosition/$offset")}").build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: error("주소를 잘못 입력했습니다.")
        val type: Type? = object : TypeToken<List<ProductEntity>>() {}.type
        return gson.fromJson(responseBody, type)
    }

    fun productById(productId: Long): ProductEntity {
        val request =
            Request.Builder().url("${mockServer.url("/products/${productId.toInt()}")}").build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: error("주소를 잘못 입력했습니다.")
        val type: Type? = object : TypeToken<ProductEntity>() {}.type
        return gson.fromJson(responseBody, type)
    }

    companion object {
        private const val TIME_OUT = 4L
    }
}
