package woowacourse.shopping.data.remote

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.database.CartDao
import woowacourse.shopping.data.database.ProductDao
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.CartedProduct
import kotlin.concurrent.thread

class MockShoppingWebServer(
    private val productDao: ProductDao,
    private val cartDao: CartDao,
) {
    private val mockWebServer = MockWebServer()
    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val requestedUrl = request.requestUrl
            println("$requestedUrl")
            return when (requestedUrl?.encodedPath) {
                "/products" -> {
                    val page = requestedUrl.queryParameter("page")?.toInt()
                    val pageSize = requestedUrl.queryParameter("page-size")?.toInt()
                    if (page == null || pageSize == null) {
                        MockResponse().setResponseCode(400)
                    } else {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(convertToJson(getProductsByPage(page, pageSize)))
                    }
                }

                "/product" -> {
                    val productId = requestedUrl.queryParameter("id")?.toLong()
                    if (productId == null) {
                        MockResponse().setResponseCode(400)
                    } else {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(convertToJson(getProductById(productId = productId)))
                    }
                }

                "/cart-items" -> {
                    val page = requestedUrl.queryParameter("page")?.toInt()
                    val pageSize = requestedUrl.queryParameter("page-size")?.toInt()
                    if (page == null || pageSize == null) {
                        MockResponse().setResponseCode(400)
                    } else {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(convertToJson(getCartItemsByPage(page, pageSize)))
                    }
                }

                "/cart-item" -> {
                    when (request.method) {
                        "DELETE" -> {
                            val item =
                                convertJsonToObject(request.body.readUtf8(), CartItem::class.java)
                            val isSuccessful = deleteCartItem(item)

                            if (isSuccessful) {
                                MockResponse()
                                    .setHeader("Content-Type", "application/json")
                                    .setResponseCode(200)
                            } else {
                                MockResponse()
                                    .setHeader("Content-Type", "application/json")
                                    .setResponseCode(400)
                            }
                        }

                        "POST" -> {
                            val item =
                                convertJsonToObject(request.body.readUtf8(), CartItem::class.java)
                            val isSuccessful = addCartItem(item)
                            if (isSuccessful) {
                                MockResponse()
                                    .setHeader("Content-Type", "application/json")
                                    .setResponseCode(200)
                            } else {
                                MockResponse()
                                    .setHeader("Content-Type", "application/json")
                                    .setResponseCode(400)
                            }
                        }

                        "PATCH" -> {
                            val id = requestedUrl.queryParameter("id")?.toLong()
                            val quantity = requestedUrl.queryParameter("quantity")?.toInt()
                            if (id == null || quantity == null) {
                                MockResponse()
                                    .setHeader("Content-Type", "application/json")
                                    .setResponseCode(400)
                            } else {
                                val isSuccessful = updateQuantity(id, quantity)
                                if (isSuccessful) {
                                    MockResponse()
                                        .setHeader("Content-Type", "application/json")
                                        .setResponseCode(200)
                                } else {
                                    MockResponse()
                                        .setHeader("Content-Type", "application/json")
                                        .setResponseCode(400)
                                }
                            }
                        }

                        else -> MockResponse().setResponseCode(404)
                    }
                }

                "/cart-item/count" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setBody(convertToJson(getTotalCount()))
                        .setResponseCode(200)
                }

                else -> {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }

    init {
        thread {
            mockWebServer.start(12345)
            mockWebServer.dispatcher = dispatcher
        }
    }

    private fun getProductsByPage(
        page: Int,
        pageSize: Int,
    ): List<CartableProduct> {
        return productDao.getCartableProducts(page, pageSize)
    }

    private fun getProductById(
        productId: Long
    ): CartableProduct {
        return productDao.getCartableProduct(productId)
    }

    private fun getCartItemsByPage(
        page: Int,
        pageSize: Int,
    ): List<CartedProduct> {
        return cartDao.getCartedProducts(page, pageSize)
    }

    private fun getTotalCount(): Int {
        return cartDao.getTotalQuantity()
    }

    private fun updateQuantity(
        cartItemId: Long,
        quantity: Int,
    ): Boolean {
        return runCatching {
            cartDao.updateQuantity(cartItemId, quantity)
            true
        }.onFailure {
            return false
        }.getOrDefault(false)
    }

    private fun deleteCartItem(
        cartItem: CartItem
    ): Boolean {
        return runCatching {
            cartDao.deleteCartItem(cartItem)
            true
        }.onFailure {
            return false
        }.getOrDefault(false)
    }

    private fun addCartItem(
        cartItem: CartItem
    ): Boolean {
        return runCatching {
            cartDao.addCartItem(cartItem)
            true
        }.onFailure {
            return false
        }.getOrDefault(false)
    }

    fun <T> convertJsonToList(json: String, classType: Class<T>): List<T> {
        val gson = Gson()
        val type = TypeToken.getParameterized(List::class.java, classType).type
        return gson.fromJson(json, type)
    }

    fun <T> convertJsonToObject(json: String, classType: Class<T>): T {
        val gson = Gson()
        return gson.fromJson(json, classType)
    }

    fun <T> convertToJson(data: T): String {
        val gson = Gson()
        return gson.toJson(data)
    }
}
