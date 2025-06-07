package woowacourse.shopping.data.inventory

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.data.toProduct
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Product
import kotlin.concurrent.thread

class InventoryRepositoryRemoteImpl(
    private val okHttpClient: OkHttpClient,
) : InventoryRepository {
    private val gson = Gson()
    private val baseUrl = "http://mock.api"

    override fun getOrNull(
        id: Int,
        onResult: (Product?) -> Unit,
    ) {
        val request =
            Request.Builder()
                .url("$baseUrl/product-items/$id")
                .get()
                .build()

        okHttpClient.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(
                    call: Call,
                    e: IOException,
                ) {
                    e.printStackTrace()
                    thread {
                        onResult(null)
                    }
                }

                override fun onResponse(
                    call: Call,
                    response: Response,
                ) {
                    var product: ProductEntity? = null
                    if (response.isSuccessful) {
                        response.body?.string()?.let { responseBody ->
                            try {
                                product = gson.fromJson(responseBody, ProductEntity::class.java)
                            } catch (e: JsonSyntaxException) {
                                e.printStackTrace()
                            }
                        }
                    } else {
                        println("GetOrNull Error: ${response.code} ${response.message}")
                    }
                    thread {
                        onResult(product?.toProduct())
                    }
                }
            },
        )
    }

    override fun getAll(onSuccess: (List<Product>) -> Unit) {
        val request =
            Request.Builder()
                .url("$baseUrl/product-items")
                .get()
                .build()

        okHttpClient.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(
                    call: Call,
                    e: IOException,
                ) {
                    e.printStackTrace()
                    thread {
                        onSuccess(emptyList())
                    }
                }

                override fun onResponse(
                    call: Call,
                    response: Response,
                ) {
                    var products: List<ProductEntity> = emptyList()
                    if (response.isSuccessful) {
                        response.body?.string()?.let { responseBody ->
                            try {
                                val listType = object : TypeToken<List<ProductEntity>>() {}.type
                                products = gson.fromJson(responseBody, listType)
                            } catch (e: JsonSyntaxException) {
                                e.printStackTrace()
                            }
                        }
                    } else {
                        println("GetAll Error: ${response.code} ${response.message}")
                    }
                    thread {
                        onSuccess(products.map(ProductEntity::toProduct))
                    }
                }
            },
        )
    }

    override fun getPage(
        pageSize: Int,
        pageIndex: Int,
        onSuccess: (Page<Product>) -> Unit,
    ) {
        getAll { allItems ->
            val from = pageSize * pageIndex
            val to = (from + pageSize).coerceAtMost(allItems.size)
            val itemsOnPage = if (from < allItems.size) allItems.subList(from, to) else emptyList()
            val hasPrevious = pageIndex > 0 && itemsOnPage.isNotEmpty()
            val hasNext = to < allItems.size
            val page =
                Page(
                    itemsOnPage,
                    hasPrevious,
                    hasNext,
                    pageIndex,
                )
            onSuccess(page)
        }
    }

    override fun insert(product: Product) {
        val productItemJson = gson.toJson(product)
        val requestBody =
            productItemJson.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val request =
            Request.Builder()
                .url("$baseUrl/product-items")
                .post(requestBody)
                .build()
        try {
            val response: Response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                println("Insert successful: ${response.code} ${response.message}")
                response.body?.string()
            } else {
                println("Insert Error: ${response.code} ${response.message} - ${response.body?.string()}")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
