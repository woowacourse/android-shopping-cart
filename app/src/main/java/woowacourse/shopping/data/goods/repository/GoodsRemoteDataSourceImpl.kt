package woowacourse.shopping.data.goods.repository

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import woowacourse.shopping.BuildConfig
import woowacourse.shopping.data.goods.GoodsDto
import woowacourse.shopping.data.util.ApiEndpoints.PRODUCTS
import woowacourse.shopping.data.util.MockInterceptor
import woowacourse.shopping.data.util.mapper.toDomain
import woowacourse.shopping.domain.model.Goods
import java.io.IOException

class GoodsRemoteDataSourceImpl(
    private val baseUrl: String = BuildConfig.BASE_URL,
    private val useInterceptor: Boolean = true,
) : GoodsRemoteDataSource {
    private val okHttpClient =
        OkHttpClient
            .Builder()
            .apply {
                if (useInterceptor) {
                    addInterceptor(MockInterceptor())
                }
            }.build()
    private val gson = Gson()

    override fun fetchGoodsSize(onComplete: (Int) -> Unit) {
        val request =
            Request
                .Builder()
                .url("$baseUrl/$PRODUCTS/size")
                .build()
        fetchGoodsSizeRequest(request, onComplete)
    }

    override fun fetchPageGoods(
        limit: Int,
        offset: Int,
        onComplete: (List<Goods>) -> Unit,
    ) {
        val request =
            Request
                .Builder()
                .url("$baseUrl/$PRODUCTS?limit=$limit&offset=$offset")
                .build()
        fetchPageGoodsRequest(request, onComplete)
    }

    override fun fetchGoodsById(
        id: Int,
        onComplete: (Goods?) -> Unit,
    ) {
        val request =
            Request
                .Builder()
                .url("$baseUrl/$PRODUCTS/$id")
                .build()
        fetchGoodsByIdRequest(request, onComplete)
    }

    private fun fetchPageGoodsRequest(
        request: Request,
        onComplete: (List<Goods>) -> Unit,
    ) {
        okHttpClient.newCall(request).enqueue(
            object : Callback {
                override fun onResponse(
                    call: Call,
                    response: Response,
                ) {
                    val responseBody = response.body
                    if (responseBody != null) {
                        val jsonString = responseBody.string()
                        if (jsonString.isNotEmpty()) {
                            try {
                                val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)
                                val productsJsonArray = jsonObject.getAsJsonArray(PRODUCTS)
                                val listType = object : TypeToken<List<GoodsDto>>() {}.type
                                val goodsDtoList: List<GoodsDto> =
                                    gson.fromJson(productsJsonArray, listType)
                                val goods = goodsDtoList.toDomain()

                                onComplete(goods)
                            } catch (e: Exception) {
                                onComplete(emptyList())
                            }
                        }
                    }
                }

                override fun onFailure(
                    call: Call,
                    e: IOException,
                ) {
                }
            },
        )
    }

    private fun fetchGoodsSizeRequest(
        request: Request,
        onComplete: (Int) -> Unit,
    ) {
        okHttpClient.newCall(request).enqueue(
            object : Callback {
                override fun onResponse(
                    call: Call,
                    response: Response,
                ) {
                    response.body?.string()?.let { jsonString ->
                        try {
                            val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)
                            val size = jsonObject.get("size")?.asInt ?: 0
                            onComplete(size)
                        } catch (e: Exception) {
                            onComplete(0)
                        }
                    } ?: onComplete(0)
                }

                override fun onFailure(
                    call: Call,
                    e: IOException,
                ) {
                    onComplete(0)
                }
            },
        )
    }

    private fun fetchGoodsByIdRequest(
        request: Request,
        onComplete: (Goods?) -> Unit,
    ) {
        okHttpClient.newCall(request).enqueue(
            object : Callback {
                override fun onResponse(
                    call: Call,
                    response: Response,
                ) {
                    response.body?.string()?.let { jsonString ->
                        try {
                            if (response.code == 404) {
                                onComplete(null)
                                return
                            }

                            val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)

                            if (jsonObject.has("product")) {
                                val productJsonString = jsonObject.get("product").toString()
                                val goodsDto =
                                    gson.fromJson(productJsonString, GoodsDto::class.java)
                                val goods = goodsDto.toDomain()
                                onComplete(goods)
                            } else {
                                onComplete(null)
                            }
                        } catch (e: Exception) {
                            onComplete(null)
                        }
                    } ?: onComplete(null)
                }

                override fun onFailure(
                    call: Call,
                    e: IOException,
                ) {
                    onComplete(null)
                }
            },
        )
    }
}
