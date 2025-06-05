package woowacourse.shopping.data.goods.repository

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import woowacourse.shopping.BuildConfig
import woowacourse.shopping.data.goods.GoodsDto
import woowacourse.shopping.data.util.mapper.toDomain
import woowacourse.shopping.domain.model.Goods
import java.io.IOException

class GoodsRemoteDataSourceImpl(
    private val baseUrl: String = BuildConfig.BASE_URL,
    private val okHttpClient: OkHttpClient,
) : GoodsRemoteDataSource {
    private val gson = Gson()

    override fun fetchGoodsSize(onComplete: (Int) -> Unit) {
        val request = createGetRequest("products/size")
        executeRequest(request) { jsonString ->
            val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)
            val size = jsonObject.get("size")?.asInt ?: 0
            onComplete(size)
        }
    }

    override fun fetchPageGoods(
        limit: Int,
        offset: Int,
        onComplete: (List<Goods>) -> Unit,
    ) {
        val request = createGetRequest("products?limit=$limit&offset=$offset")
        executeRequestForGoodsList(request, onComplete)
    }

    override fun fetchGoodsById(
        id: Int,
        onComplete: (Goods?) -> Unit,
    ) {
        val request = createGetRequest("products/$id")
        executeRequest(request) { jsonString ->
            try {
                val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)
                if (jsonObject.has("product")) {
                    val productJsonString = jsonObject.get("product").toString()
                    val goodsDto = gson.fromJson(productJsonString, GoodsDto::class.java)
                    onComplete(goodsDto.toDomain())
                } else {
                    onComplete(null)
                }
            } catch (e: Exception) {
                onComplete(null)
            }
        }
    }

    override fun fetchGoodsByIds(
        ids: List<Int>,
        onComplete: (List<Goods>?) -> Unit,
    ) {
        val requestBody = gson.toJson(mapOf("ids" to ids))
        val request = createPostRequest("products/ids", requestBody)

        executeRequestForGoodsList(request) { goodsList ->
            onComplete(goodsList)
        }
    }

    private fun createBaseRequestBuilder(): Request.Builder =
        Request
            .Builder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")

    private fun createGetRequest(endpoint: String): Request =
        createBaseRequestBuilder()
            .url("$baseUrl/$endpoint")
            .build()

    private fun createPostRequest(
        endpoint: String,
        jsonBody: String,
    ): Request {
        val requestBody =
            jsonBody.toRequestBody("application/json".toMediaTypeOrNull())
        return createBaseRequestBuilder()
            .url("$baseUrl/$endpoint")
            .post(requestBody)
            .build()
    }

    private fun executeRequest(
        request: Request,
        onSuccess: (String) -> Unit,
    ) {
        okHttpClient.newCall(request).enqueue(
            object : Callback {
                override fun onResponse(
                    call: Call,
                    response: Response,
                ) {
                    response.body?.string()?.let { jsonString ->
                        if (response.isSuccessful) {
                            onSuccess(jsonString)
                        }
                    }
                }

                override fun onFailure(
                    call: Call,
                    e: IOException,
                ) {
                    // todo 에러 처리
                }
            },
        )
    }

    private fun executeRequestForGoodsList(
        request: Request,
        onComplete: (List<Goods>) -> Unit,
    ) {
        executeRequest(request) { jsonString ->
            try {
                val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)
                val productsJsonArray = jsonObject.getAsJsonArray("products")
                val listType = object : TypeToken<List<GoodsDto>>() {}.type
                val goodsDtoList: List<GoodsDto> = gson.fromJson(productsJsonArray, listType)
                onComplete(goodsDtoList.toDomain())
            } catch (e: Exception) {
                onComplete(emptyList())
            }
        }
    }
}
